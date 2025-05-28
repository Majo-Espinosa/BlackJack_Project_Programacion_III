package co.edu.uptc.client;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import co.edu.uptc.client.game.GamePanel;
import co.edu.uptc.client.login.LoginPanel;
import co.edu.uptc.client.menu.MenuPanel;
import co.edu.uptc.client.popups.ClosePanel;
import co.edu.uptc.client.reusable.Constants;
import co.edu.uptc.client.rules.RulesPanel;

public class MainPanel extends JPanel {

	private MenuPanel menuPanel;

	private GamePanel gamePanel;

	private LoginPanel loginPanel;

	private RulesPanel rulesPanel;

	private ClosePanel closePanel;

	private final Image gameBackground;

	private final CardLayout cardLayout;

	public MainPanel() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		initComponents();
		gameBackground = new ImageIcon(getClass().getResource(Constants.GAME_BACKGROUND)).getImage();
	}

	public final void initComponents() {
		rulesPanel = new RulesPanel();
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);
		loginPanel = new LoginPanel(this);
		closePanel = new ClosePanel(this);

		add(menuPanel, Constants.MENU_KEY);
		add(gamePanel, Constants.GAME_KEY);
		add(loginPanel, Constants.LOGIN_KEY);
		setOpaque(false);
	}

	public void updatePanel(String panelName, boolean isMainMenuBackground) {
		cardLayout.show(this, panelName);
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
