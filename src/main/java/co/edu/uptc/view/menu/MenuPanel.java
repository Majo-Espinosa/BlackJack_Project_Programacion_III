package co.edu.uptc.view.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import co.edu.uptc.view.MainPanel;

public class MenuPanel extends JPanel {

	private final MainPanel mainPanel;

	private MenuButtonsPanel buttons;

	private MenuTitlePanel title;

	private GridBagConstraints gbc;

	private MenuIconsPanel leftiIconPanel;

	private MenuIconsPanel rightIconPanel;

	public MenuPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		initComponents();

		JPanel auxPanel = new JPanel(new GridBagLayout());
		gbc.ipadx = 1080;
		gbc.ipady = 190;
		gbc.insets = new Insets(110, 0, 0, 0);

		auxPanel.add(title, gbc);
		auxPanel.setOpaque(false);
		auxPanel.setPreferredSize(new Dimension(this.getWidth() / 2, 200));

		gbc.ipadx = 250;
		gbc.ipady = 340;
		gbc.insets = new Insets(0, 0, 0, 0);

		JPanel leftAuxPanel = new JPanel(new GridBagLayout());
		gbc.insets = new Insets(0, 30, 0, 0);
		leftAuxPanel.add(leftiIconPanel, gbc);
		leftAuxPanel.setOpaque(false);

		JPanel rightAuxPanel = new JPanel(new GridBagLayout());
		gbc.insets = new Insets(0, 0, 0, 30);
		rightAuxPanel.add(rightIconPanel, gbc);
		rightAuxPanel.setOpaque(false);

		this.setLayout(new BorderLayout());
		this.add(auxPanel, BorderLayout.NORTH);
		this.add(buttons, BorderLayout.CENTER);
		this.add(leftAuxPanel, BorderLayout.WEST);
		this.add(rightAuxPanel, BorderLayout.EAST);
		this.setOpaque(false);

	}

	@Override
	protected void paintComponent(Graphics g) {

	}

	private void initComponents() {

		gbc = new GridBagConstraints();
		leftiIconPanel = new MenuIconsPanel("/images/icons/MenuCardsLeft.png");
		rightIconPanel = new MenuIconsPanel("/images/icons/MenuCardsRight.png");
		buttons = new MenuButtonsPanel(mainPanel);
		title = new MenuTitlePanel();
	}

}
