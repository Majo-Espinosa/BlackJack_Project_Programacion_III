package co.edu.uptc.client;

import co.edu.uptc.utils.JsonUtils;
import co.edu.uptc.view.ConsoleView;
import java.io.*;
import java.net.Socket;
import java.util.Map;

public class BlackjackClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ConsoleView view = new ConsoleView();

        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("prompt") && line.contains("bet")) {
                        int bet = view.promptBet(1000);
                        out.println(JsonUtils.toJson(Map.of("type", "bet", "amount", bet)));
                    } else if (line.contains("prompt") && line.contains("hit")) {
                        String action = view.promptAction();
                        out.println(action.equalsIgnoreCase("hit") ? "hit" : "stand");
                    } else {
                        System.out.println("[SERVER] " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
