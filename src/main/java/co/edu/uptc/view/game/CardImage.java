package co.edu.uptc.view.game;

import co.edu.uptc.view.reusable.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CardImage extends JLabel {
    private final int width = 63;
    private final int height = 77;

    public CardImage(String resourcePath, int row, int column) {
        try {
            BufferedImage cardsSheet = ImageIO.read(getClass().getResource(Constants.CARDS_PATH));
            int xposition = column * width;
            int yposition = row * height;
            setIcon(new ImageIcon(cardsSheet.getSubimage(xposition, yposition, width, height)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
