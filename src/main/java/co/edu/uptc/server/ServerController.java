package co.edu.uptc.server;
import co.edu.uptc.server.model.Player;

import java.io.IOException;

public class ServerController {
    private Server server;
    private ClientThread clienteThread;


    public ServerController() throws IOException {

        this.server = new Server();
        clienteThread = new ClientThread();
    }


    public void hitGame() {

    }

    public void standGame() {
    }
}




