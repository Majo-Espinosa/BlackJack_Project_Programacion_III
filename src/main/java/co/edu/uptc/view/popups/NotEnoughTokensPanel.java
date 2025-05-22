/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package co.edu.uptc.view.popups;

import co.edu.uptc.view.popups.draw.CustomBtn;
import co.edu.uptc.view.popups.draw.OutlinedLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
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
		gbc.insets = new Insets(25, 40, 25, 40);
		initComponents(gbc);
	}

	private void initComponents(GridBagConstraints gbc) {
		Font customFont;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(PopUpConstants.FONT_NAME)).deriveFont(30f);
		}
		catch (FontFormatException | IOException e) {
			customFont = new Font(PopUpConstants.AUX_FONT_NAME, Font.PLAIN, 20);
		}
		addTitleLbl(gbc, customFont);
		addAcceptBtn(gbc, customFont);
	}

	private void addTitleLbl(GridBagConstraints gbc, Font customFont) {
		titleLbl = new OutlinedLabel(PopUpConstants.WARNING_TITLE_TEXT, customFont);
		titleLbl.setPreferredSize(new Dimension(600, 70));
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		add(titleLbl, gbc);
	}

	private void addAcceptBtn(GridBagConstraints gbc, Font customFont) {
		acceptBtn = new CustomBtn(PopUpConstants.FIELD_SECOND_STAGE_NAME, PopUpConstants.FIELD_FIRST_STAGE_NAME,
				PopUpConstants.ACCEPT_BTN_TEXT, customFont);

		// Configuración para centrar el botón "SÍ"
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1; // El botón ocupa solo 1 columna
		gbc.weightx = 1; // Aumenta el peso para distribuir el espacio
		acceptBtn.addActionListener((ActionEvent e) -> {
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
