package com.agrinexus.app;


import com.agrinexus.analysis.AnalysisEngine;
import com.agrinexus.analysis.Forecast;
import com.agrinexus.data.Data;
import com.agrinexus.data.DataManager;
import com.agrinexus.data.LocalDataSource;
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
        LocalDataSource localSource = new LocalDataSource();
        DataManager dataManager = new DataManager(localSource);

        // Fetch data
        Data data = dataManager.importData();
        data.validate();

        // Preprocess data
        dataManager.preprocessData();

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
