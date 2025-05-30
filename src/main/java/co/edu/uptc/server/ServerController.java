package co.edu.uptc.server;

import co.edu.uptc.model.BlackJackGame;
import co.edu.uptc.model.Player;

import java.util.List;

public class ServerController extends Thread {

    private final Server server;
    private List<Player> players;
    private CheckChangesThread checkChangesThread;
    private BlackJackGame game;

    //Asegura que una variable se escriba directamente en el almacenamiento y no en el cache, asegura que los hilos vean el cambio más reciente.
    private volatile boolean controllerChanges;

    public ServerController() {
        controllerChanges = false;
        server = new Server(this);
        server.start();
        players = server.getPlayers();
        game = new BlackJackGame(this);
        checkChangesThread = new CheckChangesThread(server, this);
        checkChangesThread.start();
    }

    @Override
    public void run() {
        while (true) {
            if (controllerChanges) {
                System.out.println(players.toString());
                game.setPlayers(players);
                game.actions();
                server.setPlayers(players);
                System.out.println(players.toString());
                controllerChanges = false;
            }

            // Añadimos un intervalo de espera para evitar el polling
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // Añadir sincronización para evitar accesos múltiples al mismo tiempo
    public synchronized boolean isControllerChanges() {
        return controllerChanges;
    }

    public synchronized void setControllerChanges(boolean controllerChanges) {
        this.controllerChanges = controllerChanges;
    }

    public void readChanges() {
        this.players = server.getPlayers();
    }

    public static void main(String[] args) {
        ServerController controller = new ServerController();
        controller.start();
    }

    public void setPlayers(List<Player> players){
        this.players = players;
    }
}