package co.edu.uptc.view.login;

import co.edu.uptc.view.MainPanel;
import co.edu.uptc.view.reusable.ImageButton;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private MainPanel mainPanel;

    public LoginPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 1;
        add(crearFranjaCampoTexto(), gbc);

        gbc.gridy = 2;
        add(crearFranjaBoton("JUGAR"), gbc);

        gbc.gridy = 3;
        add(crearFranjaBoton("SALIR"), gbc);
    }

    private JPanel crearFranjaCampoTexto() {
        JPanel franja = new JPanel(new GridBagLayout());
        franja.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(200, 20, 0, 20);
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        UsernameTextField campoTexto = new UsernameTextField();

        franja.add(campoTexto, gbc);
        return franja;
    }

    private JPanel crearFranjaBoton(String textoBoton) {
        JPanel franja = new JPanel(new GridBagLayout());
        franja.setOpaque(false);

        ImageButton boton = new ImageButton(textoBoton, false, 20) {

            {
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);

                addActionListener(_ -> {
                    if (textoBoton.equals("JUGAR")) {
                        mainPanel.updatePanel("game", false);
                    } else {
                        mainPanel.updatePanel("menu", true);
                    }
                });
            }
        };

        boton.setPreferredSize(new Dimension(250, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        franja.add(boton, gbc);

        return franja;
    }
}
