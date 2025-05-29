package co.edu.uptc.view;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

import co.edu.uptc.client.GameClient;
import co.edu.uptc.view.game.GamePanel;
import co.edu.uptc.view.login.LoginPanel;
import co.edu.uptc.view.menu.MenuPanel;
import co.edu.uptc.view.popups.ClosePanel;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.rules.RulesPanel;

public class MainPanel extends JPanel {

	private MenuPanel menuPanel;

	private GamePanel gamePanel;

	private LoginPanel loginPanel;

	private RulesPanel rulesPanel;

	private ClosePanel closePanel;

	private final Image gameBackground;

	private final CardLayout cardLayout;

	private MainFrame frame;

	public MainPanel(MainFrame frame, GameClient client) {
		this.frame = frame;
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		initComponents(client);
		gameBackground = new ImageIcon(getClass().getResource(Constants.GAME_BACKGROUND)).getImage();
	}

	public final void initComponents(GameClient client) {
		rulesPanel = new RulesPanel();
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(frame, this,client);
		loginPanel = new LoginPanel(this, client);
		closePanel = new ClosePanel(this);

		add(menuPanel, Constants.MENU_KEY);
		add(gamePanel, Constants.GAME_KEY);
		add(loginPanel, Constants.LOGIN_KEY);
		setOpaque(false);
	}

	public void updatePanel(String panelName) {
		cardLayout.show(this, panelName);
	}

	public void openClosePopup() {
		closePanel.showPopUp(true);
	}

	public void openRulesPopup() {
		rulesPanel.showPopUp(true);
	}

	public void resetTimer() {
		gamePanel.resetTimer();
	}

	public void promptBet(int betValue) {
		frame.setBet(betValue);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void addCrupierCards(String cards) {
		gamePanel.addCrupierCards(cards);
	}

	public void addPlayerCards(String cards) {
		gamePanel.addPlayerCards(cards);
	}
	
	public void clearCrupierCards() {
		gamePanel.clearCrupierCards();
	}
	
	public void clearPlayerCards() {
		gamePanel.clearPlayerCards();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameBackground, 0, 0, getWidth(), getHeight(), this);
	}

}
