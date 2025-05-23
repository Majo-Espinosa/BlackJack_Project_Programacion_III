package co.edu.uptc.view.reusable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class ImageButton extends JButton {

    private BufferedImage press;
    private BufferedImage released;
    private BufferedImage actualImage;
    private final Color pressedColor;
    private final Color releasedColor;

    public ImageButton(String text, boolean inverted, float fontSize) {
        super(text);
        if (inverted) {
            pressedColor = Constants.SECONDARY_BUTTON_COLOR;
            releasedColor = Constants.PRIMARY_BUTTON_COLOR;
        } else {
            releasedColor = Constants.SECONDARY_BUTTON_COLOR;
            pressedColor = Constants.PRIMARY_BUTTON_COLOR;
        }

        try {
            InputStream buttonStream = getClass().getResourceAsStream(Constants.REUSABLE_BUTTON_IMAGE_PATH);
            InputStream pressedButtonStream = getClass().getResourceAsStream(Constants.REUSABLE_BUTTON_PRESSED_IMAGE_PATH);

            if (buttonStream == null || pressedButtonStream == null) {
                press = createSolidColorImage(releasedColor);
                released = createSolidColorImage(pressedColor);
            } else {
                if (!inverted) {
                    released = ImageIO.read(buttonStream);
                    press = ImageIO.read(pressedButtonStream);
                } else {
                    released = ImageIO.read(pressedButtonStream);
                    press = ImageIO.read(buttonStream);
                }
            }
            actualImage = released;

        } catch (IOException e) {
            press = createSolidColorImage(pressedColor);
            released = createSolidColorImage(releasedColor);
            actualImage = released;
        }

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(releasedColor);
        setFont(Constants.CUSTOM_FONT.deriveFont(fontSize));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                actualImage = press;
                setForeground(pressedColor);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                actualImage = released;
                setForeground(releasedColor);
                repaint();
            }
        });
    }

    private BufferedImage createSolidColorImage(Color color) {
        BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, 200, 50);
        g.dispose();
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (actualImage != null) {
            g.drawImage(actualImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
