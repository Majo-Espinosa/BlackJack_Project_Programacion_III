package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
        this.name = new JLabel(name);
        betTokens = new TokensPanel();
        cardsPanel = new CardsPanel(GameConstants.PLAYER_CARDS_DIMENSION);
        
        initComponents();
        addComponents();
    }

    public final void initComponents() {

        setLayout(new GridLayout(3, 1));
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

        JPanel auxBetTokens = new JPanel();
        auxBetTokens.setOpaque(false);
        auxBetTokens.setLayout(new GridBagLayout());
        auxBetTokens.add(betTokens,gbc);
        auxBetTokens.setPreferredSize(new Dimension(300, 50));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(auxBetTokens);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 1;
        gbc.ipady = 50;
        gbc.ipadx = 180;
        cardsPanel.setPreferredSize(new Dimension(300, 200));
        add(cardsPanel);

        JPanel auxNamPanel = new JPanel();
        gbc.gridy = 2;
        gbc.ipadx = 30;
        gbc.ipady = 30;
        auxNamPanel.setOpaque(false);
        auxNamPanel.setLayout(new GridBagLayout());
        auxNamPanel.add(name,gbc);
        auxNamPanel.setPreferredSize(new Dimension(300, 50));
        
        gbc.insets = new Insets(10, 0, 0, 0);
        add(auxNamPanel);
    }

    public void addCard(String path) {
        cardsPanel.addCard(path);
    }

    public void setUsername(String name) {
        this.name.setText(name);
    }

    public void setTokens(int tokens) {
        betTokens.setTokens(tokens);
    }

    public void addCards(String cards) {
        cardsPanel.addMultipleCards(cards);
    }

    public void clearCards() {
        cardsPanel.clearCards();
    }
}
