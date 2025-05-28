package co.edu.uptc.view.game.players;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    private GridBagConstraints gbc;
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;
    private ActionsPanel actionsPanel;

    public BottomPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initComponents();
        firstLine();
    }

    private void initComponents() {
        actionsPanel = new ActionsPanel();

        leftPlayerPanel = new PlayerPanel("Waiting...");
        leftPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

        centerPlayerPanel = new PlayerPanel("Waiting...");
        centerPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        centerPlayerPanel.addCard(0,1);
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

        secondLine();
    }

    private void secondLine() {
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;

        gbc.insets = new Insets(20, 10, 0, 10);
        gbc.anchor = GridBagConstraints.LAST_LINE_START;

        add(actionsPanel, gbc);
    }
}
