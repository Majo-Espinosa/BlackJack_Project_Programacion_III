package co.edu.uptc.server;

import java.util.ArrayList;
import java.util.List;

public class Crupier {
    private List<Card> crupierCards;
    private int cardsValue;
    private boolean win;

    public Crupier() {
        crupierCards = new ArrayList<>();
        win = true;
    }

    // reparte una carta a la mano del crupier
    public void addCard(Card card) {
        crupierCards.add(card);
        crupierDecision();
        if (cardsValue > 21) {
            win = false;
        }
    }

    // eleccion de juego (tomar AS como 1 u 11)
    public void crupierDecision() {
        int[] possibleValues = countCardsValue();
        if (cardsValue == 21 && crupierCards.size() == 2) {
            return;
        }
        if (possibleValues.length == 0) {
            win = false;
        } else if (possibleValues.length == 1) {
            cardsValue = possibleValues[0];
        } else {
            int select = RandomUtil.getRandomNumber(possibleValues.length - 1);
            cardsValue = possibleValues[select];
        }

    }

    // posibles valores de la baraja si hay mas de un AS
    public int[] countCardsValue() {
        int sum = 0;
        int numberOfAs = countNumberOfAs();

        for (Card card : crupierCards) {
            sum += card.getValue();
        }

        if (numberOfAs == 0) {
            return new int[] { sum };
        }

        int[] possibleValues = new int[numberOfAs + 1];
        possibleValues[0] = sum;
        for (int i = 1; i <= numberOfAs; i++) {
            sum -= 10;
            possibleValues[i] = sum;
        }

        return positiveValues(possibleValues);
    }

    // Valores en donde el crupier no pierde
    public int[] positiveValues(int[] possibleValues) {
        List<Integer> positiveValues = new ArrayList<>();
        int positive = 0;
        for (int value : possibleValues) {
            if (value <= 21) {
                positiveValues.add(value);
                positive++;
            }
        }
        if (positive == 0) {
            return new int[0];
        }
        return positiveValues.stream().mapToInt(Integer::intValue).toArray();
    }

    // cuenta el numero de AS en la baraja
    public int countNumberOfAs() {
        int number = 0;
        for (Card card : crupierCards) {
            if (card.getRank() == 'A') {
                number++;
            }
        }
        return number;
    }

    // Valores en 0

    public void resetCrupier() {
        crupierCards.clear();
        cardsValue = 0;
        win = true;
    }

    public List<Card> getCrupierCards() {
        return crupierCards;
    }

    public void setCrupierCards(List<Card> crupierCards) {
        this.crupierCards = crupierCards;
    }

    public int getCardsValue() {
        return cardsValue;
    }

    public void setCardsValue(int cardsValue) {
        this.cardsValue = cardsValue;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean crupiersWin) {
        this.win = crupiersWin;
    }

    @Override
    public String toString() {
        return "Crupier [crupierCards=" + crupierCards + ", cardsValue=" + cardsValue + " is win: " + isWin();
    }

}
