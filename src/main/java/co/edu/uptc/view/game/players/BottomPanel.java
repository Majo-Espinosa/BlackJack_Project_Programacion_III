package co.edu.uptc.view.game.players;

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
