package co.edu.uptc.view.game;

import co.edu.uptc.view.reusable.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardImage extends JLabel {

    private final int width = 48;
    private final int height = 64;
    private ImageIcon icon;
    private ImageIcon extendedIcon;

    public CardImage(int row, int column) {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setPreferredSize(new java.awt.Dimension(width + 20,  height + 20));
        try {
            BufferedImage cardsSheet = ImageIO.read(getClass().getResource(Constants.CARDS_PATH));
            int xposition = column * width;
            int yposition = row * height;
            icon = new ImageIcon(cardsSheet.getSubimage(xposition, yposition, width, height));
            extendedIcon = new ImageIcon(icon.getImage().getScaledInstance(width + 5 , height + 5, BufferedImage.TRANSLUCENT));
            setIcon(icon);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setIcon(extendedIcon);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setIcon(icon);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
