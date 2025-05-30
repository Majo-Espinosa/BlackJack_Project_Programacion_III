package co.edu.uptc.view.game.crupier;

import co.edu.uptc.view.game.CardsPanel;
import co.edu.uptc.view.game.GameConstants;
import co.edu.uptc.view.game.GamePanel;
import co.edu.uptc.view.game.draw.CardImage;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.reusable.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrupierPanel extends JPanel {
    private final GamePanel gamePanel;
    private final GridBagConstraints gbc;
    private JLabel crupierLabel, timerLabel, ruleLabel, leftPileLabel, rightPileLabel;
    private Timer timer;
    private JButton pauseButton, helpButton;
    private CardsPanel cardsPanel;

    public CrupierPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

        timerLabel = new JLabel("-- s", SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timerLabel.setOpaque(true);
        timerLabel.setFont(Constants.CUSTOM_FONT.deriveFont(15f));
        timerLabel.setBackground(new Color(49, 41, 41, 255));
        timerLabel.setForeground(Color.WHITE);

        ruleLabel = new JLabel(GameConstants.CRUPIER_RULE_1, SwingConstants.CENTER);
        ruleLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{7, 5}, 0), new Color(25, 45, 34, 255)));
        ruleLabel.setOpaque(true);
        ruleLabel.setFont(Constants.CUSTOM_FONT.deriveFont(13f));
        ruleLabel.setBackground(new Color(181, 190, 185, 255));
        ruleLabel.setForeground(Color.YELLOW);
    }

    public boolean initTimer(int initialSeconds) {
        final int[] secondsCounter = new int[] {initialSeconds};
        final boolean[] isTimeDone = {false};
        
        timer = new Timer(GameConstants.TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsCounter[0] >= 0) {
                    if (secondsCounter[0] < 10) {
                        timerLabel.setText("0" + secondsCounter[0] + " s");
                    } else {
                        timerLabel.setText(secondsCounter[0] + " s");
                    }
                    secondsCounter[0]--;
                } else {
                    timer.stop();
                    isTimeDone[0] = true;
                }
            }
        });
        timer.start();
        return isTimeDone[0];
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

    public void addMultipleCards(String cards) {
        cardsPanel.addMultipleCards(cards);
    }

    public void clearCards() {
        this.cardsPanel.clearCards();
    }
}