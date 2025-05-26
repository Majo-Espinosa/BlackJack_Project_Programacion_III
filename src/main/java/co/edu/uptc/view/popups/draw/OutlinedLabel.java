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

public class OutlinedLabel extends JLabel {

	private final Color outlineColor = Color.WHITE;

	private final Color fillColor = Color.BLACK;

	private final float strokeWidth = 3f;

	public OutlinedLabel(String text, Font font) {
		super(text);
		setFont(font);
		setOpaque(false); // Fondo transparente
	}

	@Override
	protected void paintComponent(Graphics g) {
		Font customFont = getFont();
		Graphics2D g2 = (Graphics2D) g.create();

		// Antialiasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		String[] lines = getText().split("\n");
		FontMetrics fm = g2.getFontMetrics(customFont);
		int lineHeight = fm.getHeight();
		int totalHeight = lines.length * lineHeight;
		int startY = (getHeight() - totalHeight) / 2 + fm.getAscent();

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			GlyphVector gv = customFont.createGlyphVector(g2.getFontRenderContext(), line);
			Shape textShape = gv.getOutline();

			// Centrar horizontalmente
			Rectangle bounds = textShape.getBounds();
			int x = (getWidth() - bounds.width) / 2;

			// Calcular Y para cada línea
			int y = startY + i * lineHeight;

			// Trasladar la forma al lugar correcto
			Shape translated = gv.getOutline(x, y);

			// Contorno
			g2.setColor(outlineColor);
			g2.setStroke(new BasicStroke(strokeWidth));
			g2.draw(translated);

			// Relleno
			g2.setColor(fillColor);
			g2.fill(translated);
		}

		g2.dispose();
	}

}
