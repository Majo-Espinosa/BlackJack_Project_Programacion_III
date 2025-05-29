/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/popups/ClosePanel.java
package co.edu.uptc.view.popups;
========
package co.edu.uptc.client.popups;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/popups/ClosePanel.java

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

<<<<<<<< HEAD:src/main/java/co/edu/uptc/view/popups/ClosePanel.java
import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.popups.draw.OutlinedLabel;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.reusable.ImageButton;
========
import co.edu.uptc.client.MainPanel;
import co.edu.uptc.client.popups.draw.OutlinedLabel;
import co.edu.uptc.client.reusable.Constants;
import co.edu.uptc.client.reusable.ImageButton;
>>>>>>>> origin/Daniel-otra-vez:src/main/java/co/edu/uptc/client/popups/ClosePanel.java

/**
 * @author Jhon
 */

public class ClosePanel extends JPanel {

	private final MainPanel mainPanel;

	private JLabel titleLbl;

	private JButton acceptBtn;

	private JButton refuseBtn;

	private boolean isExit;

	private JDialog dialog;

	public ClosePanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		setBackground(new Color(18, 69, 41)); // Fondo completamente verde
		initLayout();
	}

	private void initLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipady = 15;
		gbc.insets = new Insets(15, 30, 15, 30);
		initComponents(gbc);
	}

	private void initComponents(GridBagConstraints gbc) {
		addTitleLbl(gbc);
		addAcceptBtn(gbc);
		addRefuseBtn(gbc);
	}

	private void addTitleLbl(GridBagConstraints gbc) {
		titleLbl = new OutlinedLabel(PopUpConstants.CONFIRMATION_TITLE_TEXT, Constants.CUSTOM_FONT);
		titleLbl.setPreferredSize(new Dimension(titleLbl.getPreferredSize().width, 60));
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(titleLbl, gbc);
	}

	private void addAcceptBtn(GridBagConstraints gbc) {
		acceptBtn = new ImageButton(PopUpConstants.ACCEPT_BTN_TEXT, false, 15f);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1; // El botón ocupa solo 1 columna
		gbc.fill = GridBagConstraints.HORIZONTAL; // Hace que el botón se extienda en el
		// espacio horizontal
		gbc.weightx = 1; // Aumenta el peso para distribuir el espacio
		acceptBtn.addActionListener((ActionEvent _) -> {
			if (isExit) {
				System.exit(0);
			}
			else {
				mainPanel.updatePanel(Constants.MENU_KEY);
				dialog.dispose();
			}
		});
		add(acceptBtn, gbc);
	}

	private void addRefuseBtn(GridBagConstraints gbc) {
		refuseBtn = new ImageButton(PopUpConstants.REFUSE_BTN_TEXT, true, 15f);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		refuseBtn.addActionListener((ActionEvent _) -> dialog.dispose());
		add(refuseBtn, gbc);
	}

	public void showPopUp(boolean isExit) {
		this.isExit = isExit;
		dialog = new JDialog();
		dialog.setContentPane(this);
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

}
