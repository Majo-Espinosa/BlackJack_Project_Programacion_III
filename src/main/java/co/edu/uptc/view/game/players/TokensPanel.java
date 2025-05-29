package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import co.edu.uptc.view.reusable.Constants;

public class TokensPanel extends JPanel {
    public JLabel tokens, image;
    private GridBagConstraints gbc = new GridBagConstraints();

    public TokensPanel() {
        initComponents();
        addComponents();
    }

    public void setTokens(int tokens) {
        this.tokens.setText(String.valueOf(tokens));
    }

    public int getTokens() {
        return Integer.parseInt(tokens.getText());
    }

    public void initComponents() {
        setBackground(Constants.SECONDARY_BUTTON_COLOR);
        setForeground(Constants.PRIMARY_BUTTON_COLOR);
        setLayout(new GridBagLayout());

        tokens = new JLabel("0");
        tokens.setFont(Constants.CUSTOM_FONT.deriveFont(20f));
        tokens.setForeground(Constants.PRIMARY_BUTTON_COLOR);
        tokens.setBorder(new LineBorder(Color.BLACK, 1, true));
        tokens.setHorizontalAlignment(SwingConstants.CENTER);

        setImage();
    }

    public void setImage() {
        try {
            image = new JLabel();
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource(Constants.TOKENS_PATH));
            image.setIcon(new ImageIcon(bufferedImage.getSubimage(0, 162, 33, 30)));
            image.setBorder(new LineBorder(Color.BLACK, 1, true));
            image.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addComponents() {
        gbc.ipadx = 10;
        gbc.ipady = 10;
        add(image, gbc);

        gbc.ipady = 20;
        gbc.ipadx = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tokens, gbc);
    }
}
