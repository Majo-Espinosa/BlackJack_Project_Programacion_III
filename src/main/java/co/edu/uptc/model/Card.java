package co.edu.uptc.model;

public class Card {
    private char suit;
    private char rank;
    private int value;
    private boolean state;
    private String name;

    public Card(char suit, char rank) {
        state = false;
        this.suit = suit;
        this.rank = rank;
    }

    public char getSuit() {
        return suit;
    }

    public void setSuit(char suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Card  rank=" + rank + " value=" + value;
    }

}
