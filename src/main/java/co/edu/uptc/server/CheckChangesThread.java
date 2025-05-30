package co.edu.uptc.server;

public class CheckChangesThread extends Thread {

    private Server server;
    private ServerController controller;
    private int counter = 0;

    public CheckChangesThread(Server server, ServerController controller) {
        this.controller = controller;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            if(server.isChanges()) {
                controller.readChanges();
                controller.setControllerChanges(true);
                server.setChanges(false);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}