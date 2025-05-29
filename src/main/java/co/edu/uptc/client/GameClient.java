package co.edu.uptc.client;

import co.edu.uptc.model.Player;
import co.edu.uptc.utils.JsonUtils;
import co.edu.uptc.view.ConsoleView;
import co.edu.uptc.view.MainFrame;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class GameClient extends Thread {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Player player;
    private Gson gson;
    private MainFrame view;

    public GameClient() throws IOException {
        player = new Player(null);
        gson = new Gson();
        view = new MainFrame();
    }

    private void connectClient() {
        try {
            client = new Socket("localhost", 12345);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            System.out.println("Conectado al servidor de Blackjack :D");
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    //Correr el hilo del cliente
    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        //conectar al servidor
        connectClient();
        if (client == null) {
            System.err.println("No se pudo establecer conexión");
            return;
        }

        //Conexion exitosa
        try {
            String line;
            while ((line = in.readLine()) != null) {
                // Limpiar y verificar la línea
                line = line.trim();

                if (line.isEmpty()) {
                    continue; // Ignorar líneas vacías
                }

                //Solo para debug y ver lo que se recibe xd
                System.out.println("Recibido: " + line);
                //Verifica si el mensaje es un json valido
                if (!JsonUtils.isValidJson(line)) {
                    System.err.println("JSON inválido: " + line);
                    continue;
                }
                //Lo convierte en un objeto Map<String, Object> para poder obtener los valores
                Map<String, Object> message = JsonUtils.fromJson(line, Map.class);
                if (message == null) {
                    System.err.println("No se pudo parsear: " + line);
                    continue;
                }
                String type = (String) message.get("type");

                if (type == null) {
                    System.err.println("Mensaje sin tipo: " + line);
                    continue;
                }
                processMessage(type, message);
            }
        } catch (IOException e) {
            view.showMessage("Error de conexión con el servidor: " + e.getMessage());
            System.exit(1);
        } finally {
            closeConnection();
        }
    }

    //Verifica el tipo de mensaje obtenido del servidor y maneja junto a la vista cada uno de los casos
    private void processMessage(String type, Map<String, Object> message) {
        try {
            System.out.println("Procesando mensaje tipo: " + type);
            switch (type) {
                case "connection_accepted" -> {
                    String playerId = (String) message.get("playerId");
                    if (playerId != null) {
                        setPlayerId(playerId);
                        view.showMessage("Conectado como: " + playerId);
                    }
                }
                case "prompt_bet" -> {
                    int minBet = ((Number) message.get("minBet")).intValue();
                    int maxBet = ((Number) message.get("maxBet")).intValue();
                    int bet = view.promptBet(player.getBalance(), minBet, maxBet);

                    String response = JsonUtils.toJson(Map.of(
                        "type", "bet",
                        "amount", bet
                    ));
                    out.println(response);
                    System.out.println("Enviando apuesta: " + response);
                }
                case "prompt_hit" -> {
                    String action = view.promptAction();
                    String response = JsonUtils.toJson(Map.of(
                        "type", action.toLowerCase()
                    ));
                    out.println(response);
                    System.out.println("Enviando acción: " + response);
                }
                case "game_state" -> {
                    Object data = message.get("data");
                    System.out.println(data);
                    if (data instanceof Map) {
                        view.showGameState((Map<String, Object>) data);
                    }
                }
                case "game_result" -> {
                    String result = (String) message.get("result");
                    int amount = message.get("amount") != null ?
                        ((Number) message.get("amount")).intValue() : 0;
                    int newBalance = message.get("newBalance") != null ?
                        ((Number) message.get("newBalance")).intValue() : player.getBalance();

                    player.setBalance(newBalance);
                    view.showGameResult(result, amount, newBalance);
                }
                case "insufficient_balance" -> {
                    String msg = (String) message.get("message");
                    view.showMessage(msg != null ? msg : "Balance insuficiente");
                    System.exit(0);
                }
                case "waiting_for_players" -> {
                    view.showMessage("Esperando más jugadores...");
                }
                case "round_starting" -> {
                    view.showMessage("¡Nueva ronda comenzando!");
                }
                default -> {
                    view.showMessage("Mensaje del servidor: " + JsonUtils.toPrettyJson(message));
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando mensaje tipo '" + type + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Terminar la conexion del cliente
    private void closeConnection() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (client != null) client.close();
        } catch (IOException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }

    public void setPlayerId(String id) {
        this.player.setId(id);
    }

    public void setView(ConsoleView newView) {
        this.view = newView;
    }

    public static void main(String[] args) {
        try {
            GameClient client = new GameClient();
            client.start();
        } catch (IOException e) {
            System.err.println("Error al iniciar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}