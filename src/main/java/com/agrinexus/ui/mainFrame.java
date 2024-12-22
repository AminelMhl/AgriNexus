package com.agrinexus.ui;
import java.awt.*;

import javax.swing.*;
public class mainFrame extends JFrame {
    public void initalize(User user ){

    JPanel infoPanel= new JPanel();
    infoPanel.setLayout(new GridLayout(0,2,5,5));


        setTitle("Dashboard");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE  );
        setSize(1100,650);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
