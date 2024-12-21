package com.agrinexus.app;


import java.util.ArrayList;

import com.agrinexus.analysis.AnalysisEngine;
import com.agrinexus.analysis.Forecast;
import com.agrinexus.data.Data;
import com.agrinexus.ui.UserInterface;

public class MainApp {
    private static MainApp appInstance;
    public static void main(String[] args) {
        appInstance = new MainApp();
        appInstance.launch();
    }

    public void launch() {
        System.out.println("Launching the AgriNexus Application...");

        // Initialize components
        UserInterface ui = new UserInterface();

        // data ingestion
        // initalize data from inputs
        // example data for testing
        //ArrayList<Integer> address = ui.address;
        ArrayList<Integer> address = new ArrayList<>();
        address.add(36);//lattitude for testing
        address.add(10);//longitude for testing

        //String filePath = ui.path;
        //FileParser parser = FileParser.getParser(filePath);
        //int[][] yearlyYield = parser.parseFile(filePath);
        int[][] yearlyYield = {
                { 2025, 2024, 2023 },
                { 90, 85, 95 }
        };

        // Using the default constructor and setters
        Data data = new Data();
        data.setAddress(address);
        data.setCropType("Olives");
        data.setPesticideUsed(false);
        data.setLandSize(500);
        data.setYearlyYield(yearlyYield);

        // Perform analysis
        AnalysisEngine engine = new AnalysisEngine();
        engine.trainModel();
        Forecast forecast = engine.forecastModel();

        // Display forecast in UI
        ui.displayGraphs(forecast);

        // Generate reports
        ui.generateReports(engine);
    }
}
