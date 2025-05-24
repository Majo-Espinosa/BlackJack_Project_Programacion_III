package co.edu.uptc.view.login;

import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.reusable.ImageButton;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private MainPanel mainPanel;
    private JButton play, backToMenu;
    private UsernameTextField usernameTextField;
    private GridBagConstraints gbc = new GridBagConstraints();

    public LoginPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        initComponents();
        addComponents();
    }

    public void initComponents() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        usernameTextField = new UsernameTextField();
        play = new ImageButton("Entrar a la sala", false,  16);
        backToMenu = new ImageButton("Volver", false, 16);

        play.addActionListener(e -> mainPanel.updatePanel("game", false));
        backToMenu.addActionListener(e -> mainPanel.updatePanel("menu", true));
    }

    public void addComponents() {
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(200, 20, 0, 20);
        gbc.gridy = 1;
        add(usernameTextField, gbc);
        addButtons();

    }

    public void addButtons() {
        gbc.insets = new Insets(30, 15, 30, 15);
        gbc.ipadx = 70;
        gbc.ipady = 40;
        gbc.gridy = 2;
        add(play, gbc);
        gbc.gridy = 3;
        add(backToMenu, gbc);
    }

}
