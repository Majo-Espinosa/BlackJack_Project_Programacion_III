package co.edu.uptc.client.game.players;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import co.edu.uptc.client.reusable.ImageButton;

public class ActionsPanel extends JPanel {
    private JPanel firstLine, secondLine, thirdLine;
    private JButton doblar, quit, settle, divide, ask;
    private TokensPanel tokens;
    private GridBagConstraints gbc;

    public ActionsPanel() {
        setBackground(new java.awt.Color(4, 45, 6));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        setLayout(new GridBagLayout());
        initComponents();
        firstLine();
    }

    public final void firstLine() {
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.ipadx = 5;
        gbc.ipady = 10;

        firstLine.add(ask, gbc);
        firstLine.add(doblar, gbc);
        firstLine.add(quit, gbc);
        firstLine.setOpaque(false);

        secondLine();
    }

    public void secondLine() {
        secondLine.add(settle,gbc);
        secondLine.add(divide,gbc);
        secondLine.setOpaque(false);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;

        thirdLine();
    }

    public void thirdLine() {
        thirdLine.add(tokens,gbc);
        gbc.ipadx = 0;
        gbc.ipady = 0;

        addLinesToPanel();
    }

    public void addLinesToPanel() {
        add(firstLine,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(secondLine,gbc);
        add(thirdLine,gbc);
    }

    private void initComponents() {
        firstLine = new JPanel();
        secondLine = new JPanel();
        thirdLine = new JPanel();
        gbc = new GridBagConstraints();

        firstLine.setLayout(new GridBagLayout());
        secondLine.setLayout(new GridBagLayout());
        thirdLine.setLayout(new GridBagLayout());

        ask = new ImageButton("Pedir", false, 10);
        doblar = new ImageButton("Doblar", false, 10);
        quit = new ImageButton("Rendirse", false, 10);
        settle = new ImageButton("Quedarse", false, 10);
        divide = new ImageButton("Dividir", false, 10);

        tokens = new TokensPanel();
    }

}
