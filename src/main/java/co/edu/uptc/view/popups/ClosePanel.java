/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package co.edu.uptc.view.popups;

import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.popups.draw.CustomBtn;
import co.edu.uptc.view.popups.draw.OutlinedLabel;
import co.edu.uptc.view.reusable.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Jhon
 */

public class ClosePanel extends JPanel {

    private MainPanel mainPanel;
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
        gbc.insets = new Insets(15, 30, 15, 30);
        initComponents(gbc);
    }

    private void initComponents(GridBagConstraints gbc) {
        Font customFont = Constants.CUSTOM_FONT;
        addTitleLbl(gbc, customFont);
        addAcceptBtn(gbc, customFont);
        addRefuseBtn(gbc, customFont);
    }

    private void addTitleLbl(GridBagConstraints gbc, Font customFont) {
        titleLbl = new OutlinedLabel(PopUpConstants.CONFIRMATION_TITLE_TEXT, customFont);
        titleLbl.setPreferredSize(new Dimension(titleLbl.getPreferredSize().width, 60));
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(titleLbl, gbc);
    }

    private void addAcceptBtn(GridBagConstraints gbc, Font customFont) {
        acceptBtn = new CustomBtn(PopUpConstants.FIELD_SECOND_STAGE_NAME, PopUpConstants.FIELD_FIRST_STAGE_NAME,
                PopUpConstants.ACCEPT_BTN_TEXT, customFont);
        acceptBtn.setFont(customFont.deriveFont(15f));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // El botón ocupa solo 1 columna
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hace que el botón se extienda en el
        // espacio horizontal
        gbc.weightx = 1; // Aumenta el peso para distribuir el espacio
        acceptBtn.addActionListener((ActionEvent _) -> {
            if (isExit) {
                System.exit(0);
            } else {
                mainPanel.updatePanel("menu", true);
                dialog.dispose();
            }
        });
        add(acceptBtn, gbc);
    }

    private void addRefuseBtn(GridBagConstraints gbc, Font customFont) {
        refuseBtn = new CustomBtn(PopUpConstants.FIELD_FIRST_STAGE_NAME, PopUpConstants.FIELD_SECOND_STAGE_NAME,
                PopUpConstants.REFUSE_BTN_TEXT, customFont);
        refuseBtn.setFont(customFont.deriveFont(15f));
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
