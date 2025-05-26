package co.edu.uptc.view.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MenuIconsPanel extends  JPanel {
    
    private final Image icon;

    public MenuIconsPanel(String iconSource){

         icon =  new ImageIcon(getClass().getResource(iconSource)).getImage();
        this.setBackground(Color.BLUE);
    }

      @Override
    public void paintComponent(Graphics g) {
       super.paintComponents(g);
        g.drawImage(icon, 0 , 0 , getWidth() , getHeight() , this);
        
       
    }

}
