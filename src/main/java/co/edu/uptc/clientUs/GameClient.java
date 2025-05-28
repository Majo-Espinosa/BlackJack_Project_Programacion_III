package co.edu.uptc.clientUs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import co.edu.uptc.model.Player;

public class GameClient extends Thread{

    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;
    private ReaderThread readerThread;
    private Player player;
    private Gson gson;

    public GameClient() throws IOException{

        player = new Player(null);
        gson = new Gson();
    }

    private void conectClient() {
    try {
        client = new Socket("localhost", 12345);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        writer = new PrintWriter(client.getOutputStream(),true);
        readerThread = new ReaderThread(reader, client);
        readerThread.start();
    } catch (UnknownHostException e) {
        
        e.printStackTrace();
    } catch (IOException e) {
      
        e.printStackTrace();
    }
       
    }

    @Override
    public void run() {
        conectClient();
        boolean exit = false;
        boolean inParty = false;
        while (exit!=true){

            if(player.getId()!=null && inParty!=true){
                writer.println(gson.toJson(player));
                System.out.println("enviado");
                inParty = true;
            }else{
                try {
                    sleep(3000);
                    System.out.println("waiting");
                } catch (InterruptedException ex) {
                }
                
            }

        }
    }

    

    public void setPlayerId(String id){
        this.player.setId(id);
    }

}
