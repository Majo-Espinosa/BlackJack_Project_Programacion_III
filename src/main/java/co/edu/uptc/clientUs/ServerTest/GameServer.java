package co.edu.uptc.clientUs.ServerTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    
	final int PORT = 12345;
	private ArrayList<PrintWriter> outputs;

	public GameServer() {

		outputs = new ArrayList<PrintWriter>();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized public ArrayList<PrintWriter> getOutputs() {
		return outputs;
	}

	public static void main(String[] args) {
		new GameServer();
	}

}
