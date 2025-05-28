package co.edu.uptc.view;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import co.edu.uptc.view.reusable.Constants;

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

    public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame();
        
    }

}
