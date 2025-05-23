package co.edu.uptc.view;

import co.edu.uptc.view.game.GamePanel;
import co.edu.uptc.view.login.LoginPanel;
import co.edu.uptc.view.menu.MenuPanel;
import co.edu.uptc.view.popups.ClosePanel;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.rules.RulesPanel;
import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	private LoginPanel loginPanel;
	private RulesPanel rulesPanel;
	private ClosePanel closePanel;

	private Image gameBackground;
	private CardLayout cardLayout;

	public MainPanel() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		initComponents();
		gameBackground = new ImageIcon(getClass().getResource(Constants.MAIN_MENU_BACKGROUND)).getImage();
	}

	public void initComponents() {
		rulesPanel = new RulesPanel();
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);
		loginPanel = new LoginPanel(this);
		closePanel = new ClosePanel(this);

		setGameBackground(true);
		add(menuPanel, "menu");
		add(gamePanel, "game");
		add(loginPanel, "login");
		setOpaque(false);
	}

	public void updatePanel(String panelName, boolean isMainMenuBackground) {
		setGameBackground(isMainMenuBackground);
		cardLayout.show(this, panelName);
	}

	public void setGameBackground(boolean isMainMenuBackground) {
		if (isMainMenuBackground) {
			gameBackground = new ImageIcon(getClass().getResource(Constants.MAIN_MENU_BACKGROUND)).getImage();
		} else {
			gameBackground = new ImageIcon(getClass().getResource(Constants.GAME_BACKGROUND)).getImage();
		}
		repaint();
	}

	public void openClosePopup() {
		closePanel.showPopUp(true);
	}

	public void openRulesPopup() {
		rulesPanel.showPopUp(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameBackground, 0, 0, getWidth(), getHeight(), this);
	}
}
