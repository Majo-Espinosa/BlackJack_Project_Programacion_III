package co.edu.uptc.client;

import java.net.URL;
import javax.swing.*;

import co.edu.uptc.client.reusable.Constants;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;


    public MainFrame() {
        super(Constants.FRAME_TITLE);
        initComponents();
        setSize(Constants.WINDOW_DIMENSION);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setIcon();
    }

    private void setIcon() {
        URL iconURL = getClass().getResource(Constants.FRAME_ICON_PATH);
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
