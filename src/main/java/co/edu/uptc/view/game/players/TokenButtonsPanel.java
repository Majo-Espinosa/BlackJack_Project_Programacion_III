package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import co.edu.uptc.view.MainFrame;
import co.edu.uptc.view.game.draw.TokenButton;

public class TokenButtonsPanel extends  JPanel {
    private BottomPanel bottomPanel;

    private MainFrame frame;
    private TokenButton ten;
    private TokenButton fifty;
    private TokenButton hundred;
    private TokenButton twoHundred;
    private TokenButton fiveHundred;
    private TokenButton thousand;
    private GridBagConstraints gbc;

    public TokenButtonsPanel(BottomPanel bottomPanel, MainFrame frame) {
        this.frame = frame;
        this.bottomPanel = bottomPanel;
        initcomponents();
        this.setLayout(new GridBagLayout());
        setBackground(new java.awt.Color(4, 45, 6));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        gbc.insets = new Insets(0, 5, 5, 5);

        this.add(ten,gbc);
        this.add(fifty,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(hundred,gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 5, 0, 5);
        this.add(twoHundred,gbc);
        this.add(fiveHundred,gbc);
        this.add(thousand,gbc);
        
    }

    private void initcomponents() {
        gbc = new GridBagConstraints();
        ten = new TokenButton(0, 162,"10");
       
        fifty = new TokenButton(0, 162,"50");
        
        hundred = new TokenButton(0, 162,"100");
      
        twoHundred = new TokenButton(0, 162,"200");
      
        fiveHundred = new TokenButton(0, 162,"500");
     
        thousand = new TokenButton(0, 162,"1K");

        setTokensActions();
    }

    private void setTokensActions() {
        ten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setBet(10);
            }
        });

        fifty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setBet(50);
            }
        });

        hundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setBet(100);
            }
        });

        twoHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setBet(200);
            }
        });

        fiveHundred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setBet(500);
            }
        });

        thousand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setBet(1000);
            }
        });
    }




}
