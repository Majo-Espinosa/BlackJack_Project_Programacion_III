package co.edu.uptc.model;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int[] values = {2,3,4,5,6,7,8,9,10,10,10,10,11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.push(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.pop();
    }
}
