package co.edu.uptc.view.reusable;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ArcadeButton extends JButton {

    private Color normalBg;
    private Color hoverBg;
    private Color clickBg;
    private Color textColor;

    public ArcadeButton(String text, long fontSize, Color bgColor, Color textColor) {
        super(text);
        setFont(Constants.CUSTOM_FONT.deriveFont(fontSize));
        this.normalBg = bgColor;
        this.hoverBg = bgColor.brighter();
        this.clickBg = bgColor.darker();
        this.textColor = textColor;

        initStyle();
        initListeners();
    }

    private void initStyle() {
        setBackground(normalBg);
        setForeground(textColor);
        setFocusPainted(false);
        Border blackLine = BorderFactory.createLineBorder(textColor, 4);
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(blackLine, padding));
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void initListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalBg);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(clickBg);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverBg);
            }
        });
    }
}
