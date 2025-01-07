package com.agrinexus.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

import com.agrinexus.analysis.AnalysisEngine;
import com.agrinexus.analysis.Forecast;
import com.agrinexus.data.Parsers.FileParser;
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
                    //get the data in the uploaded file
                    try{
                        double[][] data = parser.parseFile(filePath);
                        //prints the data in the array for debugging
                        for (int i = 0; i < data.length; i++) {
                            for (int j = 0; j < data[i].length; j++) {
                                System.out.print(data[i][j] + " ");
                            }}
                    }
                    catch(Exception ex){
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