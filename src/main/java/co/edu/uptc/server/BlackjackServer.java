package co.edu.uptc.server;

import co.edu.uptc.model.*;
import co.edu.uptc.utils.JsonUtils;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class BlackjackServer {
    private static final int PORT = 12345;
    private static final int MAX_PLAYERS = 3;
    private static final int BET_TIMEOUT = 20000;
    private static final int ACTION_TIMEOUT = 20000;

    private final GameState gameState = new GameState();
    private final List<ClientHandler> clients = new ArrayList<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(MAX_PLAYERS);

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started at port: " + PORT);

        while (true) {
            if (clients.size() < MAX_PLAYERS) {
                Socket socket = serverSocket.accept();
                String playerId = "Player" + (clients.size() + 1);
                Player player = new Player(playerId);
                gameState.addPlayer(player);

                ClientHandler clientHandler = new ClientHandler(socket, playerId);
                clients.add(clientHandler);
                pool.execute(clientHandler);

                if (clients.size() == 1) {
                    new Thread(this::startGame).start();
                }
            }
        }
    }

    private void startGame() {
        while (true) {
            try {
                gameState.resetHands();
                Deck deck = gameState.getDeck();
                deck.shuffle();

                // Fase de apuestas
                for (ClientHandler client : clients) {
                    client.send(JsonUtils.toJson(Map.of("type", "betting", "message", "Place your bet.")));
                }

                for (ClientHandler client : clients) {
                    client.awaitBet(BET_TIMEOUT);
                }

                // Repartir cartas
                for (ClientHandler client : clients) {
                    Player player = gameState.getPlayer(client.getPlayerId());
                    player.addCard(deck.draw());
                    player.addCard(deck.draw());
                }
                gameState.getDealer().addCard(deck.draw());
                gameState.getDealer().addCard(deck.draw());

                // Fase de decisiones
                for (ClientHandler client : clients) {
                    client.sendGameState();
                    client.awaitAction(ACTION_TIMEOUT, deck);
                }

                // Turno del crupier
                Crupier crupier = gameState.getDealer();
                crupier.playTurn(deck);

                // Evaluar resultados
                for (ClientHandler client : clients) {
                    Player player = gameState.getPlayer(client.getPlayerId());
                    int playerVal = player.getHandValue();
                    int dealerVal = crupier.getHandValue();

                    if (playerVal > 21) {
                        player.updateBalance(-player.getBet());
                        client.send(JsonUtils.toJson(Map.of("type", "result", "message", "You busted. You lose.")));
                    } else if (dealerVal > 21) {
                        player.updateBalance(player.getBet());
                        client.send(JsonUtils.toJson(Map.of("type", "result", "message", "Dealer busted. You win!")));
                    } else if (playerVal > dealerVal) {
                        player.updateBalance(player.getBet());
                        client.send(JsonUtils.toJson(Map.of("type", "result", "message", "You win!")));
                    } else if (playerVal == dealerVal) {
                        client.send(JsonUtils.toJson(Map.of("type", "result", "message", "Push (draw).")));
                    } else {
                        player.updateBalance(-player.getBet());
                        client.send(JsonUtils.toJson(Map.of("type", "result", "message", "You lose.")));
                    }
                }

                Thread.sleep(5000); // Esperar antes de siguiente ronda
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;
        private final String playerId;
        private BufferedReader in;
        private PrintWriter out;
        private boolean betReceived = false;
        private boolean actionReceived = false;

        public ClientHandler(Socket socket, String playerId) {
            this.socket = socket;
            this.playerId = playerId;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void send(String message) {
            out.println(message);
        }

        public void sendGameState() {
            Player player = gameState.getPlayer(playerId);
            Map<String, Object> data = new HashMap<>();
            data.put("type", "state");
            data.put("hand", player.getHand());
            data.put("value", player.getHandValue());
            data.put("balance", player.getBalance());
            send(JsonUtils.toJson(data));
        }

        public void awaitBet(int timeout) throws IOException {
            betReceived = false;
            send(JsonUtils.toJson(Map.of("type", "prompt", "message", "Enter your bet (within 20s).")));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!betReceived) {
                        gameState.getPlayer(playerId).setBet(50); // apuesta mínima
                        betReceived = true;
                        send(JsonUtils.toJson(Map.of("type", "info", "message", "Default bet applied.")));
                    }
                }
            }, timeout);

            while (!betReceived) {
                String line = in.readLine();
                if (line != null && line.contains("bet")) {
                    Map data = JsonUtils.fromJson(line, Map.class);
                    int amount = ((Double) data.get("amount")).intValue();
                    gameState.getPlayer(playerId).setBet(amount);
                    betReceived = true;
                }
            }
            timer.cancel();
        }

        public void awaitAction(int timeout, Deck deck) throws IOException {
            actionReceived = false;
            send(JsonUtils.toJson(Map.of("type", "prompt", "message", "Your turn: hit or stand (20s).")));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!actionReceived) {
                        actionReceived = true;
                        send(JsonUtils.toJson(Map.of("type", "info", "message", "Auto-stand applied.")));
                    }
                }
            }, timeout);

            while (!actionReceived) {
                String line = in.readLine();
                if (line != null) {
                    if (line.contains("hit")) {
                        Player player = gameState.getPlayer(playerId);
                        player.addCard(deck.draw());
                        sendGameState();
                        if (player.getHandValue() >= 21) {
                            actionReceived = true;
                        } else {
                            send(JsonUtils.toJson(Map.of("type", "prompt", "message", "hit or stand?")));
                        }
                    } else if (line.contains("stand")) {
                        actionReceived = true;
                    }
                }
            }
            timer.cancel();
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                send(JsonUtils.toJson(Map.of("type", "info", "message", "Connected as " + playerId)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BlackjackServer server = new BlackjackServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
