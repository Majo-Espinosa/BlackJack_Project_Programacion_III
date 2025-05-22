package co.edu.uptc.view.game;

import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.game.crupier.CrupierPanel;
import co.edu.uptc.view.game.players.PlayersPanel;
import co.edu.uptc.view.popups.ClosePanel;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MainPanel mainPanel;
    private CrupierPanel crupierPanel;
    private PlayersPanel playersPanel;
    private ClosePanel closePanel;

    public GamePanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        closePanel = new ClosePanel(mainPanel);
        crupierPanel = new CrupierPanel(this);
        playersPanel = new PlayersPanel();
        setLayout(new BorderLayout());
        add(crupierPanel, BorderLayout.NORTH);
        add(playersPanel, BorderLayout.CENTER);
        setOpaque(false);
    }

    public void openClosePopup() {
        closePanel.showPopUp(false);
    }

//    public void openRulesMenu() {
//        mainPanel.openRulesPopup();
//    }
}
