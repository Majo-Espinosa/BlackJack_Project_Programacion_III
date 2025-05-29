package co.edu.uptc.view.game.draw;

import co.edu.uptc.view.game.CardsPanel;
import co.edu.uptc.view.game.GameConstants;

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
    private CardsPanel parent;
    private int originalZindex = -1;

    public CardImage(String name) {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setPreferredSize(new java.awt.Dimension(width + 40, height + 40));
        try {
            String path = CardJsonReader.getPathByName(name);
            BufferedImage card = ImageIO.read(getClass().getResource(path));
            icon = new ImageIcon(card.getScaledInstance(width + 20, height + 20, BufferedImage.SCALE_REPLICATE));
            setIcon(icon);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (parent != null) {
                        originalZindex = parent.getComponentZOrder(CardImage.this);
                        parent.bringToFront(CardImage.this);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (parent != null && originalZindex >= 0) {
                        parent.restoreZOrder(CardImage.this, originalZindex);
                        originalZindex = -1;
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CardImage() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setPreferredSize(new java.awt.Dimension(width + 40, height + 40));
        try {
            BufferedImage card = ImageIO.read(getClass().getResource(GameConstants.BACK_CARD_PATH));
            icon = new ImageIcon(card.getScaledInstance(width + 20, height + 20, BufferedImage.SCALE_REPLICATE));
            setIcon(icon);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setParentPanel(CardsPanel parent) {
        this.parent = parent;
    }
}
