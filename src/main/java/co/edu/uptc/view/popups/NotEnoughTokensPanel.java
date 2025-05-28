/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package co.edu.uptc.view.popups;

import co.edu.uptc.view.popups.draw.OutlinedLabel;
import co.edu.uptc.view.reusable.Constants;
import co.edu.uptc.view.reusable.ImageButton;
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

/**
 * @author Jhon
 */
public class NotEnoughTokensPanel extends JPanel {

	private JLabel titleLbl;

	private JButton acceptBtn;

	public NotEnoughTokensPanel() {
		setBackground(new Color(18, 69, 41)); // Fondo completamente verde
		initLayout();
	}

	private void initLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipady = 15;
		gbc.insets = new Insets(25, 40, 25, 40);
		initComponents(gbc);
	}

	private void initComponents(GridBagConstraints gbc) {
		addTitleLbl(gbc);
		addAcceptBtn(gbc);
	}

	private void addTitleLbl(GridBagConstraints gbc) {
		titleLbl = new OutlinedLabel(PopUpConstants.WARNING_TITLE_TEXT, Constants.CUSTOM_FONT);
		titleLbl.setPreferredSize(new Dimension(600, 70));
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(titleLbl, gbc);
	}

	private void addAcceptBtn(GridBagConstraints gbc) {
		acceptBtn = new ImageButton(PopUpConstants.ACCEPT_BTN_TEXT, false, 15f);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1; // El botón ocupa solo 1 columna
		gbc.weightx = 1; // Aumenta el peso para distribuir el espacio
		acceptBtn.addActionListener((ActionEvent _) -> {
			// one chiriskreifor dos tres trucos
		});
		add(acceptBtn, gbc);
	}

	public void showPopUp() {
		JDialog dialog = new JDialog();
		dialog.setContentPane(this);
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(true);
		dialog.setVisible(true);
	}

}
