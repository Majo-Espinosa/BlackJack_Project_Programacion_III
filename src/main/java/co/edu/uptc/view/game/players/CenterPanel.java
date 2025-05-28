package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {

    private GridBagConstraints gbc;
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;

    public CenterPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initComponents();
        firstLine();
    }

    private void initComponents() {
        leftPlayerPanel = new PlayerPanel("Waiting...");
        leftPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        leftPlayerPanel.addCard(0, 1);
        leftPlayerPanel.addCard(0, 3);
        leftPlayerPanel.addCard(0, 3);

        centerPlayerPanel = new PlayerPanel("Waiting...");
        centerPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        centerPlayerPanel.addCard(0,1);
        centerPlayerPanel.addCard(1,1);
        centerPlayerPanel.addCard(1,1);

        rightPlayerPanel = new PlayerPanel("Waiting...");
        rightPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        rightPlayerPanel.addCard(0,2);
        rightPlayerPanel.addCard(1,2);
        rightPlayerPanel.addCard(2,2);

        leftPlayerPanel.setOpaque(false);
        centerPlayerPanel.setOpaque(false);
        rightPlayerPanel.setOpaque(false);
    }

    private void firstLine() {
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(leftPlayerPanel, gbc);

        gbc.gridx = 2;
        add(centerPlayerPanel, gbc);

        gbc.gridx = 3;
        add(rightPlayerPanel, gbc);
        gbc.gridwidth = 1;
        gbc.ipady = 10;

    }
}
