package co.edu.uptc.view.game.players;

import co.edu.uptc.view.reusable.Constants;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PlayersPanel extends JPanel {

    private GridBagConstraints gbc;
    private JLabel leftPlayerLabel, centerPlayerLabel, rightPlayerLabel, decoration1, decoration2, decoration3;
    private JPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;
    private Image playerTokens;
    private ActionsPanel actionsPanel;
    private Font customFont;

    public PlayersPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        loadCustomFont();
        initComponents();
        firstLine();
        applyFont(this);
    }

    private void loadCustomFont() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream(Constants.FONT_NAME)).deriveFont(15f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.BOLD, 24);
        }
    }

    private void initComponents() {
        playerTokens = new ImageIcon(getClass().getResource("/images/icons/tokens.png")).getImage();
        actionsPanel = new ActionsPanel();

        leftPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel cardLabel = new JLabel(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
        leftPlayerPanel.add(cardLabel);

        centerPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        cardLabel = new JLabel(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
        centerPlayerPanel.add(cardLabel);

        rightPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        cardLabel = new JLabel(new ImageIcon(getClass().getResource("/images/cards/card_back.png")));
        rightPlayerPanel.add(cardLabel);

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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
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

        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(new JLabel(new ImageIcon(playerTokens)), gbc);
    }

    private void applyFont(Component component) {
        if (component instanceof JLabel) {
            component.setFont(customFont);
            component.setForeground(Color.WHITE);
        } else if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyFont(child);
            }
        }
    }
}
