package co.edu.uptc.view.game.players;

import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.reusable.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionsPanel extends JPanel {
    private MainFrame frame;
    private JPanel firstLine, secondLine, thirdLine;
    private JButton doblar, quit, settle, ask;
    private TokensPanel tokens;
    private GridBagConstraints gbc;

    public ActionsPanel(MainFrame frame) {
        this.frame = frame;
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
        secondLine.add(settle, gbc);
        secondLine.setOpaque(false);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;

        thirdLine();
    }

    public void thirdLine() {
        thirdLine.add(tokens, gbc);
        gbc.ipadx = 0;
        gbc.ipady = 0;

        addLinesToPanel();
    }

    public void addLinesToPanel() {
        add(firstLine, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(secondLine, gbc);
        add(thirdLine, gbc);
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

        tokens = new TokensPanel();

        addActionListeners();
    }

    public void addActionListeners() {
        ask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setAction("HIT");
            }
        });

        doblar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setAction("DOUBLE");
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setAction("SURRENDER");
            }
        });

        settle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setAction("STAND");
            }
        });

    }

    public void setTokens(int tokens) {
        this.tokens.setTokens(tokens);
    }

    public TokensPanel getTokens() {
        return this.tokens;
    }
}
