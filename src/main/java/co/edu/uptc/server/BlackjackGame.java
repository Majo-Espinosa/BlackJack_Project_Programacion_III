package co.edu.uptc.server;

import java.util.LinkedList;
import java.util.Queue;

public class BlackjackGame {

    private Queue<Player> players;
    private Crupier crupier;
    private Deck deck;
    private boolean inGame;

    public BlackjackGame() {
        players = new LinkedList<>();
        generateDeck();
        inGame = false;
        crupier = new Crupier();
    }

    public void generateDeck() {
        deck = new Deck((int) NumericalConstraints.DECK_SIZE.getValue());
    }

    public boolean addPlayer(Player player) {
        if (players.size() <= 3) {
            player.setPosition(players.size());
            players.add(player);
            return true;
        }
        return false;
    }

    // reparte a cada jugador sus cartas, y luego al cruper
    public void initialDeal() {
        int size = players.size();
        for (int i = 0; i < size; i++) {
            Player player = players.poll();
            dealCards(2, player);
            players.add(player);
        }
        dealCards(2);
    }

    // Reparte una carta al jugador seleccionado
    public void dealCards(int numberOfCards, Player player) {
        for (int i = 0; i < numberOfCards; i++) {
            player.dealtCard(deck.dealCard());
        }

    }

    // Reparte cartas al crupier
    public void dealCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            crupier.addCard(deck.dealCard());
        }

    }

    // MOVIMIENTOS DEL JUGADOR

    // Apostar o pedir carta
    public boolean playerHit() {
        Player player = players.peek();
        player.dealtCard(deck.dealCard());
        if (!player.isInGame()) {
            players.poll();
            players.add(player);
            return false;
        }
        return true;
    }

    // Mantenerse
    public void playerStay() {
        Player player = players.poll();
        if (player != null) {
            player.setInGame(false);
            players.add(player);
        }
    }

    // dividir mano
    public void playerDivide() {
        Player player = players.poll();
        if (player != null) {
            player.setInGame(false);
            player.splitHand(deck.dealCard(), deck.dealCard());
            players.add(player);
        }
    }

    // MOVIMIENTOS DEL CRUPIER
    public void crupierGame() {
        while (crupier.getCardsValue() < 17) {
            dealCards(1);
        }
        if (!crupier.isWin()) {
            inGame = false;
            crupiersLose();
        } else {
            inGame = false;
            crupierPay();
        }
    }

    // pagar las apuestas
    public void crupierPay() {
        double points = 0;
        for (Player player : players) {
            points = player.getPoints();
            if (player.isSplit()) {
                splitPay(player);
                continue;
            }
            if (crupier.getCardsValue() < player.cardsValue()[0]) {
                player.setPoints(points + (player.getBet()));
            }
            if (crupier.getCardsValue() == 21 && player.cardsValue()[0] == 21 && player.blackJack()
                    && crupier.getCrupierCards().size() > 2) {
                player.setPoints(points + player.getBet());
            }
            if (crupier.getCardsValue() > player.cardsValue()[0]) {
                player.setPoints(points - player.getBet());
            }
        }

    }

    // PAgar mano dividida
    public void splitPay(Player player) {
        double point = player.getPoints();
        int[] handsValue = player.cardsValue();
        for (int i = 0; i < handsValue.length; i++) {
            if (crupier.getCardsValue() > handsValue[i]) {
                player.setPoints(point - (player.getBet() / 2));
            } else {
                player.setPoints(point + (player.getBet() / 2));
            }
        }
    }

    // si el crupier se pasa
    public void crupiersLose() {
        for (Player player : players) {
            double points = player.getPoints();
            if (player.isInPay()) {
                player.setPoints(points + (player.getBet()));
            } else {
                player.setPoints(points - (player.getBet()));
            }
        }

    }

    // Reiniciar el juego
    public void resetGame() {
        for (Player player : players) {
            player.resetGame();
        }
        inGame = true;
        generateDeck();
        crupier.resetCrupier();
    }

    public Queue<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Queue<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Crupier getCrupier() {
        return crupier;
    }

    public void setCrupier(Crupier crupier) {
        this.crupier = crupier;
    }

}
