package co.edu.uptc.server;

import co.edu.uptc.model.*;
import co.edu.uptc.utils.JsonUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class BlackjackServer {
    private final List<ClientHandler> clients = new ArrayList<>();
    private final GameState gameState = new GameState();
    private boolean gameRunning = false;
    private static final int MIN_BET = 10;

    public static void main(String[] args) throws IOException {
        new BlackjackServer().start();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor Blackjack iniciado...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(clientSocket, this);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    public synchronized void broadcastGameState() {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "game_state");
        message.put("data", gameState);
        String json = JsonUtils.toJson(message);
        for (ClientHandler client : clients) {
            client.sendMessage(json);
        }
    }

    public synchronized GameState getGameState() {
        return gameState;
    }

    public synchronized ClientHandler getClientById(String id) {
        for (ClientHandler c : clients) {
            if (id.equals(c.getPlayerId())) return c;
        }
        return null;
    }

    public synchronized void removePlayer(String playerId) {
        gameState.getPlayers().remove(playerId);
        clients.removeIf(client -> playerId.equals(client.getPlayerId()));
        if (gameState.getPlayers().isEmpty()) {
            gameRunning = false;
        }
        System.out.println("Jugador " + playerId + " ha sido removido de la sala");
    }

    public synchronized void checkAndStartGame() {
        if (!gameRunning && gameState.getPlayers().size() >= 1) {
            gameRunning = true;
            new Thread(() -> {
                while (true) {
                    try {
                        runGameRound();
                        Thread.sleep(5000); // Espera entre rondas
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void runGameRound() throws InterruptedException {
        gameState.resetHands();
        gameState.getDeck().shuffle();

        // Verificar saldos antes de aceptar apuestas
        Iterator<Map.Entry<String, Player>> iterator = gameState.getPlayers().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Player> entry = iterator.next();
            Player player = entry.getValue();
            if (player.getBalance() < MIN_BET) {
                ClientHandler handler = getClientById(player.getId());
                if (handler != null) {
                    handler.sendMessage(JsonUtils.toJson(Map.of(
                        "type", "insufficient_balance",
                        "message", "Saldo insuficiente. Serás expulsado de la mesa."
                    )));
                    handler.disconnect();
                }
                iterator.remove();
            }
        }

        // Si no quedan jugadores, terminar la ronda
        if (gameState.getPlayers().isEmpty()) {
            gameRunning = false;
            return;
        }

        // Solicitar apuestas
        for (Player player : gameState.getPlayers().values()) {
            ClientHandler handler = getClientById(player.getId());
            if (handler != null) {
                handler.sendMessage(JsonUtils.toJson(Map.of(
                    "type", "prompt_bet",
                    "minBet", MIN_BET,
                    "maxBet", player.getBalance()
                )));
                synchronized (handler) {
                    handler.wait(20000); // Espera 20 segundos máximo por apuesta
                }

                // Verificar que la apuesta sea válida
                System.out.println(player);
                if (player.getBet() < MIN_BET || player.getBet() > player.getBalance()) {
                    player.setBet(0); // Apuesta inválida, se considera como stand
                } else {
                    player.updateBalance(-player.getBet()); // Descontar apuesta del balance
                }
            }
        }

        // Repartir cartas iniciales
        for (Player player : gameState.getPlayers().values()) {
            player.addCard(gameState.getDeck().draw());
            player.addCard(gameState.getDeck().draw());
        }
        gameState.getDealer().addCard(gameState.getDeck().draw());
        gameState.getDealer().addCard(gameState.getDeck().draw());
        broadcastGameState();

        // Turnos de jugadores
        for (Player player : gameState.getPlayers().values()) {
            ClientHandler handler = getClientById(player.getId());
            if (handler == null) continue;

            boolean done = false;
            while (!done && player.getHandValue() < 21) {
                handler.sendMessage(JsonUtils.toJson(Map.of("type", "prompt_hit")));
                synchronized (handler) {
                    handler.setLastAction(null);
                    handler.wait(20000);
                }

                String action = handler.getLastAction();
                if ("hit".equalsIgnoreCase(action)) {
                    player.addCard(gameState.getDeck().draw());
                    broadcastGameState();
                } else {
                    done = true;
                }
            }
        }

        // Turno del crupier
        while (gameState.getDealer().getHandValue() < 17) {
            gameState.getDealer().addCard(gameState.getDeck().draw());
            broadcastGameState();
            Thread.sleep(1000); // Pausa para efecto visual
        }

        // Resolver apuestas
        int dealerValue = gameState.getDealer().getHandValue();
        boolean dealerBusted = dealerValue > 21;

        for (Player player : gameState.getPlayers().values()) {
            int playerValue = player.getHandValue();
            int bet = player.getBet();

            if (playerValue > 21) { // Jugador se pasó
                sendResultToPlayer(player.getId(), "lose", bet);
            } else if (dealerBusted || playerValue > dealerValue) {
                player.updateBalance(bet * 2); // Pago 1:1
                sendResultToPlayer(player.getId(), "win", bet);
            } else if (playerValue == dealerValue) {
                player.updateBalance(bet); // Devuelve la apuesta
                sendResultToPlayer(player.getId(), "push", bet);
            } else {
                sendResultToPlayer(player.getId(), "lose", bet);
            }
            player.setBet(0);
        }

        broadcastGameState();
    }

    private void sendResultToPlayer(String playerId, String result, int amount) {
        ClientHandler handler = getClientById(playerId);
        if (handler != null) {
            handler.sendMessage(JsonUtils.toJson(Map.of(
                "type", "game_result",
                "result", result,
                "amount", amount,
                "newBalance", gameState.getPlayer(playerId).getBalance()
            )));
        }
    }
}