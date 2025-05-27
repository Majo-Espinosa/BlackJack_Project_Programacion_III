package co.edu.uptc.view.game.players;

import javax.swing.*;
import java.awt.*;

public class PlayersPanel extends JPanel {

    private GridBagConstraints gbc;
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;
    private JPanel playerTokens;
    private ActionsPanel actionsPanel;

    public PlayersPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initComponents();
        firstLine();
    }

    private void initComponents() {
        playerTokens = new TokensPanel();
        actionsPanel = new ActionsPanel();

        leftPlayerPanel = new PlayerPanel("Waiting...");
        leftPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        leftPlayerPanel.addCard(0,0);

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
        gbc.ipadx = 180;
        gbc.ipady = 20;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(leftPlayerPanel, gbc);

        gbc.gridx = 2;
        add(centerPlayerPanel, gbc);

        gbc.gridx = 3;
        add(rightPlayerPanel, gbc);
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
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

        gbc.ipady = 0;
        gbc.gridx = 3;

        gbc.anchor = GridBagConstraints.LAST_LINE_END;

        add(playerTokens, gbc);
    }
}
