<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/rules/ImagePanel.java
package co.edu.uptc.view.rules;
========
package co.edu.uptc.client.rules;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/rules/ImagePanel.java

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {

	private BufferedImage image;

	public ImagePanel(String resourcePath) {
		try {
			image = ImageIO.read(getClass().getResource(resourcePath));
		}
		catch (IOException e) {
		}

		setLayout(new BorderLayout());
	}

	public ImagePanel(BufferedImage image) {
		this.image = image;
		setLayout(new BorderLayout());
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
