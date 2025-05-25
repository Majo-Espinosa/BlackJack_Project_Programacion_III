package co.edu.uptc.view.rules;

import co.edu.uptc.view.reusable.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RulesPanel extends JPanel {

    private ImagePanel rules1;
    private ImagePanel rules2;
    private ImagePanel rules3;
    private ImagePanel rules4;
    private JDialog dialog;

    public RulesPanel() {
        initComponents();
        this.setLayout(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);


        JPanel auxPanel1 = new JPanel();
        auxPanel1.setLayout(new BorderLayout());
        auxPanel1.setOpaque(false);
        ImageButton button1 = new ImageButton("->", false, 16);
        auxPanel1.add(button1, BorderLayout.EAST);
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "2");
            }
        });
        rules1.add(auxPanel1, BorderLayout.SOUTH);


        JPanel auxPanel2 = new JPanel();
        auxPanel2.setLayout(new BorderLayout());
        auxPanel2.setOpaque(false);
        ImageButton button2 = new ImageButton("->", false, 16);
        ImageButton button2Prev = new ImageButton("<-", false, 16);
        auxPanel2.add(button2, BorderLayout.EAST);
        auxPanel2.add(button2Prev,BorderLayout.WEST);
        rules2.add(auxPanel2, BorderLayout.SOUTH);
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(cardPanel, "3");
            }
        });
        button2Prev.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "1");
            }
        });

        JPanel auxPanel3 = new JPanel();
        auxPanel3.setLayout(new BorderLayout());
        auxPanel3.setOpaque(false);
        ImageButton button3 = new ImageButton("->", false, 16);
        auxPanel3.add(button3, BorderLayout.EAST);
        ImageButton button3Prev = new ImageButton("<-", false, 16);
        rules3.add(auxPanel3, BorderLayout.SOUTH);
        auxPanel3.add(button3Prev,BorderLayout.WEST);
        button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "4");
            }
        });
        button3Prev.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "2");
            }
        });

        JPanel auxPanel4 = new JPanel();
        auxPanel4.setLayout(new BorderLayout());
        auxPanel4.setOpaque(false);
        ImageButton button4Prev = new ImageButton("<-", false, 16);
        auxPanel4.add(button4Prev,BorderLayout.WEST);
        button4Prev.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "3");
            }
        });
        rules4.add(auxPanel4, BorderLayout.SOUTH);

        cardPanel.add(rules1, "1");
        cardPanel.add(rules2, "2");
        cardPanel.add(rules3, "3");
        cardPanel.add(rules4, "4");
        this.add(cardPanel);
    }

    private void initComponents() {

        rules1 = new ImagePanel("/images/backgrounds/rules_1.png");
        rules2 = new ImagePanel("/images/backgrounds/rules_2.png");
        rules3 = new ImagePanel("/images/backgrounds/rules_3.png");
        rules4 = new ImagePanel("/images/backgrounds/rules_4.png");

    }

    public void showPopUp(boolean isExit) {
        dialog = new JDialog();
        dialog.setContentPane(this);
        dialog.setSize(new Dimension(900, 700));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

}
