package co.edu.uptc.view.game.players;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import co.edu.uptc.view.game.draw.TokenButton;

public class TokenButtonsPanel extends  JPanel {
    
    private TokenButton ten;
    private TokenButton fifty;
    private TokenButton hundred;
    private TokenButton twoHundred;
    private TokenButton fiveHundred;
    private TokenButton thousand;
    private GridBagConstraints gbc;

    public TokenButtonsPanel(){
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
        

    }




}
