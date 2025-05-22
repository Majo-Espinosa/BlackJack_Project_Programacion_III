package co.edu.uptc.view.login;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class JTextFieldConFondo extends JTextField {
    private Image imagenFondo;
    private Font customFont;

    public JTextFieldConFondo() {
        super();

        setPreferredSize(new Dimension(500, 100));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        try {
            // Cargar imagen de fondo desde recursos (funciona en .jar)
            InputStream imgStream = getClass().getResourceAsStream("/images/icons/button_register.png");
            if (imgStream == null) {
                throw new IOException("No se encontró la imagen en la ruta: " + "images/icons/button_register.png");
            }
            imagenFondo = ImageIO.read(imgStream);
            setOpaque(false);

            // Cargar fuente personalizada desde recursos (funciona en .jar)
            InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (fontStream == null) {
                throw new IOException("No se encontró la fuente en la ruta: " + "/fonts/PressStart2P-Regular.ttf");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            setFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println("Error al cargar recursos: " + e.getMessage());
            setOpaque(true);
            setText("Error cargando recursos");
            setForeground(Color.RED);
            setFont(new Font("Arial", Font.TRUETYPE_FONT, 30));
        }

        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }

}
