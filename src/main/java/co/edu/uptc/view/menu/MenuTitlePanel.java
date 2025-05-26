package co.edu.uptc.view.menu;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MenuTitlePanel extends JPanel {

	private final Image title;

	public MenuTitlePanel() {
		title = new ImageIcon(getClass().getResource("/images/icons/title.png")).getImage();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(title, 0, 0, getWidth(), getHeight(), this);

	}

}
