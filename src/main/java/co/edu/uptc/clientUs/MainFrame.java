package co.edu.uptc.clientUs;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import co.edu.uptc.clientUs.reusable.Constants;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private GameClient client;

    public MainFrame(GameClient client) {
        super(Constants.FRAME_TITLE);
        this.client = client;
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
        mainPanel = new MainPanel(this.client);
        setContentPane(mainPanel);
    }

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        client.start();
        MainFrame mainFrame = new MainFrame(client);
        
    }

}
