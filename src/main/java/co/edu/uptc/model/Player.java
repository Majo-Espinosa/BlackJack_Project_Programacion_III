package co.edu.uptc.model;

import java.util.*;

//Clase jugador con su nombre, fichas, apuesta, mano
public class Player {
    private String id;
    private List<Card> hand;
    private int balance;
    private int bet;

    public Player(String id) {
        this.id = id;
        this.hand = new ArrayList<>();
        this.balance = 1000;
        this.bet = 0;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int value = 0, aces = 0;
        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("A")) aces++;
        }
        while (value > 21 && aces-- > 0) {
            value -= 10;
        }
        return value;
    }

    public void clearHand() {
        hand.clear();
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public int getBalance() {
        return balance;
    }

    public void updateBalance(int delta) {
        balance += delta;
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getId() {
        return id;
    }
}
