/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package co.edu.uptc.view.rules.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Jhon
 */
public class RulesCustomBtn extends JButton {

	private ImageIcon originalIcon; // Guarda la imagen original para poder restaurarla
									// tras la animación

	private final int baseWidth; // Ancho base para el tamaño del icono

	private final int baseHeight; // Alto base para el tamaño del icono

	public RulesCustomBtn(String text, String iconPath, int width, int height) {
		super(text);
		this.baseWidth = width;
		this.baseHeight = height;

		// Establece el tamaño preferido, máximo y mínimo para que el botón tenga tamaño
		// fijo
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));

		// Remueve el fondo predeterminado para que solo se vea el icono y texto (si hay)
		setContentAreaFilled(false);
		// Quita el borde visual del botón
		setBorderPainted(false);
		// Evita que el botón muestre el foco con un borde
		setFocusPainted(false);
		// Hace que el botón sea transparente
		setOpaque(false);
		// Elimina el margen interno para que el tamaño del botón coincida con el tamaño
		// del icono
		setMargin(new Insets(0, 0, 0, 0));
		// Borra cualquier borde para que no afecte el tamaño o el diseño
		setBorder(BorderFactory.createEmptyBorder());
		// Cambia el cursor cuando el mouse está encima para indicar que es clickeable
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Cambia el color del texto si es que se usa texto
		setForeground(Color.WHITE);

		if (iconPath != null) {
			// Carga y redimensiona el icono desde la ruta especificada
			originalIcon = resizeIcon(iconPath, baseWidth, baseHeight);
			setIcon(originalIcon);
		}

		// Agrega un listener para detectar cuando se presiona y se libera el botón del
		// mouse
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Cuando se presiona, agranda el icono un 15% para efecto visual
				zoomIcon(1.15);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Cuando se suelta, vuelve el icono a su tamaño original
				setIcon(originalIcon);
			}
		});
	}

	/**
	 * Cambia el tamaño del icono aplicando un factor de escala.
	 * @param scale factor por el que se multiplica el ancho y alto base.
	 */
	private void zoomIcon(double scale) {
		int newWidth = (int) (baseWidth * scale);
		int newHeight = (int) (baseHeight * scale);
		// Redimensiona el icono original al nuevo tamaño
		ImageIcon zoomed = resizeIcon(originalIcon, newWidth, newHeight);
		setIcon(zoomed);
	}

	/**
	 * Carga una imagen desde el path y la redimensiona.
	 * @param path ruta del recurso de la imagen.
	 * @param width ancho deseado.
	 * @param height alto deseado.
	 * @return ImageIcon redimensionado.
	 */
	private ImageIcon resizeIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(getClass().getResource(path));
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	/**
	 * Redimensiona un icono dado sin recargar la imagen.
	 * @param icon icono original.
	 * @param width nuevo ancho.
	 * @param height nuevo alto.
	 * @return ImageIcon redimensionado.
	 */
	private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

}
