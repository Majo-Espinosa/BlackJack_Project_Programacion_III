<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/menu/MenuTitlePanel.java
package co.edu.uptc.view.menu;
========
package co.edu.uptc.client.menu;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/menu/MenuTitlePanel.java

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
