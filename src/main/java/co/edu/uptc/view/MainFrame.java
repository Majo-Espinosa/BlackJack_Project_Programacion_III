package co.edu.uptc.view;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import co.edu.uptc.view.popups.MessageDialog;
import co.edu.uptc.view.reusable.Constants;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;

    public MainFrame() {
        super(Constants.FRAME_TITLE);
        initComponents();
        setSize(Constants.WINDOW_DIMENSION);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setIcon();
    }

    private void setIcon() {
        URL iconURL = getClass().getResource(Constants.FRAME_ICON_PATH);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            this.setIconImage(icon.getImage());
        }
    }

    public final void initComponents() {
        mainPanel = new MainPanel();
        setContentPane(mainPanel);
    }

    public void showMessage(String message) {
        new MessageDialog(message).showPopUp();
    }

    public int promptBet(int balance, int minBet, int maxBet) {
        System.out.println("\n---FASE DE APUESTAS---");
        System.out.println("Balance actual: $" + balance);
        System.out.println("Apuesta mínima: $" + minBet + " | Apuesta máxima: $" + maxBet);
        System.out.print("Ingresa tu apuesta (tienes 20 segundos): $");

        try {
            String input = consoleReader.readLine();
            int bet = Integer.parseInt(input);

            if (bet < minBet)
                bet = minBet;
            if (bet > maxBet)
                bet = maxBet;
            if (bet > balance)
                bet = balance;

            return bet;
        } catch (IOException | NumberFormatException e) {
            return minBet; // Apuesta mínima por defecto
        }
    }

    public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame();
    }

}
