package co.edu.uptc.view.game.draw;

import co.edu.uptc.view.reusable.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TokenButton extends JButton {

    private BufferedImage image, press, released;
    private BufferedImage bufferedImage;

    public TokenButton(int x, int y, String text) {
        super(text);
        try {
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bufferedImage = ImageIO.read(getClass().getResource(Constants.TOKENS_PATH));
            image = bufferedImage.getSubimage(x, y, 33, 30);
            press = image;
            setButtonPressedBackground(bufferedImage,x,y);
            setPreferredSize(new Dimension(43, 40));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setVerticalAlignment(SwingConstants.CENTER);

            setForeground(Constants.PRIMARY_BUTTON_COLOR);
            setFont(Constants.CUSTOM_FONT.deriveFont(8f));
            setAlignmentY(Component.TOP_ALIGNMENT);
        } catch (IOException e) {
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                image = press;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                image = released;
                repaint();
            }
        });
    }

    private void setButtonPressedBackground(BufferedImage bufferedImage,int x, int y) {
        released = bufferedImage.getSubimage(x+49, y-2, 30, 32);
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (image != null) {
            g.drawImage(image, 0, 0, 43, 40, this);
        }
        super.paintComponent(g);
    }
}
