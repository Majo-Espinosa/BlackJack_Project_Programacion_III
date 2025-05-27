package co.edu.uptc.model;

//Clase crupier que hereda de jugador
public class Crupier extends Player{
    public Crupier() {
        super("dealer");
        super.setBalance(50000);
    }

    public void playTurn(Deck deck) {
        while (getHandValue() < 17) {
            addCard(deck.draw());
        }
    }
}
