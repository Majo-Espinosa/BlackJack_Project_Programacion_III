package co.edu.uptc.view.login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import co.edu.uptc.client.GameClient;
import co.edu.uptc.model.Player;
import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.popups.MessageDialog;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.reusable.ImageButton;

public class LoginPanel extends JPanel {
    private final MainPanel mainPanel;
    private JButton play, backToMenu;
    private UsernameTextField usernameTextField;
    private final GridBagConstraints gbc = new GridBagConstraints();

    public LoginPanel(MainPanel mainPanel, GameClient client) {
        this.mainPanel = mainPanel;
        initComponents(client);
        addComponents();
    }

    public final void initComponents(GameClient client) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        usernameTextField = new UsernameTextField();
        play = new ImageButton("Entrar a la sala", false,  16, ImageButton.PLAY_ICON);
        backToMenu = new ImageButton("Volver", false, 16, ImageButton.HOME_ICON);

        play.addActionListener(_ -> {
            if (usernameTextField.getText().isEmpty()) {
                new MessageDialog("Ingresa un nombre de \nusuario para continuar").showPopUp();

            } else {
                mainPanel.updatePanel(Constants.GAME_KEY);
                Player player = client.getPlayer();
                player.setName(usernameTextField.getText());
                client.setPlayer(player);
                client.start();
            }
        }  );
        backToMenu.addActionListener(_ -> mainPanel.updatePanel(Constants.MENU_KEY));
    }

    public final void addComponents() {
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
