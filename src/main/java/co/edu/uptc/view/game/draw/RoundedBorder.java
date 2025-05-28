package co.edu.uptc.view.game.draw;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedBorder extends AbstractBorder {
    private final int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255, 75));
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(radius, radius, radius, radius);
        return insets;
    }
}
