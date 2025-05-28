package co.edu.uptc.clientUs.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import co.edu.uptc.clientUs.game.draw.CardImage;

public class CardsPanel extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();
    private ArrayList<CardImage> cards = new ArrayList<>();

    public CardsPanel(Dimension dimension) {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(dimension);
        setAlignmentY(Component.TOP_ALIGNMENT);
    }

    public void addCard(int row, int column) {
        CardImage label = new CardImage(row, column);
        add(label);
    }

    public void addHiddenCard() {
        CardImage label = new CardImage();
        add(label);
    }

    private void add(CardImage label) {
        cards.add(label);

        if (cards.size() > 3) {
            removeAll();
            repaintMultipleCards();
        } else {
            gbc.ipadx = 5;
            gbc.ipady = 5;
            add(label, gbc);
            revalidate();
            repaint();
        }
    }

    private void repaintMultipleCards() {
        for (int i = 0; i < cards.size(); i++) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, (cards.size()*20)-(i*20) , 0, i*30);

            add(cards.get(i), gbc);
        }
    }

    public void clearCards() {
        cards.clear();
        removeAll();
        revalidate();
        repaint();
    }
}
