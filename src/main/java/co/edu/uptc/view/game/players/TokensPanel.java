package co.edu.uptc.view.game.players;

import co.edu.uptc.view.reusable.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TokensPanel extends JPanel {
    public JLabel tokens, image;
    private final GridBagConstraints gbc = new GridBagConstraints();

    public TokensPanel() {
        initComponents();
        addComponents();
    }

    public void setTokens(int tokens) {
        this.tokens.setText(String.valueOf(tokens));
    }

    public final void initComponents() {
        setBackground(Constants.SECONDARY_BUTTON_COLOR);
        setBorder(new LineBorder(Color.BLACK));
        setForeground(Constants.PRIMARY_BUTTON_COLOR);
        setLayout(new GridBagLayout());

        tokens = new JLabel("100");
        tokens.setFont(Constants.CUSTOM_FONT.deriveFont(20f));
        tokens.setForeground(Constants.PRIMARY_BUTTON_COLOR);

        setImage();
    }

    public void setImage() {
        try {
            image = new JLabel();
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource(Constants.TOKENS_PATH));
            image.setIcon(new ImageIcon(bufferedImage.getSubimage(0, 18, 33, 30)));
            image.setBorder(new LineBorder(Color.BLACK, 1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void addComponents() {
        gbc.ipadx = 5;
        gbc.ipady = 10;
        add(image, gbc);

        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(0, 20, 0, 20);
        add(tokens, gbc);
    }
}
