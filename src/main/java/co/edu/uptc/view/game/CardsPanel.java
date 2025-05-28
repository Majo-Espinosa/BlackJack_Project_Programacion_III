package co.edu.uptc.view.game;

import co.edu.uptc.view.game.draw.CardImage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
            gbc.insets = new Insets(0, 0, 0, i*30);

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
