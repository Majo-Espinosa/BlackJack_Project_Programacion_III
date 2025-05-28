package co.edu.uptc.clientUs.ServerTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import co.edu.uptc.clientUs.ReaderThread;
import co.edu.uptc.model.Player;

public class ClientThread extends Thread {

	private BufferedReader reader;
	private PrintWriter writer;
	private Socket client;
	private GameServer server;
	private ReaderThread readerThread;

	public ClientThread(Socket client, GameServer server) throws IOException {
		this.server = server;
		this.client = client;
		reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		writer = new PrintWriter(this.client.getOutputStream(), true);
		readerThread = new ReaderThread(reader, this.client);
		server.getOutputs().add(writer);
	}

	@Override
	public void run() {
		try {
			Gson gson = new Gson();
			readerThread.start();

			while (client.isConnected()) {

				String message = readerThread.getMessage();
				
				if (message != null) {
					Player JsonPlayer = gson.fromJson(message, Player.class);
					String finalMessage = gson.toJson(JsonPlayer);
                    writer.println(finalMessage + "[SERVER]");
                    readerThread.setMessage(null);
                    System.out.println("mensaje enviado");
					}
					
                    
				}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
        }
		}


	

	public BufferedReader getReader() {
		return reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

}
