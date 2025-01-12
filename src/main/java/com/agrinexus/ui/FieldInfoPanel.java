package com.agrinexus.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldInfoPanel extends JPanel {
    private JTextField fieldSizeField;
    private JComboBox<String> cropSelector;
    private JButton addFieldButton;
    private JPanel farmPanel;

    public FieldInfoPanel(ActionListener addFieldAction) {
        setLayout(new BorderLayout());

        fieldSizeField = new JTextField(20);
        cropSelector = new JComboBox<>(new String[] {"Oranges", "Olives", "Dates", "Pepper", "Tomatoes", "Potatoes", "Carrots"});
        addFieldButton = new JButton("Add new Field");
        addFieldButton.addActionListener(addFieldAction);

        farmPanel = new JPanel();
        farmPanel.setLayout(new BoxLayout(farmPanel, BoxLayout.Y_AXIS));

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(0, 1));
        fieldPanel.add(new JLabel("Enter Field Size:"));
        fieldPanel.add(fieldSizeField);
        fieldPanel.add(new JLabel("Select Crop Type:"));
        fieldPanel.add(cropSelector);
        fieldPanel.add(addFieldButton);

        add(fieldPanel, BorderLayout.CENTER);
        add(farmPanel, BorderLayout.SOUTH);
    }

    public String getFieldSizeInput() {
        return fieldSizeField.getText();
    }

    public String getSelectedCrop() {
        return (String) cropSelector.getSelectedItem();
    }

    public void addFieldInput() {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(0, 1));

        JLabel fieldSizeLabel = new JLabel("Enter Field Size:");
        JTextField fieldSizeField = new JTextField(20);

        JLabel cropTypeLabel = new JLabel("Select Crop Type:");
        JComboBox<String> cropSelector = new JComboBox<>(new String[] {"Oranges", "Olives", "Dates", "Pepper", "Tomatoes", "Potatoes", "Carrots"});
        fieldPanel.add(fieldSizeLabel);
        fieldPanel.add(fieldSizeField);
        fieldPanel.add(cropTypeLabel);
        fieldPanel.add(cropSelector);

        farmPanel.add(fieldPanel);
        farmPanel.revalidate();
        farmPanel.repaint();
    }
}