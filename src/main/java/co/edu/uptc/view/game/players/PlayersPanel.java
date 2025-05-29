package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlayersPanel extends JPanel {
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;

    public PlayersPanel() {
        setOpaque(false);
        setLayout(new GridLayout(0, 3));
        initComponents();
        firstLine();
    }

    private void initComponents() {
        leftPlayerPanel = new PlayerPanel("Waiting...");
        centerPlayerPanel = new PlayerPanel("Waiting...");
        rightPlayerPanel = new PlayerPanel("Waiting...");
    }

    private void firstLine() {
        add(leftPlayerPanel);
        add(centerPlayerPanel);
        add(rightPlayerPanel);
    }

    public void addCards(String cards) {
        centerPlayerPanel.addCards(cards);
    }

    public void clearCards() {
        centerPlayerPanel.clearCards();
    }
}
