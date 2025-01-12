package com.agrinexus.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

import com.agrinexus.analysis.AnalysisEngine;
import com.agrinexus.data.Parsers.FileParser;
import com.agrinexus.ml.LinearRegression;
import com.agrinexus.ml.ML_Model;
import com.agrinexus.reporting.ReportGenerator;

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
                        ReportGenerator reportGenerator = new ReportGenerator();
                        ML_Model linearRegressionModel = new LinearRegression();
                        linearRegressionModel.trainModel(features, targets);
                        String message = reportGenerator.generateReport("Yield Prediction Report", features, targets, linearRegressionModel);
                        JOptionPane.showMessageDialog(null, message, "Report Generation",
                        JOptionPane.INFORMATION_MESSAGE);
                        List<String> categories = new ArrayList<>();
                        List<double[]> values = new ArrayList<>();
                        for (int i = 0; i < targets.length; i++) {
                            categories.add("Year " + (i + 1));
                            values.add(new double[] { targets[i] }); // Wrap the target in an array
                        }
                        ChartPanel chartPanel = ReportGenerator.generateChart("Yield Prediction Report", categories, values);
                        add(chartPanel, BorderLayout.CENTER);
                        revalidate();
                        repaint();
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