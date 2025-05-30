package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hand {
    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getValue() {
        int value = 0;
        int aces = 0;

        for (Card card : cards) {
            value += card.getValue();
            if (card.isAce()) {
                aces++;
            }
        }

        // Ajustar valor de ases
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards.toString() +
                '}';
    }
}
