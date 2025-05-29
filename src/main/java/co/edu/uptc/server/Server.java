package co.edu.uptc.server;

import co.edu.uptc.model.Dealer;
import co.edu.uptc.model.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    final int PORT = 12345;
    private ArrayList<PrintWriter> outputs;
    private List<Player> players;
    private ServerSocket serverSocket;

    public Server() {
        outputs = new ArrayList<PrintWriter>();
        players = new ArrayList<>();
        Dealer dealer = new Dealer();
        dealer.setName("Dealer");
        dealer.setId(0);
        players.add(dealer);
        try {
            ServerSocket server = new ServerSocket(PORT);

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



    synchronized public ArrayList<PrintWriter> getOutputs() {
        return outputs;
    }

    public static void main(String[] args) {
            new Server();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}



