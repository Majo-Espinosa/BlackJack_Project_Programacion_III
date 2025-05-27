package co.edu.uptc.model;

import java.util.Collections;
import java.util.Stack;

//Clase que representa la baraja
public class Deck {
    private Stack<Card> cards;

    //Constructor de una baraja
    public Deck() {
        cards = new Stack<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int[] values = {2,3,4,5,6,7,8,9,10,10,10,10,11};

        //Se crean cuatro barajas, cada una con una categoria y todos los rangos
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.push(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }

    //usa el metodo shuffle de la clase collections para barajar
    public void shuffle() {
        Collections.shuffle(cards);
    }

    //"Saca" la carta que se encuentra en la parte superior
    public Card draw() {
        return cards.pop();
    }
}
