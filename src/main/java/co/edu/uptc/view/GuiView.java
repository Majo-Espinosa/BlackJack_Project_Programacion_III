package co.edu.uptc.view;

import co.edu.uptc.view.popups.MessageDialog;

import java.io.BufferedReader;
import java.util.Map;

public class GuiView {
    private BufferedReader consoleReader;
    private MainFrame mainFrame;

    public GuiView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public int promptBet(int balance, int minBet, int maxBet) {
        mainFrame.setAction("STAND");

        mainFrame.getMainPanel().getGamePanel().getBottomPanel().getActionsPanel().getTokens().setTokens(balance);

        try {
            int bet = mainFrame.promptBet();
            if (bet < minBet) bet = minBet;
            if (bet > maxBet) bet = maxBet;
            if (bet > balance) bet = balance;

            return bet;
        } catch (NumberFormatException e) {
            return minBet; // Apuesta mínima por defecto
        }
    }

    public String promptAction() {
        mainFrame.setAction("STAND");
        mainFrame.setBet(10);

        String input = mainFrame.promptAction();

        System.out.println();
        System.out.printf("GUIVIEW" + mainFrame.promptAction());
        System.out.println();
        return input;
    }

    public void showGameState(Map<String, Object> gameStateData) {
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
                mainFrame.addCrupierCards(dealerHand.get("cards").toString());
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
                    mainFrame.addPlayerCards(hand.get("cards").toString());
                    System.out.println("    Valor: " + hand.get("value"));

                    if ((Boolean) hand.get("isBlackjack")) {
                        mainFrame.showMessage("¡BLACKJACK!");
                    }
                    if ((Boolean) hand.get("isBusted")) {
                        mainFrame.showMessage("Te pasaste :C");
                    }
                }
            }
        }
        System.out.println("=".repeat(50));
    }

    public void showGameResult(String result, int amount, int newBalance) {
        switch (result) {
            case "WIN" -> {
                mainFrame.showMessage("RESULTADO DE LA RONDA\n ¡GANASTE!" + "\nGanancia: +$" + amount);
            }
            case "LOSE" -> {
                mainFrame.showMessage("RESULTADO DE LA RONDA\nPerdiste \nPérdida: -$" + Math.abs(amount));
            }
            case "PUSH" -> {
                mainFrame.showMessage("RESULTADO DE LA RONDA\nEmpate\n Sin cambios en el balance");
            }
        }
        mainFrame.getMainPanel().getGamePanel().getBottomPanel().getActionsPanel().getTokens().setTokens(newBalance);
        mainFrame.clearCrupierCards();
        mainFrame.clearPlayerCards();
    }

    public void showMessage(String message) {
        System.out.println(">>> " + message);
    }
}