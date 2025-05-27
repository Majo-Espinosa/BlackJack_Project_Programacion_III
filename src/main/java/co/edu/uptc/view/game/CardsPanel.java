package co.edu.uptc.view.game;

import javax.swing.*;
import java.awt.*;

public class CardsPanel extends JPanel {
    private GridBagConstraints gbc = new GridBagConstraints();

    public CardsPanel(Dimension dimension) {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(dimension);
        setAlignmentY(Component.TOP_ALIGNMENT);
    }

    public void addCard(int row, int column) {
        JLabel label = new CardImage(row, column);
        gbc.ipadx = 5;
        gbc.ipady = 5;
        gbc.insets = new Insets(0, 10, 0, 10);
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
