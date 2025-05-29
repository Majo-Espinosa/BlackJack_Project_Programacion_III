<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/game/players/BottomPanel.java
package co.edu.uptc.view.game.players;
========
package co.edu.uptc.client.game.players;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/game/players/BottomPanel.java

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class BottomPanel extends JPanel{
    
    private final ActionsPanel actionsPanel;

    public BottomPanel() {
        this.actionsPanel = new ActionsPanel();
        this.setOpaque(false);
        initcomponents();
        addComponents();
    }

    private void initcomponents(){
        this.setLayout(new FlowLayout());

    }

    private void addComponents(){
        this.add(actionsPanel);
    }
}
