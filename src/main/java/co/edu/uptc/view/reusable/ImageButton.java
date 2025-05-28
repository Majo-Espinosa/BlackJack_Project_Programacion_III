package co.edu.uptc.view.reusable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ImageButton extends JButton {

    private BufferedImage press;
    private BufferedImage released;
    private BufferedImage actualImage;
    private final Color textColor;
    public static final int PLAY_ICON = 1;
    public static final int SKULL_ICON = 3;
    public static final int HOME_ICON = 4;
    public static final int DARK_QUESTION_MARK_ICON = 12;


    public ImageButton(String text, boolean inverted, float fontSize) {
        super(text);
        if (inverted) {
            textColor = Constants.PRIMARY_BUTTON_COLOR;
        } else {
            textColor = Constants.SECONDARY_BUTTON_COLOR;
        }

        setButtonBackground(inverted);
        setColorAndFeatures(fontSize);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                actualImage = press;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                actualImage = released;
                repaint();
            }
        });
    }



    public ImageButton(String text, boolean inverted, float fontSize, int iconType) {
        super(text, addIcon(iconType));
        if (inverted) {
            textColor = Constants.PRIMARY_BUTTON_COLOR;
        } else {
            textColor = Constants.SECONDARY_BUTTON_COLOR;
        }
        setButtonBackground(inverted);
        setHorizontalTextPosition(SwingConstants.LEFT);
        setColorAndFeatures(fontSize);
        setIconTextGap(10);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                actualImage = press;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                actualImage = released;
                repaint();
            }
        });
    }

    private void setButtonBackground(boolean inverted) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(Constants.BUTTONS_PATH));

            if (image == null) {
                press = createSolidColorImage(true);
                released = createSolidColorImage(false);
            } else {
                if (!inverted) {
                    released = image.getSubimage(0, 0, 48, 16);
                    press = image.getSubimage(48, 0, 48, 16);
                } else {
                    released = image.getSubimage(0, 16, 48, 16);
                    press = image.getSubimage(48, 16, 48, 16);
                }
            }
            actualImage = released;

        } catch (IOException e) {
            press = createSolidColorImage(true);
            released = createSolidColorImage(false);
            actualImage = released;
        }
    }

    private void setColorAndFeatures(float fontSize) {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(textColor);
        setFont(Constants.CUSTOM_FONT.deriveFont(fontSize));
    }

    private BufferedImage createSolidColorImage(boolean pressed) {
        BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        if (textColor.equals(Constants.PRIMARY_BUTTON_COLOR)) {
            g.setColor(Constants.SECONDARY_BUTTON_COLOR);
        } else g.setColor(textColor);

        if (pressed) {
            g.fillRect(0, 1, 200, 49);
        } else {
            g.fillRect(0, 0, 200, 50);
        }
        g.dispose();
        return image;
    }

    public static ImageIcon addIcon(int iconType) {
        try {
            switch (iconType) {
                case 1 -> {
                    return new ImageIcon(ImageIO.read(ImageButton.class.getResource(Constants.BUTTONS_PATH)).getSubimage(150, 3, 8, 8).getScaledInstance(20, 20, BufferedImage.SCALE_SMOOTH));
                }
                case 3 -> {
                    return new ImageIcon(ImageIO.read(ImageButton.class.getResource(Constants.BUTTONS_PATH)).getSubimage(148, 51, 8, 8).getScaledInstance(20, 20, BufferedImage.SCALE_SMOOTH));
                }
                case 4 -> {
                    return new ImageIcon(ImageIO.read(ImageButton.class.getResource(Constants.BUTTONS_PATH)).getSubimage(164, 3, 8, 8).getScaledInstance(20, 20, BufferedImage.SCALE_SMOOTH));
                }
                case 12 -> {
                    return new ImageIcon(ImageIO.read(ImageButton.class.getResource(Constants.BUTTONS_PATH)).getSubimage(164, 51, 8, 8).getScaledInstance(20, 20, BufferedImage.SCALE_SMOOTH));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (actualImage != null) {
            g.drawImage(actualImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
