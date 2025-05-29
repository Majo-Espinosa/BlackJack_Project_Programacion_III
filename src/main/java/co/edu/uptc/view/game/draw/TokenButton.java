package co.edu.uptc.view.game.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import co.edu.uptc.view.reusable.Constants;

public class TokenButton extends JButton {
    
    private BufferedImage image;


    public TokenButton(int x, int y, String text){
        super(text);
            try {
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                image = ImageIO.read(getClass().getResource(Constants.TOKENS_PATH));
                image = image.getSubimage(x , y, 33, 30);
                setPreferredSize(new Dimension(43, 40));
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(false);
                setVerticalAlignment(SwingConstants.CENTER);

                setForeground(Constants.PRIMARY_BUTTON_COLOR);
                setFont(Constants.CUSTOM_FONT.deriveFont(5f));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    

     @Override
    protected void paintComponent(Graphics g) {
       
        if (image != null) {
            g.drawImage(image, 0, 0, 43, 40, this);
        }
         super.paintComponent(g);
    }
}
