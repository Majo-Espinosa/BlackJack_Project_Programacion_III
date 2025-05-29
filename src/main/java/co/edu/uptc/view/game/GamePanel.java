package co.edu.uptc.view.game;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.client.GameClient;
import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.game.crupier.CrupierPanel;
import co.edu.uptc.view.game.draw.RoundedBorder;
import co.edu.uptc.view.game.players.BottomPanel;
import co.edu.uptc.view.game.players.PlayersPanel;
import co.edu.uptc.view.popups.ClosePanel;
import co.edu.uptc.view.reusable.Constants;

public class GamePanel extends JPanel {
    private final MainFrame frame;
    private MainPanel mainPanel;
    private final CrupierPanel crupierPanel;
    private final PlayersPanel playersPanel;
    private final BottomPanel bottomPanel;
    private final ClosePanel closePanel;

    public GamePanel(MainFrame frame, MainPanel mainPanel, GameClient client) {
        this.frame = frame;
        this.mainPanel = mainPanel;
        closePanel = new ClosePanel(mainPanel);
        crupierPanel = new CrupierPanel(this);
        playersPanel = new PlayersPanel();
        bottomPanel = new BottomPanel(this, frame,client);

        initComponents();
    }

    public void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(crupierPanel);
        addDecorations();
        add(playersPanel);
        add(bottomPanel);
        setOpaque(false);
    }

    public void addDecorations() {
        JLabel decoration1 = new JLabel(GameConstants.CRUPIER_RULE_2);
        JLabel decoration2 = new JLabel(GameConstants.CRUPIER_RULE_3);
        JLabel decoration3 = new JLabel(GameConstants.CRUPIER_RULE_2);

        decoration1.setBorder(new RoundedBorder(15));
        decoration2.setBorder(new RoundedBorder(15));
        decoration3.setBorder(new RoundedBorder(15));

        decoration1.setHorizontalAlignment(SwingConstants.CENTER);
        decoration2.setHorizontalAlignment(SwingConstants.CENTER);
        decoration3.setHorizontalAlignment(SwingConstants.CENTER);

        decoration1.setFont(Constants.CUSTOM_FONT.deriveFont(12f));
        decoration2.setFont(Constants.CUSTOM_FONT.deriveFont(12f));
        decoration3.setFont(Constants.CUSTOM_FONT.deriveFont(12f));

        decoration1.setForeground(new Color(255,255,255, 184));
        decoration2.setForeground(new Color(255,255,255, 184));
        decoration3.setForeground(new Color(255,255,255, 184));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 120, 0));
        panel.setOpaque(false);
        panel.add(decoration1);
        panel.add(decoration2);
        panel.add(decoration3);

        add(panel);
    }

    public void openClosePopup() {
        closePanel.showPopUp(false);
    }

    public void openRulesMenu() {
        mainPanel.openRulesPopup();
    }

    public BottomPanel getBottomPanel(){
        return bottomPanel;
    }

    public void resetTimer() {
        crupierPanel.resetTimer();
    }

    public void addCrupierCards(String cards) {
        crupierPanel.addMultipleCards(cards);
    }

    public void addPlayerCards(String cards) {
        playersPanel.addCards(cards);
    }

    public void clearCrupierCards() {
        crupierPanel.clearCards();
    }

    public void clearPlayerCards() {
        playersPanel.clearCards();
    }
}
