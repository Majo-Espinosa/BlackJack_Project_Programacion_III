package co.edu.uptc.client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ReaderThread extends Thread {

    private BufferedReader reader;
    private String message;
    private Gson gson;
    private Socket client;

    public ReaderThread(BufferedReader reader, Socket client) throws IOException {
        this.reader = reader;
        this.client = client;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try {

            while (client.isClosed()!=true) {

                String msg = null;
                msg = reader.readLine();

                if (msg != null) {
                    System.out.println(msg); //para ver lo que llega
                    this.message = msg;

                }
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        }

    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
