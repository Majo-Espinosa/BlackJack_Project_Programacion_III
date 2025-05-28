package co.edu.uptc.clientDaniel;

import co.edu.uptc.model.Player;
import co.edu.uptc.utils.JsonUtils;
import co.edu.uptc.clientUs.ConsoleView;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class BlackjackClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ConsoleView view = new ConsoleView();

        String username = view.askUserName();
        Player player = new Player(username);

        // Enviar al servidor el nombre del jugador
        out.println(JsonUtils.toJson(Map.of("type", "register", "id", player.getId())));

        Thread listeningThread = new Thread(new Runnable() {
            public void run() {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        Map<String, Object> message = JsonUtils.fromJson(line, Map.class);
                        String type = (String) message.get("type");

                        if (type.equals("prompt_bet")) {
                            int bet = view.promptBet(player.getBalance());
                            out.println(JsonUtils.toJson(Map.of("type", "bet", "amount", bet)));
                        } else if (type.equals("prompt_hit")) {
                            String action = view.promptAction();
                            out.println(action.equalsIgnoreCase("hit") ? "hit" : "stand");
                        } else if (type.equals("game_state")) {
                            Map<String, Object> data = (Map<String, Object>) message.get("data");
                            view.showGameState(data);
                        } else {
                            System.out.println("[SERVER] " + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        listeningThread.start();
    }
}
