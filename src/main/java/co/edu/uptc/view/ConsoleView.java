package co.edu.uptc.view;

import java.util.Scanner;

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
}

