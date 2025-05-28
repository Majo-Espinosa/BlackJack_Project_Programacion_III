package co.edu.uptc.server;

import co.edu.uptc.model.GameState;
import co.edu.uptc.utils.JsonUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class BlackjackServer {
    private List<ClientHandler> clients = new ArrayList<>();
    private GameState gameState = new GameState();

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

    public static void main(String[] args) throws IOException {
        BlackjackServer server = new BlackjackServer();
        server.start();
    }
}
