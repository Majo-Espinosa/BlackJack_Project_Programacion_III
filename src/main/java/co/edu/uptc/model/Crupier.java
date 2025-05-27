package co.edu.uptc.model;

public class Crupier extends Player{
    public Crupier() {
        super("dealer");
    }

    public void playTurn(Deck deck) {
        while (getHandValue() < 17) {
            addCard(deck.draw());
        }
    }
}
