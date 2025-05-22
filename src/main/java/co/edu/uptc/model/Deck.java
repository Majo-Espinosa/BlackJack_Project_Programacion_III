package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private Stack<Card> cards;

    public Deck(int size) {
        createDeck(size);
    }

    // se crea un mazo con el numero de barajas que se deseen
    public void createDeck(int SingleDecks) {
        cards = new Stack<>();
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
        String[] suits = { "H", "D", "C", "S" };
        for (int i = 0; i < SingleDecks; i++) {
            for (String rank : ranks) {
                for (String suit : suits) {
                    cards.add(createCard(rank, suit));
                }
            }
        }
        shuffle();
    }

    // Crea una carta
    public Card createCard(String rank, String suit) {
        Card card = new Card(suit.charAt(0), rank.charAt(0));
        card.setName(suit + rank);
        try {
            card.setValue(Integer.parseInt(rank));
        } catch (Exception e) {
            if (!rank.equals("A")) {
                card.setValue(10);
            } else {
                card.setValue(11);
            }
        }
        return card;
    }

    // Mezcla la baraja
    public void shuffle() {
        List<Card> list = new ArrayList<>(cards);
        Collections.shuffle(list);
        Collections.shuffle(list);
        cards.clear();
        cards.addAll(list);
    }

    // Reparte una carta
    public Card dealCard() {
        return cards.pop();
    }

    public int size() {
        return cards.size();
    }

}
