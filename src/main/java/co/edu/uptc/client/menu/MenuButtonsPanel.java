<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/menu/MenuButtonsPanel.java
package co.edu.uptc.view.menu;
========
package co.edu.uptc.client.menu;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/menu/MenuButtonsPanel.java

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/menu/MenuButtonsPanel.java
import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.reusable.ImageButton;
========
import co.edu.uptc.client.MainPanel;
import co.edu.uptc.client.reusable.Constants;
import co.edu.uptc.client.reusable.ImageButton;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/menu/MenuButtonsPanel.java

public class MenuButtonsPanel extends JPanel {
    
    private GridBagConstraints gbc;
    private final MainPanel mainPanel;
    private ImageButton play;
    private ImageButton rules;
    private ImageButton exit;

    public MenuButtonsPanel( MainPanel mainPanel){
        
        this.mainPanel = mainPanel;
        initComponents();
        this.setLayout(new GridBagLayout());


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.ipadx = 130;
        gbc.ipady = 40;
        gbc.insets = new Insets(0, 0, 20, 0);
        this.add(play, gbc);
        this.add(rules, gbc);
         gbc.insets = new Insets(0, 0, 70, 0);
        this.add(exit, gbc);
        this.setOpaque(false);
    }

    private void initComponents() {
        gbc = new GridBagConstraints();
        play = new ImageButton("Jugar", false, 16, ImageButton.PLAY_ICON);
        rules = new ImageButton("Reglas", true, 16, ImageButton.DARK_QUESTION_MARK_ICON);
        exit = new ImageButton("Salir", false, 16, ImageButton.SKULL_ICON);

        play.addActionListener(_ -> mainPanel.updatePanel(Constants.LOGIN_KEY));
        rules.addActionListener(_ -> mainPanel.openRulesPopup());
        exit.addActionListener(_ -> mainPanel.openClosePopup());
    }

}
