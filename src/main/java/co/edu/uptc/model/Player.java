package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private String id;
    private int balance;
    private List<Hand> hands;
    private int currentBet;
    private boolean finished;

    public Player(String id) {
        this.id = id;
        this.balance = 1000; // Balance inicial
        this.hands = new ArrayList<>();
        this.hands.add(new Hand()); // Mano principal
        this.currentBet = 0;
        this.finished = false;
    }

    public void addCard(Card card) {
        if (!hands.isEmpty()) {
            hands.get(0).addCard(card);
        }
    }

    public int getHandValue() {
        return hands.isEmpty() ? 0 : hands.get(0).getValue();
    }

    public boolean isBlackjack() {
        return !hands.isEmpty() && hands.get(0).isBlackjack();
    }

    public boolean isBusted() {
        return !hands.isEmpty() && hands.get(0).isBusted();
    }

    public void clearHands() {
        hands.clear();
        hands.add(new Hand());
        currentBet = 0;
        finished = false;
    }

    public void setBet(int amount) {
        this.currentBet = amount;
    }

    public Map<String, Object> toMap() {
        return Map.of(
            "id", id,
            "balance", balance,
            "currentBet", currentBet,
            "hands", hands.stream().map(Hand::toMap).toList(),
            "finished", finished
        );
    }
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }
    public int getCurrentBet() { return currentBet; }
    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }
    public List<Hand> getHands() { return hands; }
}