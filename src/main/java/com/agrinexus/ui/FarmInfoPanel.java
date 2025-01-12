package com.agrinexus.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FarmInfoPanel extends JPanel {
    private JButton addFarmButton;

    public FarmInfoPanel(ActionListener addFarmAction) {
        setLayout(new GridLayout(0, 1));

        addFarmButton = new JButton("Add new Farm");
        addFarmButton.addActionListener(addFarmAction);

        add(addFarmButton);
    }
}