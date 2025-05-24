package co.edu.uptc.view.game;

import co.edu.uptc.view.reusable.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardImage extends JLabel {

    public CardImage(int row, int column) {
        try {
            BufferedImage cardsSheet = ImageIO.read(getClass().getResource(Constants.CARDS_PATH));
            int width = 48;
            int xposition = column * width;
            int height = 64;
            int yposition = row * height;
            setIcon(new ImageIcon(cardsSheet.getSubimage(xposition, yposition, width, height)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
