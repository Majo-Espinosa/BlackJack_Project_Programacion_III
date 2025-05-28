package co.edu.uptc.client;

import co.edu.uptc.utils.JsonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import co.edu.uptc.model.Player;
import java.util.Map;

public class GameClient extends Thread{

    private Socket client;
    private BufferedReader reader;
    private BufferedReader in;
    private PrintWriter out;
    private Player player;
    private Gson gson;

    public GameClient() throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        player = new Player(null);
        gson = new Gson();
    }

    private void conectClient() {
    try {
        client = new Socket("localhost", 12345);
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
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

            try {
                String line;
                while ((line = in.readLine()) != null) {
                    Map<String, Object> message = JsonUtils.fromJson(line, Map.class);
                    String type = (String) message.get("type");

                    switch (type) {
                        case "prompt_bet" -> {
                            int minBet = ((Number) message.get("minBet")).intValue();
                            int maxBet = ((Number) message.get("maxBet")).intValue();
                            int bet = view.promptBet(player.getBalance(), minBet, maxBet);
                            out.println(JsonUtils.toJson(Map.of(
                                "type", "bet",
                                "amount", bet
                            )));
                        }
                        case "prompt_hit" -> {
                            String action = view.promptAction();
                            out.println(JsonUtils.toJson(Map.of(
                                "type", action.toLowerCase()
                            )));
                        }
                        case "game_state" -> {
                            view.showGameState((Map<String, Object>) message.get("data"));
                        }
                        case "game_result" -> {
                            String result = (String) message.get("result");
                            int amount = ((Number) message.get("amount")).intValue();
                            int newBalance = ((Number) message.get("newBalance")).intValue();
                            player.setBalance(newBalance);
                            view.showGameResult(result, amount, newBalance);
                        }
                        case "insufficient_balance" -> {
                            view.showMessage((String) message.get("message"));
                            System.exit(0);
                        }
                        default -> view.showMessage(line);
                    }
                }
            } catch (IOException e) {
                view.showMessage("Error de conexión con el servidor");
                System.exit(1);
            }
        }
    }

    

    public void setPlayerId(String id){
        this.player.setId(id);
    }

}
