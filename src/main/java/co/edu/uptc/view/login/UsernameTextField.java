package co.edu.uptc.view.login;

import co.edu.uptc.view.reusable.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UsernameTextField extends JTextField {
    private Image backgoundImage;

    public UsernameTextField() {
        super();
        initComponents();
    }

    private void loadBackgroundImage() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(Constants.BUTTONS_PATH));
            backgoundImage = image.getSubimage(48, 16, 48, 16);
        } catch (IOException e) {
            backgoundImage = createSolidColorImage();
        }    }

    private void initComponents() {
        loadBackgroundImage();
        setPreferredSize(new Dimension(500, 100));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setOpaque(false);
        setFont(Constants.CUSTOM_FONT.deriveFont(20f));
        setForeground(Color.WHITE);
    }

    private BufferedImage createSolidColorImage() {
        BufferedImage image = new BufferedImage(200, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Constants.SECONDARY_BUTTON_COLOR);
        g.fillRect(0, 0, 200, 50);
        g.dispose();
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgoundImage != null) {
            g.drawImage(backgoundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);

        if (getText().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            int padding = (getHeight() - getFont().getSize()) / 2 + g2.getFontMetrics().getAscent() - 2;
            String placeholder = "Ingresa tu nombre";
            g2.drawString(placeholder, insets.left + 5, padding);
            g2.dispose();
        }
    }

}
