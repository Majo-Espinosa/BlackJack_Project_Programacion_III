package co.edu.uptc.view;

import java.net.URL;
import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;


    public MainFrame() {
        super("Blackjack!");
        initComponents();
        setSize(1080, 720);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setIcon();
    }

    private void setIcon() {
        URL iconURL = getClass().getResource("/images/icons/logo.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            this.setIconImage(icon.getImage());
        }
    }

    public final void initComponents() {
        mainPanel = new MainPanel();
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

}
