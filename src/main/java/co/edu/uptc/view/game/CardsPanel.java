package co.edu.uptc.view.game;

import javax.swing.*;
import java.awt.*;

public class CardsPanel extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();

    public CardsPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(200, 100));
    }

    public void addCard(int row, int column) {
        JLabel label = new CardImage(row, column);
        gbc.insets = new Insets(0, 5, 0, 5);
        add(label, gbc);
        revalidate();
        repaint();
    }

    public void clearCards() {
        removeAll();
        revalidate();
        repaint();
    }
}
