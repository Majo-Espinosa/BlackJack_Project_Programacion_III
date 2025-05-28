package co.edu.uptc.client.game.players;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CenterPanel extends JPanel {
    private PlayerPanel leftPlayerPanel, centerPlayerPanel, rightPlayerPanel;

    public CenterPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridLayout(0, 3));
        initComponents();
        firstLine();
    }

    private void initComponents() {
        leftPlayerPanel = new PlayerPanel("Waiting...");
        leftPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        leftPlayerPanel.addCard("/images/cards/light/2-C.png");
        leftPlayerPanel.addCard("/images/cards/light/A-C.png");
        leftPlayerPanel.addCard("/images/cards/light/3-D.png");
        leftPlayerPanel.addCard("/images/cards/light/J-P.png");
        leftPlayerPanel.addCard("/images/cards/light/10-H.png");
        leftPlayerPanel.addCard("/images/cards/light/A-C.png");

        centerPlayerPanel = new PlayerPanel("Waiting...");
        centerPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        centerPlayerPanel.addCard("/images/cards/light/A-C.png");
        centerPlayerPanel.addCard("/images/cards/light/A-C.png");

        rightPlayerPanel = new PlayerPanel("Waiting...");
        rightPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        rightPlayerPanel.addCard("/images/cards/light/A-C.png");

        leftPlayerPanel.setOpaque(false);
        centerPlayerPanel.setOpaque(false);
        rightPlayerPanel.setOpaque(false);
    }

    private void firstLine() {
        add(leftPlayerPanel);
        add(centerPlayerPanel);
        add(rightPlayerPanel);
      
    }
}
