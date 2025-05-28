package co.edu.uptc.client.game.crupier;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.client.game.CardsPanel;
import co.edu.uptc.client.game.GameConstants;
import co.edu.uptc.client.game.GamePanel;
import co.edu.uptc.client.game.draw.CardImage;
import co.edu.uptc.client.reusable.Constants;
import co.edu.uptc.client.reusable.ImageButton;

public class CrupierPanel extends JPanel {
    private final GamePanel gamePanel;
    private final GridBagConstraints gbc;
    private JLabel crupierLabel, timerLabel, ruleLabel, leftPileLabel, rightPileLabel;
    private JButton pauseButton, helpButton;
    private CardsPanel cardsPanel;

    public CrupierPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setOpaque(false);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        initComponents();
        firstLine();
        crupierLabel.setFont(Constants.CUSTOM_FONT.deriveFont(18f));
        crupierLabel.setForeground(Color.WHITE);
    }

    private void initComponents() {
        initButtons();
        leftPileLabel = new CardImage();
        rightPileLabel = new CardImage();

        cardsPanel = new CardsPanel(GameConstants.CRUPIER_CARDS_DIMENSION);
        for (int i = 0; i < 3; i++) {
            cardsPanel.addHiddenCard();
        }

        initLabels();
    }

    private void initButtons() {
        pauseButton = new ImageButton("X", false, 10);
        helpButton = new ImageButton("?", false, 12);

        pauseButton.addActionListener(e -> {
            gamePanel.openClosePopup();
        });

        helpButton.addActionListener(e -> {
            gamePanel.openRulesMenu();
        });
    }

    private void initLabels() {
        crupierLabel = new JLabel("CRUPIER");

        timerLabel = new JLabel("30 s");
        timerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timerLabel.setOpaque(true);
        timerLabel.setFont(Constants.CUSTOM_FONT.deriveFont(15f));
        timerLabel.setBackground(new Color(49,41,41,255));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ruleLabel = new JLabel(GameConstants.CRUPIER_RULE_1);
        ruleLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{7,5}, 0), new Color(25,45,34,255)));
        ruleLabel.setOpaque(true);
        ruleLabel.setFont(Constants.CUSTOM_FONT.deriveFont(13f));
        ruleLabel.setBackground(new Color(181,190,185,255));
        ruleLabel.setForeground(Color.YELLOW);
        ruleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void firstLine() {
        gbc.ipadx = 0;
        gbc.ipady = 10;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(pauseButton, gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(0, 200, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridheight = 2;
        add(rightPileLabel, gbc);
        gbc.gridheight = 1;

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(crupierLabel, gbc);

        gbc.insets = new Insets(0, 10, 0, 200);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridheight = 2;
        add(leftPileLabel, gbc);
        gbc.gridheight = 1;

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(helpButton, gbc);
        gbc.gridwidth = 1;

        secondLine();
    }

    private void secondLine() {
        // Timer
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 30;
        gbc.ipady = 35;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(timerLabel, gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        add(cardsPanel, gbc);
        gbc.gridx = 0;
        gbc.gridheight = 1;

        thirdLine();
    }

    private void thirdLine() {
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.ipadx = 0;
        gbc.ipady = 25;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(ruleLabel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
    }
}
