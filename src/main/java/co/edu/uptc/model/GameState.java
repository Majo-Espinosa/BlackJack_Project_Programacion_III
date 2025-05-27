package co.edu.uptc.model;

import  java.util.*;

public class GameState {
    private Map<String, Player> players = new HashMap<>();
    private Crupier crupier = new Crupier();
    private Deck deck = new Deck();

    public Map<String, Player> getPlayers() { return players; }
    public Crupier getDealer() { return crupier; }
    public Deck getDeck() { return deck; }

    public void resetHands() {
        for (Player player : players.values()) player.clearHand();
        crupier.clearHand();
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public Player getPlayer(String id) {
        return players.get(id);
    }
}
