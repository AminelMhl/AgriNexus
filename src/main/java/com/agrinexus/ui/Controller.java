package com.agrinexus.ui;

import com.agrinexus.data.Farmer;
import com.agrinexus.data.Farm;
import com.agrinexus.data.Field;
import com.agrinexus.data.Parsers.FileParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Controller {
    private GUI gui;
    private ArrayList<Farmer> farmers = new ArrayList<>();
    private Farmer currentFarmer;

    public Controller() {
        gui = new GUI(new SubmitAction(), new AddFarmAction(), new AddFieldAction());
    }

    // Add new farmer
    class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Farmer farmer = new Farmer();
            farmer.setNameFromGUI(gui);
            farmers.add(farmer);
            currentFarmer = farmer;
            System.out.println("Farmer " + farmer.getName() + " added");
            gui.showNextPhase();
        }
    }

    class AddFarmAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Farm farm = new Farm();
            currentFarmer.addFarm(farm);
            System.out.println("Farm added to farmer " + currentFarmer.getName() + ".");
            gui.showNextPhase();
        }
    }

    class AddFieldAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int size = Integer.parseInt(gui.getFieldSizeInput());
            String cropType = gui.getSelectedCrop();
            Field field = new Field(size, cropType);
            currentFarmer.getFarms().get(currentFarmer.getFarms().size() - 1).addField(field);
            System.out.println("Field added to farm.");
            gui.addFieldInput();
            gui.showNextPhase();
        }
    }

    public double[][] getTrainingData() {
        // Assuming the file path is provided through the GUI
        String filePath = gui.getSelectedFilePath();
        if (filePath != null) {
            FileParser parser = FileParser.getParser(filePath);
            try {
                double[][] data = parser.parseFile(filePath);
                // Separate features (years) and targets (yields)
                double[][] features = new double[data.length][data[0].length - 1];
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length - 1; j++) {
                        features[i][j] = data[i][j];
                    }
                }
                return features;
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return null;
    }

    public double[] getRegressionTargets() {
        // Assuming the file path is provided through the GUI
        String filePath = gui.getSelectedFilePath();
        if (filePath != null) {
            FileParser parser = FileParser.getParser(filePath);
            try {
                double[][] data = parser.parseFile(filePath);
                double[] targets = new double[data.length];
                for (int i = 0; i < data.length; i++) {
                    targets[i] = data[i][data[i].length - 1];
                }
                return targets;
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return null;
    }
}