package co.edu.uptc.server;

import co.edu.uptc.model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//REVISAR LA CLASE READERTHREAD PORQUE SOLO PRODUCE GASTO EN MEMORIA, ES MEJOR HACER AMBAS COSAS EN UN SOLO HILO
public class ClientThread extends Thread {

    private final BufferedReader reader;
    private final PrintWriter writer;
    private Socket client;
    private final Server server;
    // private ReaderThread readerThread;
    private List<Player> players;

    public ClientThread(Socket client, Server server) throws IOException {
        this.server = server;
        this.client = client;
        reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        writer = new PrintWriter(this.client.getOutputStream(), true);
//        readerThread = new ReaderThread(reader, this.client);
        server.getOutputs().add(writer);
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            writer.println(gson.toJson(server.getPlayers())); // Enviar estado inicial

            String msg;

            while ((msg = reader.readLine()) != null) {

                synchronized (server) {
                    Type listType = new TypeToken<List<Player>>() {
                    }.getType();
                    List<Player> receivedPlayers = gson.fromJson(msg, listType);
                    server.setPlayers(receivedPlayers);
                    server.setChanges(true);
                }
                // Enviar los cambios a todos los clientes
                sendMessage(server.getOutputs(), gson.toJson(server.getPlayers()));
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }
    }


    private Player verifyPlayerTurn() {
        Player player = null;
        for (Player tempPlayer : players) {
            if (tempPlayer.isInGame()) {
                player = tempPlayer;
            }
        }
        return player;
    }

    synchronized private void sendMessage(ArrayList<PrintWriter> outputs, String message) {
        ArrayList<PrintWriter> outputsCopy = new ArrayList<>(outputs);
        int i = 0;
        int end = outputsCopy.size();
        while (i < end) {
            outputsCopy.get(i).println(message);
            i++;
        }

    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

}
