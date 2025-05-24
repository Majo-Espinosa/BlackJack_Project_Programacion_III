package co.edu.uptc.view.game.players;

import co.edu.uptc.view.game.CardImage;
import co.edu.uptc.view.reusable.Constants;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PlayersPanel extends JPanel {

    private GridBagConstraints gbc;
    private JLabel leftPlayerLabel, centerPlayerLabel, rightPlayerLabel, decoration1, decoration2, decoration3;
    private JPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel, playerTokens;
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

        leftPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel c0 = new CardImage(1,1);
        leftPlayerPanel.add(c0);

        centerPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel c1 = new CardImage(0,13);
        centerPlayerPanel.add(c1);

        rightPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel c2 = new CardImage(4,2);
        rightPlayerPanel.add(c2);

        leftPlayerPanel.setOpaque(false);
        centerPlayerPanel.setOpaque(false);
        rightPlayerPanel.setOpaque(false);

        initLabels();
    }

    private void initLabels() {
        leftPlayerLabel = new JLabel("Jugador Izq");
        centerPlayerLabel = new JLabel("Jugador Central");
        rightPlayerLabel = new JLabel("Jugador Der");

        leftPlayerLabel.setBackground(new Color(49,41,41,255));
        leftPlayerLabel.setOpaque(true);
        leftPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPlayerLabel.setBorder(BorderFactory.createLineBorder(new Color(238,189,138,255), 2, true) );
        centerPlayerLabel.setBackground(new Color(49,41,41,255));
        centerPlayerLabel.setOpaque(true);
        centerPlayerLabel.setBorder(BorderFactory.createLineBorder(new Color(238,189,138,255), 2, true) );
        centerPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPlayerLabel.setBackground(new Color(49,41,41,255));
        rightPlayerLabel.setOpaque(true);
        rightPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPlayerLabel.setBorder(BorderFactory.createLineBorder(new Color(238,189,138,255), 2, true) );


        decoration1 = new JLabel("Paga 2 a 1");
        decoration2 = new JLabel("SEGURO");
        decoration3 = new JLabel("Paga 2 a 1");

        decoration1.setBorder(new RoundedBorder(15));
        decoration2.setBorder(new RoundedBorder(15));
        decoration3.setBorder(new RoundedBorder(15));

        decoration1.setHorizontalAlignment(SwingConstants.CENTER);
        decoration2.setHorizontalAlignment(SwingConstants.CENTER);
        decoration3.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void firstLine() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 120, 0));
        panel.setOpaque(false);
        panel.add(decoration1);
        panel.add(decoration2);
        panel.add(decoration3);

        gbc.gridwidth = GridBagConstraints.REMAINDER;

        add(panel, gbc);

        secondLine();
    }

    private void secondLine() {
        gbc.insets = new Insets(5, 100, 5, 100);
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
        add(leftPlayerLabel, gbc);

        add(centerPlayerLabel, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(rightPlayerLabel, gbc);
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
