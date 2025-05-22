package co.edu.uptc.view.menu;

import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.reusable.ImageButton;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {



    private MainPanel mainPanel;
    private GridBagConstraints gbc;
    private ImageButton play;
    private ImageButton rules;
    private ImageButton exit;

    public MenuPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        initComponents();
        this.setLayout(new GridBagLayout());

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.ipadx = 170;
        gbc.ipady = 40;
        gbc.insets = new Insets(200, 0, 10, 0);
        this.add(play, gbc);
        gbc.insets = new Insets(10, 0, 10, 0);
        this.add(rules, gbc);
        this.add(exit, gbc);

        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

    }

    private void initComponents() {
        gbc = new GridBagConstraints();
        play = new ImageButton("Jugar", false, 16);
        rules = new ImageButton("Reglas", true, 16);
        exit = new ImageButton("Salir", false, 16);

        play.addActionListener(e -> mainPanel.updatePanel("login", true));
        rules.addActionListener(e -> mainPanel.openRulesPopup());
        exit.addActionListener(e -> mainPanel.openClosePopup());
    }

}
