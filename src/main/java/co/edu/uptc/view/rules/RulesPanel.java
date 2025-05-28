package co.edu.uptc.view.rules;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import co.edu.uptc.view.rules.draw.RulesCustomBtn;

public class RulesPanel extends JPanel {

	private ImagePanel informationPanel;

	private ImagePanel actionsPanel;

	private ImagePanel crupierPanel;

	private ImagePanel rewardsPanel;

	private JDialog popUp;

	private final CardLayout cardLayout;

	public RulesPanel() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		initComponents();
		// Se agregan los paneles con un nombre clave para que CardLayout pueda mostrarlos :(
		// al invocar show()
		this.add(informationPanel, RulesConstants.INFO_PANEL_NAME);
		this.add(actionsPanel, RulesConstants.ACTIONS_PANEL_NAME);
		this.add(crupierPanel, RulesConstants.CRUPIER_PANEL_NAME);
		this.add(rewardsPanel, RulesConstants.REWARDS_PANEL_NAME);
	}

	private void initComponents() {
		// Inicializa cada ImagePanel con su respectiva imagen :)
		informationPanel = new ImagePanel(RulesConstants.INFO_PANEL_IMG);
		actionsPanel = new ImagePanel(RulesConstants.ACTIONS_PANEL_IMG);
		crupierPanel = new ImagePanel(RulesConstants.CRUPIER_PANEL_IMG);
		rewardsPanel = new ImagePanel(RulesConstants.REWARDS_PANEL_IMG);

		// Inicializa la configuración interna de cada panel, agregando botones y layouts :)
		initInformationPanel();
		initActionsPanel();
		initCrupierPanel();
		initRewardsPanel();
	}

	/**
	 * Método para crear un panel de navegación con botones "previo" y "siguiente"
	 * utilizando un BorderLayout para ubicarlos a izquierda y derecha respectivamente. Se
	 * usan paneles intermedios con FlowLayout para mantener el alineamiento correcto.
	 * @param prevBtn botón previo (puede ser null si no se quiere mostrar)
	 * @param nextBtn botón siguiente (puede ser null si no se quiere mostrar)
	 * @return JPanel configurado con los botones alineados a izquierda y derecha
	 */
	
	private JPanel createButtonsPanel(JButton prevBtn, JButton nextBtn) {
		JPanel navPanel = new JPanel(new BorderLayout());
		navPanel.setOpaque(false);

		if (prevBtn != null) {
			// Panel izquierdo con FlowLayout alineado a la izquierda para el botón previo
			JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			leftPanel.setOpaque(false);
			leftPanel.add(prevBtn);
			navPanel.add(leftPanel, BorderLayout.WEST);
		}

		if (nextBtn != null) {
			// Panel derecho con FlowLayout alineado a la derecha para el botón siguiente
			JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			rightPanel.setOpaque(false);
			rightPanel.add(nextBtn);
			navPanel.add(rightPanel, BorderLayout.EAST);
		}

		return navPanel;
	}

	// Los métodos initInformationPanel, initActionsPanel, initCrupierPanel e
	// initRewardsPanel
	// configuran cada panel con su imagen de fondo, layout y botones para navegar entre
	// ellos.
	// Los botones prev y next llaman a cardLayout.show() para mostrar el panel deseado.

	private void initInformationPanel() {
		informationPanel = new ImagePanel(RulesConstants.INFO_PANEL_IMG);
		informationPanel.setLayout(new BorderLayout());

		RulesCustomBtn nextBtn = new RulesCustomBtn("", RulesConstants.NEXT_BTN_IMG, 56, 32);
		nextBtn.addActionListener((ActionEvent e) -> {
			cardLayout.show(this, RulesConstants.ACTIONS_PANEL_NAME);
		});

		informationPanel.add(createButtonsPanel(null, nextBtn), BorderLayout.SOUTH);
	}

	private void initActionsPanel() {
		actionsPanel = new ImagePanel(RulesConstants.ACTIONS_PANEL_IMG);
		actionsPanel.setLayout(new BorderLayout());

		RulesCustomBtn nextBtn = new RulesCustomBtn("", RulesConstants.NEXT_BTN_IMG, 56, 32);
		RulesCustomBtn prevBtn = new RulesCustomBtn("", RulesConstants.PREV_BTN_IMG, 56, 32);

		nextBtn.addActionListener((ActionEvent e) -> {
			cardLayout.show(this, RulesConstants.CRUPIER_PANEL_NAME);
		});
		prevBtn.addActionListener((ActionEvent e) -> {
			cardLayout.show(this, RulesConstants.INFO_PANEL_NAME);
		});

		actionsPanel.add(createButtonsPanel(prevBtn, nextBtn), BorderLayout.SOUTH);
	}

	private void initCrupierPanel() {
		crupierPanel = new ImagePanel(RulesConstants.CRUPIER_PANEL_IMG);
		crupierPanel.setLayout(new BorderLayout());

		RulesCustomBtn nextBtn = new RulesCustomBtn("", RulesConstants.NEXT_BTN_IMG, 56, 32);
		RulesCustomBtn prevBtn = new RulesCustomBtn("", RulesConstants.PREV_BTN_IMG, 56, 32);

		nextBtn.addActionListener((ActionEvent e) -> {
			cardLayout.show(this, RulesConstants.REWARDS_PANEL_NAME);
		});
		prevBtn.addActionListener((ActionEvent e) -> {
			cardLayout.show(this, RulesConstants.ACTIONS_PANEL_NAME);
		});

		crupierPanel.add(createButtonsPanel(prevBtn, nextBtn), BorderLayout.SOUTH);
	}

	private void initRewardsPanel() {
		rewardsPanel = new ImagePanel(RulesConstants.REWARDS_PANEL_IMG);
		rewardsPanel.setLayout(new BorderLayout());

		RulesCustomBtn prevBtn = new RulesCustomBtn("", RulesConstants.PREV_BTN_IMG, 56, 32);

		prevBtn.addActionListener((ActionEvent e) -> {
			cardLayout.show(this, RulesConstants.CRUPIER_PANEL_NAME);
		});

		rewardsPanel.add(createButtonsPanel(prevBtn, null), BorderLayout.SOUTH);
	}

	public void showPopUp(boolean isExit) {
		// Crea un diálogo modal para mostrar este RulesPanel como ventana emergente
		popUp = new JDialog();
		popUp.setContentPane(this);
		popUp.setSize(new Dimension(720, 480));
		popUp.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popUp.setLocationRelativeTo(null);
		popUp.setResizable(false);
		popUp.setVisible(true);
	}

}
