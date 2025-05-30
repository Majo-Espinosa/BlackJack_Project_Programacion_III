package co.edu.uptc.model;

import co.edu.uptc.server.ServerController;

import java.util.List;

public class BlackJackGame {


    private List<Player> players;
    private ServerController controller;
    private Deck deck;

    public BlackJackGame(ServerController controller) {
        deck = new Deck();
        this.controller = controller;
    }

    public void actions() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isInTurn()) {
                switch (players.get(i).getAction()) {

                    case "HIT" -> {
                       this.hit(players.get(i));
                       controller.setPlayers(players);
                    }
                    case "STAND" -> {
                        this.stand(players.get(i));
                        controller.setPlayers(players);
                    }
                }

            }
        }

    }

    public void startGame() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getHand().addCard(deck.dealCard());
            players.get(i).getHand().addCard(deck.dealCard());
        }
    }

    public void nextTurn() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isInTurn()) {
                players.get(i).setInTurn(false);
                // If current player is second to last (before dealer)
                if (i == players.size()) {
                    // Skip dealer and give turn to first player
                    players.get(0).setInTurn(true);
                } else {
                    // Give turn to next player
                    players.get(i + 1).setInTurn(true);
                }
                break;
            }
        }
    }



    public void stand(Player player){
        nextTurn();
    }

    public int totalCardValue(Player player){
        return player.getHand().getValue();
    }

    public void hit(Player player) {
        player.getHand().addCard(deck.dealCard());
        nextTurn();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
