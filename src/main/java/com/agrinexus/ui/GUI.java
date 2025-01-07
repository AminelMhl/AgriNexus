package com.agrinexus.ui;
import javax.swing.*;

import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GUI(ActionListener submitAction, ActionListener addFarmAction, ActionListener addFieldAction) {
        frame = new JFrame("Agrinexus");
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        mainPanel.setLayout(new CardLayout());
        cardLayout = (CardLayout) mainPanel.getLayout();

        FarmerInfoPanel farmerInfoPanel = new FarmerInfoPanel(submitAction);
        FarmInfoPanel farmInfoPanel = new FarmInfoPanel(addFarmAction);
        FieldInfoPanel fieldInfoPanel = new FieldInfoPanel(addFieldAction);
        YieldDataPanel yieldDataPanel = new YieldDataPanel(this);

        mainPanel.add(farmerInfoPanel, "Phase1");
        mainPanel.add(farmInfoPanel, "Phase2");
        mainPanel.add(fieldInfoPanel, "Phase3");
        mainPanel.add(yieldDataPanel, "Phase4");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 300);
        frame.setVisible(true);
    }

    public void displayReport(List<String> reportContent, ChartPanel chartPanel) {
        // Create a new panel to display the report and chart
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new BorderLayout());

        // Add report content to the panel
        JTextArea reportTextArea = new JTextArea();
        for (String line : reportContent) {
            reportTextArea.append(line + "\n");
        }
        reportPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);

        // Add chart to the panel
        reportPanel.add(chartPanel, BorderLayout.SOUTH);

        // Add the report panel to the main panel
        mainPanel.add(reportPanel, "Report");
        cardLayout.show(mainPanel, "Report");
    }

    public void showNextPhase() {
        cardLayout.next(mainPanel);
    }

    public String getNameInput() {
        return ((FarmerInfoPanel) mainPanel.getComponent(0)).getNameInput();
    }

    public String getFieldSizeInput() {
        return ((FieldInfoPanel) mainPanel.getComponent(2)).getFieldSizeInput();
    }

    public String getSelectedCrop() {
        return ((FieldInfoPanel) mainPanel.getComponent(2)).getSelectedCrop();
    }

    public void addFieldInput() {
        ((FieldInfoPanel) mainPanel.getComponent(2)).addFieldInput();
    }
}