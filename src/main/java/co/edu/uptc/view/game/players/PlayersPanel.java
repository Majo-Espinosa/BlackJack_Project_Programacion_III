package co.edu.uptc.view.game.players;

import co.edu.uptc.view.game.CardImage;
import co.edu.uptc.view.reusable.Constants;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PlayersPanel extends JPanel {

    private GridBagConstraints gbc;
    private JLabel decoration1, decoration2, decoration3;
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;
    private JPanel playerTokens;
    private ActionsPanel actionsPanel;

    public PlayersPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initComponents();
        firstLine();
    }

    private void initComponents() {
        playerTokens = new TokensPanel();
        actionsPanel = new ActionsPanel();

        leftPlayerPanel = new PlayerPanel("Waiting...");
        leftPlayerPanel.addCard(0,0);

        centerPlayerPanel = new PlayerPanel("Waiting...");
        centerPlayerPanel.addCard(0,1);
        centerPlayerPanel.addCard(1,1);

        rightPlayerPanel = new PlayerPanel("Waiting...");
        rightPlayerPanel.addCard(0,2);
        rightPlayerPanel.addCard(1,2);
        rightPlayerPanel.addCard(2,2);

        leftPlayerPanel.setOpaque(false);
        centerPlayerPanel.setOpaque(false);
        rightPlayerPanel.setOpaque(false);

        initLabels();
    }

    private void initLabels() {
        decoration1 = new JLabel("Paga 2 a 1");
        decoration2 = new JLabel("SEGURO");
        decoration3 = new JLabel("Paga 2 a 1");

        decoration1.setBorder(new RoundedBorder(15));
        decoration2.setBorder(new RoundedBorder(15));
        decoration3.setBorder(new RoundedBorder(15));

        decoration1.setHorizontalAlignment(SwingConstants.CENTER);
        decoration2.setHorizontalAlignment(SwingConstants.CENTER);
        decoration3.setHorizontalAlignment(SwingConstants.CENTER);

        decoration1.setFont(Constants.CUSTOM_FONT.deriveFont(12f));
        decoration2.setFont(Constants.CUSTOM_FONT.deriveFont(12f));
        decoration3.setFont(Constants.CUSTOM_FONT.deriveFont(12f));

        decoration1.setForeground(new Color(255,255,255, 205));
        decoration2.setForeground(new Color(255,255,255, 205));
        decoration3.setForeground(new Color(255,255,255, 205));
    }

    private void firstLine() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 120, 0));
        panel.setOpaque(false);
        panel.add(decoration1);
        panel.add(decoration2);
        panel.add(decoration3);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 0, 20, 0);

        add(panel, gbc);

        secondLine();
    }

    private void secondLine() {
        gbc.gridwidth = 1;

        add(leftPlayerPanel, gbc);

        add(centerPlayerPanel, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(rightPlayerPanel, gbc);
        gbc.gridwidth = 1;

        thirdLine();
    }

    private void thirdLine() {
        gbc.ipadx = 30;
        gbc.ipady = 35;
        gbc.gridy = 2;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;

        fourthLine();
    }

    private void fourthLine() {
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        add(actionsPanel, gbc);

        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(playerTokens, gbc);
    }
}
