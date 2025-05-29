package co.edu.uptc.view.menu;

import co.edu.uptc.view.reusable.Constants;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MenuTitlePanel extends JPanel {

	private final Image title;

	public MenuTitlePanel() {
		title = new ImageIcon(getClass().getResource(Constants.TITLE_PATH)).getImage();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(title, 0, 0, getWidth(), getHeight(), this);

	}

}
