<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/MainFrame.java
package co.edu.uptc.view;
========
package co.edu.uptc.client;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/MainFrame.java

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/MainFrame.java
import co.edu.uptc.view.reusable.Constants;
========
import co.edu.uptc.client.reusable.Constants;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/MainFrame.java

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
<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/MainFrame.java
        MainFrame mainFrame = new MainFrame();
        
========
        GameClient client = new GameClient();
        MainFrame mainFrame = new MainFrame(client);
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/MainFrame.java
    }

}
