package co.edu.uptc.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

//Cuando ya este la vista normal esto se puede borrar :p

public class ConsoleView {
    private BufferedReader consoleReader;

    public ConsoleView() {
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public int promptBet(int balance, int minBet, int maxBet) {
        System.out.println("\n---FASE DE APUESTAS---");
        System.out.println("Balance actual: $" + balance);
        System.out.println("Apuesta mínima: $" + minBet + " | Apuesta máxima: $" + maxBet);
        System.out.print("Ingresa tu apuesta (tienes 20 segundos): $");

        try {
            String input = consoleReader.readLine();
            int bet = Integer.parseInt(input);

            if (bet < minBet) bet = minBet;
            if (bet > maxBet) bet = maxBet;
            if (bet > balance) bet = balance;

            return bet;
        } catch (IOException | NumberFormatException e) {
            return minBet; // Apuesta mínima por defecto
        }
    }

    public String promptAction() {
        System.out.println("\n---TU TURNO---");
        System.out.println("Acciones disponibles:");
        System.out.println("1. HIT (Pedir carta)");
        System.out.println("2. STAND (Plantarse)");
        System.out.println("3. DOUBLE (Doblar)");
        System.out.println("4. SURRENDER (Rendirse)");
        System.out.print("Elige tu acción (1-4): ");

        try {
            String input = consoleReader.readLine();
            return switch (input) {
                case "1" -> "HIT";
                case "2" -> "STAND";
                case "3" -> "DOUBLE";
                case "4" -> "SURRENDER";
                default -> "STAND"; // Por defecto se planta
            };
        } catch (IOException e) {
            return "STAND";
        }
    }

    public void showGameState(Map<String, Object> gameStateData) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ESTADO DEL JUEGO");
        System.out.println("=".repeat(50));

        // Mostrar dealer
        Object dealerObj = gameStateData.get("dealer");
        if (dealerObj instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<String, Object> dealer = (Map<String, Object>) dealerObj;
            Object handObj = dealer.get("hand");
            if (handObj instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, Object> dealerHand = (Map<String, Object>) handObj;
                System.out.println("DEALER:");
                System.out.println("  Cartas: " + dealerHand.get("cards"));
                System.out.println("  Valor: " + dealerHand.get("value"));
            } else {
                System.out.println("DEALER:");
                System.out.println("  Cartas: N/A");
                System.out.println("  Valor: N/A");
            }
        }

        // Mostrar jugadores
        System.out.println("\nJUGADORES:");
        @SuppressWarnings("unchecked")
        java.util.List<Map<String, Object>> players =
            (java.util.List<Map<String, Object>>) gameStateData.get("players");

        if (players != null) {
            for (Map<String, Object> player : players) {
                String playerId = (String) player.get("id");
                int balance = ((Number) player.get("balance")).intValue();
                int currentBet = ((Number) player.get("currentBet")).intValue();

                @SuppressWarnings("unchecked")
                java.util.List<Map<String, Object>> hands =
                    (java.util.List<Map<String, Object>>) player.get("hands");

                System.out.println("  " + playerId + ":");
                System.out.println("    Balance: $" + balance);
                System.out.println("    Apuesta: $" + currentBet);

                if (!hands.isEmpty()) {
                    Map<String, Object> hand = hands.get(0);
                    System.out.println("    Cartas: " + hand.get("cards"));
                    System.out.println("    Valor: " + hand.get("value"));

                    if ((Boolean) hand.get("isBlackjack")) {
                        System.out.println("    ¡BLACKJACK!");
                    }
                    if ((Boolean) hand.get("isBusted")) {
                        System.out.println("    ¡BUSTED!");
                    }
                }
            }
        }
        System.out.println("=".repeat(50));
    }

    public void showGameResult(String result, int amount, int newBalance) {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("RESULTADO DE LA RONDA");
        System.out.println("=".repeat(30));

        switch (result) {
            case "WIN" -> {
                System.out.println("¡GANASTE!");
                System.out.println("Ganancia: +$" + amount);
            }
            case "LOSE" -> {
                System.out.println("Perdiste");
                System.out.println("Pérdida: $" + Math.abs(amount));
            }
            case "PUSH" -> {
                System.out.println("Empate");
                System.out.println("Sin cambios en el balance");
            }
        }

        System.out.println("Nuevo balance: $" + newBalance);
        System.out.println("=".repeat(30));
    }

    public void showMessage(String message) {
        System.out.println(">>> " + message);
    }
}