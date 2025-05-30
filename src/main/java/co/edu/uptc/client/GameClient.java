package co.edu.uptc.client;

import co.edu.uptc.model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class GameClient extends Thread {

    private final int PORT = 12345;
    private final String SERVER = "localhost";
    private Gson gson;
    private Scanner in = new Scanner(System.in);
    private int id;
    private Player player;

    public GameClient() {
        player = new Player();
        gson = new Gson();
        in = new Scanner(System.in);
        id = (int) (Math.random() * 100) + 1;
        player.setId(id);
    }

    @Override
    public void run() {
        initConnection();
    }
 
    public void initConnection() {
        try {
            Socket client = new Socket(SERVER, PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            List<Player> players;
            ReaderThread readerThread = new ReaderThread(reader, client);
        /*
            System.out.println("Escriba su nombre: ");
            user.setUser(in.nextLine());
        */

            boolean exit = false;
            String message;
            readerThread.start();

            while (!exit) {


                if ((player.getName() != null) && !player.isInGame()) {
                    player.setInGame(true);
                    Type playerListType = new TypeToken<List<Player>>() {
                    }.getType();
                    String msg;
                    while ((msg = readerThread.getMessage()) == null) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    players = gson.fromJson(msg, playerListType);
                    if (players != null) {
                        player.setInTurn(true);
                        players.addFirst(player);
                        String jsonPlayers = gson.toJson(players);
                        writer.println(jsonPlayers);
                    }

                }

                message = readerThread.getMessage();

                if (message != null) {
                    //System.out.println(readerThread.getMessage());
                    Type playerListType = new TypeToken<List<Player>>() {
                    }.getType();
                    players = gson.fromJson(message, playerListType);
                    readerThread.setMessage(null);

                }

                //  players = (metodo que devuelve la lista con el jugador en turno con la accion ya seteada) // devolver la lista de jugadores con la accion ya seteada para el jugador en turno

               /* if (message.equalsIgnoreCase("/salir")) {
                    exit = true;
                    user.setMessage(message);
                    writer.println(gson.toJson(user));
                    client.close();
                } else {
                    user.setMessage(message);
                    }
                */

            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendUpdateInfo(List<Player> players) {
        int potition;
        for (int i = 0; i < players.size(); i++) {
            if (player.getId() == players.get(i).getId()) {
                //players.
            }
        }
    }

    public boolean playerIsInTurn(List<Player> players) {
        boolean isInTurn = false;

        for (int i = 0; i < players.size(); i++) {
            if (this.id == players.get(i).getId()) {
                if (players.get(i).isInTurn()) {
                    System.out.println("El jugador " + this.id + " esta en turno");
                    isInTurn = true;
                }
            }
        }
        return isInTurn;
    }


    public static void main(String[] args) {
        GameClient client = new GameClient();

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
