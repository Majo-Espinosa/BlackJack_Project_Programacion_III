package co.edu.uptc.server;

import co.edu.uptc.model.Player;
import co.edu.uptc.utils.JsonUtils;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BlackjackServer server;
    private final BufferedReader in;
    private final PrintWriter out;
    private String playerId;
    private String lastAction;
    private boolean disconnected;

    public ClientHandler(Socket socket, BlackjackServer server) throws IOException {
        this.socket = socket;
        disconnected = false;
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null && !disconnected) {
                Map<String, Object> message = JsonUtils.fromJson(line, Map.class);
                String type = (String) message.get("type");

                switch (type) {
                    case "register" -> handleRegistration(message);
                    case "bet" -> handleBet(message);
                    case "hit", "stand" -> handleGameAction(type);
                    default -> System.out.println("Mensaje no reconocido: " + type);
                }
            }
        } catch (IOException e) {
            if (!disconnected) {
                System.out.println("Error de conexión con cliente: " + playerId);
                disconnect();
            }
        } finally {
            disconnect();
        }
    }

    private void handleRegistration(Map<String, Object> message) {
        playerId = (String) message.get("id");
        Player player = new Player(playerId);
        player.setBalance(1000);
        server.getGameState().addPlayer(player);
        server.broadcastGameState();
        server.checkAndStartGame();
    }

    private void handleBet(Map<String, Object> message) {
        int amount = ((Number) message.get("amount")).intValue();
        Player player = server.getGameState().getPlayer(playerId);

        if (player != null) {
            synchronized (player) {
                if (amount >= 10 && amount <= player.getBalance()) {
                    player.setBet(amount);
                    player.updateBalance(-amount);
                    server.broadcastGameState();
                    synchronized (this) {
                        notify();
                    }
                } else {
                    sendMessage(JsonUtils.toJson(Map.of(
                        "type", "bet_error",
                        "message", "Apuesta inválida. Verifica los límites."
                    )));
                }
            }
        }
    }

    private void handleGameAction(String action) {
        synchronized (this) {
            lastAction = action;
            notify();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getPlayerId() {
        return playerId;
    }

    public synchronized String getLastAction() {
        return lastAction;
    }

    public synchronized void setLastAction(String action) {
        this.lastAction = action;
    }

    public synchronized void disconnect() {
        if (!disconnected) {
            disconnected = true;
            try {
                if (playerId != null) {
                    server.getGameState().removePlayer(playerId);
                    server.broadcastGameState();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}