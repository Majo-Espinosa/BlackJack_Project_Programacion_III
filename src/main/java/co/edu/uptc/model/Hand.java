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

    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    public boolean isBusted() {
        return getValue() > 21;
    }

    public void clear() {
        cards.clear();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public Map<String, Object> toMap() {
        return Map.of(
            "cards", cards.stream().map(Card::toString).toList(),
            "value", getValue(),
            "isBlackjack", isBlackjack(),
            "isBusted", isBusted()
        );
    }
}
