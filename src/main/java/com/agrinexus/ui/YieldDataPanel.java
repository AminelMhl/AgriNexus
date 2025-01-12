package com.agrinexus.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.agrinexus.analysis.AnalysisEngine;
import com.agrinexus.data.Parsers.FileParser;
import com.agrinexus.ml.LinearRegression;
import com.agrinexus.ml.ML_Model;

public class YieldDataPanel extends JPanel {

    private JButton uploadButton;
    private JButton submiButton;
    private JTextField filePathField;
    private JFileChooser fileChooser;
    private GUI gui;

    public String getSelectedFilePath() {
        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    public YieldDataPanel(GUI gui) {
        this.gui = gui;
        setLayout(new BorderLayout());

        // Initialize components
        uploadButton = new JButton("Upload");
        submiButton = new JButton("Submit");
        filePathField = new JTextField(20);
        fileChooser = new JFileChooser();

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(YieldDataPanel.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        submiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = getSelectedFilePath();
                if (filePath != null) {
                    FileParser parser = FileParser.getParser(filePath);
                    // Get the data in the uploaded file
                    try {
                        double[][] data = parser.parseFile(filePath);
                        // Separate features (years) and targets (yields)
                        double[][] features = new double[data.length][data[0].length - 1];
                        double[] targets = new double[data.length];
                        
                        for (int i = 0; i < data.length; i++) {
                            for (int j = 0; j < data[i].length - 1; j++) {
                                features[i][j] = data[i][j];
                            }
                            targets[i] = data[i][data[i].length - 1];
                        }
        
                        // Train the model using the parsed data
                        AnalysisEngine analysisEngine = new AnalysisEngine();
                        ML_Model linearRegressionModel = new LinearRegression();
                        linearRegressionModel.trainModel(features, targets);
        
                        // Generate a report based on the trained model
                        double[][] testData = {
                            {2023},
                            {2024},
                            {2025}
                        };
                        double[] actualValues = { /* Add actual values if available */ };
                        analysisEngine.generateReport("Yield Prediction Report", testData, actualValues, linearRegressionModel);

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout());
        filePanel.add(new JLabel("Select Yield Data File:"));
        filePanel.add(filePathField);
        filePanel.add(uploadButton);

        add(filePanel, BorderLayout.CENTER);
        add(submiButton, BorderLayout.SOUTH);
    }

}