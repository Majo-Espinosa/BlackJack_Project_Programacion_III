/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package co.edu.uptc.view.popups.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphVector;

/**
 * @author Jhon
 */

public class CustomBtn extends JButton {

	private Image normalImage;

	private Image pressedImage;

	private boolean isPressed = false;

	private Color outlineColor = Color.WHITE;
	private Color fillColor = Color.BLACK;
	private float strokeWidth = 2f;

	public CustomBtn(String normalPath, String pressedPath, String text, Font font) {
		super(text);
		font = font.deriveFont(18f);
		try {
			normalImage = new ImageIcon(getClass().getResource(normalPath)).getImage();
			pressedImage = new ImageIcon(getClass().getResource(pressedPath)).getImage();
		}
		catch (Exception e) {
			System.err.println("Error loading images: " + e.getMessage());
		}

		setFont(font);
		
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setOpaque(false);
		setForeground(fillColor);

		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				isPressed = true;
				repaint();
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				isPressed = false;
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		Image bg = isPressed ? pressedImage : normalImage;
		if (bg != null) {
			g2.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}

		// Dibujar texto con contorno
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		String text = getText();
		Font font = getFont();
		FontMetrics metrics = g2.getFontMetrics(font);
		int x = (getWidth() - metrics.stringWidth(text)) / 2;
		int y = (getHeight() + metrics.getAscent() - metrics.getDescent()) / 2;

		GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);
		Shape textShape = gv.getOutline(x, y);

		// Contorno
		g2.setColor(outlineColor);
		g2.setStroke(new BasicStroke(strokeWidth));
		g2.draw(textShape);

		// Relleno
		g2.setColor(fillColor);
		g2.fill(textShape);

		g2.dispose();
	}

	// Setters opcionales
	public void setOutlineColor(Color color) {
		this.outlineColor = color;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public void setStrokeWidth(float width) {
		this.strokeWidth = width;
	}

}
