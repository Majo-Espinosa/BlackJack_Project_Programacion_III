package co.edu.uptc.view;

import java.util.*;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public int promptBet(int defaultBet) {
        System.out.println("Realiza tu apuesta (en menos de 20s): ");
        try {
            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) < 20000 && !scanner.hasNextInt()) {
                Thread.sleep(100);
            }

            if (scanner.hasNextInt()) {
                int bet = scanner.nextInt();
                scanner.nextLine(); // limpiar
                return bet > 0 ? bet : defaultBet;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("No se recibió apuesta a tiempo. Se aplica apuesta mínima.");
        return defaultBet;
    }

    public String promptAction() {
        System.out.println("¿Deseas 'hit' (pedir carta) o 'stand' (plantarte)? Tienes 20s...");
        try {
            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) < 20000 && !scanner.hasNextLine()) {
                Thread.sleep(100);
            }

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("hit") || input.equals("stand")) {
                    return input;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("No respondiste a tiempo. Se aplicó 'stand' por defecto.");
        return "stand";
    }

    public String askUserName() {
        System.out.println("Set your user name:");
        String input = scanner.nextLine();
        return input;
    }

    public void showGameState(Map<String, Object> data) {
        System.out.println("\n================== ESTADO DEL JUEGO ==================");

        // Mostrar estado del crupier
        Map<String, Object> dealer = (Map<String, Object>) data.get("crupier");
        List<Map<String, Object>> dealerHand = (List<Map<String, Object>>) dealer.get("hand");
        System.out.println(" Crupier:");
        printHand(dealerHand);
        System.out.println("Dealer tokens:" + dealer.get("balance"));

        // Mostrar todos los jugadores
        Map<String, Object> players = (Map<String, Object>) data.get("players");
        for (String playerId : players.keySet()) {
            Map<String, Object> player = (Map<String, Object>) players.get(playerId);
            List<Map<String, Object>> hand = (List<Map<String, Object>>) player.get("hand");
            System.out.println(player.get("balance"));
            int balance = ((Number) player.get("balance")).intValue();
            int bet = ((Number) player.get("bet")).intValue();

            System.out.println("\n Jugador: " + playerId);
            System.out.println(" Saldo: " + balance + " | Apuesta: " + bet);
            printHand(hand);
        }

        System.out.println("======================================================\n");
    }

    private void printHand(List<Map<String, Object>> hand) {
        StringBuilder cards = new StringBuilder("Cartas: ");
        int value = 0;
        int aces = 0;

        for (Map<String, Object> card : hand) {
            String rank = (String) card.get("rank");
            String suit = (String) card.get("suit");
            cards.append("[").append(rank).append(" de ").append(suit).append("] ");
            int cardValue = ((Number) card.get("value")).intValue();
            value += cardValue;
            if ("A".equals(rank)) aces++;
        }

        // Ajustar valor si hay ases y el valor supera 21
        while (value > 21 && aces-- > 0) {
            value -= 10;
        }

        cards.append("=> Valor: ").append(value);
        System.out.println(cards.toString());
    }
}

