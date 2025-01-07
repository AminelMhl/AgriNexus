package com.agrinexus.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FarmerInfoPanel extends JPanel {
    private JTextField nameField;
    private JButton submitButton;

    public FarmerInfoPanel(ActionListener submitAction) {
        setLayout(new GridLayout(0, 1));

        JLabel nameLabel = new JLabel("Enter Farmer's Name:");
        nameField = new JTextField(20);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(submitAction);

        add(nameLabel);
        add(nameField);
        add(submitButton);
    }

    public String getNameInput() {
        return nameField.getText();
    }
}