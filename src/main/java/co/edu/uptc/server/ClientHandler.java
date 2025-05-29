package co.edu.uptc.server;

import co.edu.uptc.enums.PlayerAction;
import co.edu.uptc.utils.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String playerId;
    private GameManager gameManager;
    private boolean active;

    public ClientHandler(Socket socket, String playerId, GameManager gameManager) throws IOException {
        this.socket = socket;
        this.playerId = playerId;
        this.gameManager = gameManager;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.active = true;
    }

    //Hilo del cliente
    @Override
    public void run() {
        try {
            String inputLine;
            while (active && (inputLine = in.readLine()) != null) {
                inputLine = inputLine.trim();

                if (inputLine.isEmpty()) {
                    continue;
                }

                System.out.println("Recibido de " + playerId + ": " + inputLine);

                if (!JsonUtils.isValidJson(inputLine)) {
                    System.err.println("JSON inválido de " + playerId + ": " + inputLine);
                    continue;
                }

                Map<String, Object> message = JsonUtils.fromJson(inputLine, Map.class);
                if (message != null) {
                    processMessage(message);
                }
            }
        } catch (SocketException e) {
            System.out.println("Cliente " + playerId + " desconectado");
        } catch (IOException e) {
            System.out.println("Error de E/O con cliente " + playerId + ": " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void processMessage(Map<String, Object> message) {
        try {
            String type = (String) message.get("type");

            if (type == null) {
                return;
            }

            System.out.println("Procesando de " + playerId + ": " + type);

            switch (type) {
                case "bet" -> {
                    Object amountObj = message.get("amount");
                    if (amountObj instanceof Number) {
                        int amount = ((Number) amountObj).intValue();
                        gameManager.processBet(playerId, amount);
                    }
                }
                case "hit" -> gameManager.processPlayerAction(playerId, PlayerAction.HIT);
                case "stand" -> gameManager.processPlayerAction(playerId, PlayerAction.STAND);
                case "double" -> gameManager.processPlayerAction(playerId, PlayerAction.DOUBLE);
                case "split" -> gameManager.processPlayerAction(playerId, PlayerAction.SPLIT);
                case "surrender" -> gameManager.processPlayerAction(playerId, PlayerAction.SURRENDER);
                case "insurance" -> gameManager.processPlayerAction(playerId, PlayerAction.INSURANCE);
            }
        } catch (Exception e) {
            System.err.println("Error procesando mensaje de " + playerId + ": " + e.getMessage());
        }
    }

    public void sendMessage(Map<String, Object> message) {
        if (active && out != null) {
            try {
                // Usar JSON compacto para envío
                String jsonMessage = JsonUtils.toJson(message);
                out.println(jsonMessage);
                out.flush();

                String type = (String) message.get("type");
                System.out.println("Enviado a " + playerId + ": " + type);

            } catch (Exception e) {
                System.err.println("Error enviando a " + playerId + ": " + e.getMessage());
                active = false;
            }
        }
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean isActive() {
        return active;
    }

    private void cleanup() {
        active = false;
        gameManager.removePlayer(playerId);

        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error cerrando recursos de " + playerId);
        }

        System.out.println("Cliente " + playerId + " desconectado completamente");
    }
}