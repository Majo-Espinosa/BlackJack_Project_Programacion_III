package co.edu.uptc.view.popups;

import co.edu.uptc.view.popups.draw.OutlinedLabel;
import co.edu.uptc.view.reusable.Constants;

import javax.swing.*;
import java.awt.*;

public class MessageDialog extends JPanel {
    private JDialog dialog;
    private String message;

    public MessageDialog(String message) {
        this.message = message;
        setBackground(new Color(18, 69, 41));
        initLayout();
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipady = 15;
        gbc.insets = new Insets(15, 0, 15, 0);
        initComponents(gbc);
    }

    private void initComponents(GridBagConstraints gbc) {
        addTitleLbl(gbc);
    }

    private void addTitleLbl(GridBagConstraints gbc) {
        JLabel label = new OutlinedLabel(message, Constants.CUSTOM_FONT);
        label.setPreferredSize(new Dimension(600, 70));
        add(label, gbc);
    }

    public void showPopUp() {
        dialog = new JDialog();
        dialog.setContentPane(this);
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}
