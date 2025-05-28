package co.edu.uptc.clientUs.game;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import co.edu.uptc.clientUs.GameClient;
import co.edu.uptc.clientUs.MainPanel;
import co.edu.uptc.clientUs.game.crupier.CrupierPanel;
import co.edu.uptc.clientUs.game.draw.RoundedBorder;
import co.edu.uptc.clientUs.game.players.BottomPanel;
import co.edu.uptc.clientUs.game.players.PlayersPanel;
import co.edu.uptc.clientUs.popups.ClosePanel;
import co.edu.uptc.clientUs.reusable.Constants;

public class GamePanel extends JPanel {
    private final MainPanel mainPanel;
    private final CrupierPanel crupierPanel;
    private final PlayersPanel playersPanel;
    private final BottomPanel bottomPanel;
    private final ClosePanel closePanel;

    public GamePanel(MainPanel mainPanel, GameClient client) {
        this.mainPanel = mainPanel;
        closePanel = new ClosePanel(mainPanel);
        crupierPanel = new CrupierPanel(this);
        playersPanel = new PlayersPanel();
        bottomPanel = new BottomPanel();
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
}
