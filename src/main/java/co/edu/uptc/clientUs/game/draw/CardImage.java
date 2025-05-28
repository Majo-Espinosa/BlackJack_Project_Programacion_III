package co.edu.uptc.clientUs.game.draw;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import co.edu.uptc.clientUs.reusable.Constants;

public class CardImage extends JLabel {

    private final int width = 48;
    private final int height = 64;
    private ImageIcon icon;
    private ImageIcon extendedIcon;

    public CardImage(int row, int column) {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setPreferredSize(new java.awt.Dimension(width + 40,  height + 40));
        try {
            BufferedImage cardsSheet = ImageIO.read(getClass().getResource(Constants.CARDS_PATH));
            int xposition = column * width;
            int yposition = row * height;
            icon = new ImageIcon(cardsSheet.getSubimage(xposition, yposition, width, height).getScaledInstance(width + 20 , height + 20, BufferedImage.SCALE_REPLICATE));
            extendedIcon = new ImageIcon(icon.getImage().getScaledInstance(width + 25 , height + 25, BufferedImage.TRANSLUCENT));
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

    public CardImage() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setPreferredSize(new java.awt.Dimension(width + 40,  height + 40));
        try {
            BufferedImage cardsSheet = ImageIO.read(getClass().getResource(Constants.CARDS_PATH));
            int xposition = 0 * width;
            int yposition = 4 * height;
            icon = new ImageIcon(cardsSheet.getSubimage(xposition, yposition, width, height).getScaledInstance(width + 20 , height + 20, BufferedImage.SCALE_REPLICATE));
            extendedIcon = new ImageIcon(icon.getImage().getScaledInstance(width + 25 , height + 25, BufferedImage.TRANSLUCENT));
            setIcon(icon);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
