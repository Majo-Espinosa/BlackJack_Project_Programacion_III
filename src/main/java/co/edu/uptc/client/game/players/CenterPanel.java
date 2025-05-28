package co.edu.uptc.client.game.players;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;

    public CenterPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridLayout(0, 3));
        initComponents();
        firstLine();
    }

    private void initComponents() {
        leftPlayerPanel = new PlayerPanel("Waiting...");
        leftPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        leftPlayerPanel.addCard(0, 1);
        leftPlayerPanel.addCard(0, 3);
        leftPlayerPanel.addCard(0, 3);
        leftPlayerPanel.addCard(0, 3);
        leftPlayerPanel.addCard(0, 3);
        leftPlayerPanel.addCard(0, 3);

        centerPlayerPanel = new PlayerPanel("Waiting...");
        centerPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        centerPlayerPanel.addCard(0,1);
        centerPlayerPanel.addCard(1,1);

        rightPlayerPanel = new PlayerPanel("Waiting...");
        rightPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        rightPlayerPanel.addCard(0,2);

        leftPlayerPanel.setOpaque(false);
        centerPlayerPanel.setOpaque(false);
        rightPlayerPanel.setOpaque(false);
    }

    private void firstLine() {
        add(leftPlayerPanel);
        add(centerPlayerPanel);
        add(rightPlayerPanel);
      
    }
}
