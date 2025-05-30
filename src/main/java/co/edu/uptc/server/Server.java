package co.edu.uptc.server;

import co.edu.uptc.model.Dealer;
import co.edu.uptc.model.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    ServerController controller;
    final int PORT = 12345;
    private ArrayList<PrintWriter> outputs;
    private List<Player> players;
    private volatile boolean changes;
    private ServerSocket server;

    public Server(ServerController controller) {
        outputs = new ArrayList<PrintWriter>();
        players = new ArrayList<>();
        Dealer dealer = new Dealer();
        dealer.setName("Dealer");
        dealer.setId(0);
        players.add(dealer);
        changes = true;

    }

    public void initServer() {
        try {
            server = new ServerSocket(PORT);

            while (true) {
                System.out.println("Esperando clientes");
                Socket client = server.accept();
                System.out.println("Cliente aceptado");
                ClientThread newClient = new ClientThread(client, this);
                newClient.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initServer();
    }

    synchronized public ArrayList<PrintWriter> getOutputs() {
        return outputs;
    }

    public synchronized List<Player> getPlayers() {
        return players;
    }

    public synchronized void setPlayers(List<Player> players) {
        this.players = players;
    }

    public synchronized boolean isChanges() {
        return this.changes;
    }

    public synchronized void setChanges(boolean changes) {
        this.changes = changes;
    }
}



