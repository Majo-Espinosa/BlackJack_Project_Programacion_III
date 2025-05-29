package co.edu.uptc.view.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import co.edu.uptc.view.game.draw.CardImage;

public class CardsPanel extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private ArrayList<CardImage> cards = new ArrayList<>();

    public CardsPanel(Dimension dimension) {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(dimension);
        setAlignmentY(Component.TOP_ALIGNMENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 5;
        gbc.ipady = 5;
    }

    public void addCard(String path) {
        CardImage label = new CardImage(path);
        add(label);
    }

    public void addHiddenCard() {
        CardImage label = new CardImage();
        add(label);
    }

    private void add(CardImage label) {
        label.setParentPanel(this);
        cards.add(label);

        if (cards.size() > 3) {
            removeAll();
            repaintMultipleCards();
        } else {
            gbc.insets = new Insets(0, cards.size() * 20, 0, 0);
            add(label, gbc);
            revalidate();
            repaint();
        }
    }

    private void repaintMultipleCards() {
        for (int i = 0; i < cards.size(); i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, -i * 60, 0, 0);
            add(cards.get(i), gbc);
        }
        revalidate();
        repaint();
    }

    public void addMultipleCards(String cards) {
        this.cards.clear();
        removeAll();
        CardImage cardToAdd;
        String[] cardNames = cards.split(", ");
        for (String name : cardNames) {
            name = name.replaceAll("\\[", "");
            name = name.replaceAll("]", "");
            if (name.equals("CARTA OCULTA")) {
                cardToAdd = new CardImage();
            } else {
                cardToAdd = new CardImage(name);
            }
            cardToAdd.setParentPanel(this);
            this.cards.add(cardToAdd);
        }

        if (this.cards.size() > 3) {
            repaintMultipleCards();
        } else {
            for (int i = 0; i < this.cards.size(); i++) {
                gbc.gridx = i;
                gbc.insets = new Insets(0, 0, 0, 0);
                add(this.cards.get(i), gbc);
            }
        }
        revalidate();
        repaint();
    }

    public void clearCards() {
        cards.clear();
        removeAll();
        revalidate();
        repaint();
    }

    public void bringToFront(CardImage card) {
        this.setComponentZOrder(card, 0);
        revalidate();
        repaint();
    }

    public void restoreZOrder(CardImage card, int index){
        setComponentZOrder(card, index);
        revalidate();
        repaint();
    }

}
