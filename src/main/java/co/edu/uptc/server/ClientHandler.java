package co.edu.uptc.server;

import co.edu.uptc.model.Player;
import co.edu.uptc.utils.JsonUtils;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BlackjackServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String playerId;

    public ClientHandler(Socket socket, BlackjackServer server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                Map<String, Object> message = JsonUtils.fromJson(line, Map.class);
                String type = (String) message.get("type");

                if (type.equals("register")) {
                    playerId = (String) message.get("id");
                    Player player = new Player(playerId);
                    server.getGameState().addPlayer(player);
                    server.broadcastGameState();
                } else if (type.equals("bet")) {
                    int amount = (int) message.get("amount");
                    Player player = server.getGameState().getPlayer(playerId);
                    player.setBet(amount);
                    player.updateBalance(-amount);
                    server.broadcastGameState();
                } else if (type.equals("hit") || type.equals("stand")) {
                    // Aquí se manejaría la lógica del turno
                    System.out.println("Jugador " + playerId + " eligió: " + type);
                    // Luego actualiza y transmite
                    server.broadcastGameState();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

