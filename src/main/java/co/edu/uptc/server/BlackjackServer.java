package co.edu.uptc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlackjackServer {
    //Constantes
    private static final int PORT = 12345;
    private static final int MAX_CLIENTS = 3;

    private ServerSocket serverSocket;
    private GameManager gameManager;
    //Da metodos para facilitar la ejecucion y terminacion de los procesos como los clientes
    private ExecutorService clientPool;
    private ConcurrentHashMap<String, ClientHandler> clients;

    //Constructor
    public BlackjackServer() {
        gameManager = new GameManager();
        clientPool = Executors.newFixedThreadPool(MAX_CLIENTS);
        clients = new ConcurrentHashMap<>();
    }

    //Iniciar servidor
    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado en puerto " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            if (clients.size() < MAX_CLIENTS) {
                String clientId = "Player" + (clients.size() + 1);
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, gameManager);
                clients.put(clientId, clientHandler);
                clientPool.execute(clientHandler);
                gameManager.addPlayer(clientId, clientHandler);
            } else {
                clientSocket.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new BlackjackServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}