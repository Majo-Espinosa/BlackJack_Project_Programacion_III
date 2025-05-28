package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.view.game.CardsPanel;
import co.edu.uptc.view.game.GameConstants;
import co.edu.uptc.view.reusable.Constants;

public class PlayerPanel extends JPanel {
    private final JLabel name;
    private final CardsPanel cardsPanel;
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final TokensPanel betTokens;

    public PlayerPanel(String name) {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        this.name = new JLabel(name);
        betTokens = new TokensPanel();
        cardsPanel = new CardsPanel(GameConstants.PLAYER_CARDS_DIMENSION);

        initComponents();
        addComponents();
    }

    public final void initComponents() {

        setLayout(new GridBagLayout());
        setOpaque(false);
        setAlignmentY(Component.TOP_ALIGNMENT);


        name.setFont(Constants.CUSTOM_FONT.deriveFont(10f));
        name.setBackground(new Color(49,41,41,255));
        name.setOpaque(true);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setForeground(Color.WHITE);
        name.setBorder(BorderFactory.createLineBorder(new Color(238,189,138,255), 2, true));
    }

    public final void addComponents() {

         gbc.gridy = 0;
         gbc.insets = new Insets(10, 0, 0, 0);
        add(betTokens,gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 1;
        gbc.ipady = 50;
        gbc.ipadx = 180;
        add(cardsPanel, gbc);

        gbc.gridy = 2;
        gbc.ipadx = 30;
        gbc.ipady = 30;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(name, gbc);
    }

    public void addCard(int row, int column) {
        cardsPanel.addCard(row, column);
    }

    public void clearCards() {
        cardsPanel.clearCards();
    }
}
