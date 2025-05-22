package co.edu.uptc.model;

import java.util.Arrays;
import java.util.List;

public class Player {
    private String nickName;
    private PlayerHand hand;
    private double points;
    private double bet;
    private boolean inGame;
    private boolean inPay;

    private int position;

    public Player(String nickName, double points) {
        this.nickName = nickName;
        this.points = points;
        inGame = true;
        inPay = true;
        hand = new PlayerHand();
    }

    public Player() {
        inGame = true;
        inPay = true;
        hand = new PlayerHand();
    }

    // Pide una carta
    public void dealtCard(Card card) {
        if (!hand.addCard(card)) {
            inPay = false;
            inGame = false;
        }
        if (hand.getPlayerCards().size() == 2) {
            hand.checkBlackJack();
            if (blackJack()) {
                inGame = false;
            }
        }
    }

    // Divide la mano
    public boolean splitHand(Card card1, Card card2) {
        if (hand.isSpliteable()) {
            hand.splitHand(card1, card2);
            return true;
        }
        return false;
    }

    public int[] cardsValue() {
        return hand.handsValue();
    }

    public void resetGame() {
        hand.clearHand();
        bet = 0;
        inGame = true;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public List<Card> getCardsInGame() {
        return hand.getPlayerCards();
    }

    public List<Card> getCardsInSecondHand() {
        return hand.getPlayerSecondHand();
    }

    public boolean isInGame() {
        return inGame;
    }

    public boolean blackJack() {
        return hand.isBlackJack();
    }

    public boolean isSplit() {
        return hand.isSplit();
    }

    public boolean canSplit() {
        return hand.isSpliteable();
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isInPay() {
        return inPay;
    }

    public void setInPay(boolean inPay) {
        this.inPay = inPay;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player : " + hand + " value=" + Arrays.toString(
                hand.handsValue()) + " inGame=" + inGame + " inPay=" + inPay;
    }

}
