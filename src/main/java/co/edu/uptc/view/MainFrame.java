package co.edu.uptc.view;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;


    public MainFrame() {
        super("Blackjack!");
        initComponents();
        setSize(1400, 1000);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initComponents() {
        mainPanel = new MainPanel();
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
