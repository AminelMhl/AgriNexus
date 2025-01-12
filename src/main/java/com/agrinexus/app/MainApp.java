package com.agrinexus.app;

import com.agrinexus.analysis.AnalysisEngine;
import com.agrinexus.ml.DecisionTreeRegression;
import com.agrinexus.ml.LinearRegression;
import com.agrinexus.ml.LogisticRegressionModel;
import com.agrinexus.ml.ML_Model;
import com.agrinexus.ml.RandomForestRegression;
import com.agrinexus.ui.Controller;

public class MainApp {
    public static void main(String[] args) {
        // Initialize the controller for UI
        Controller controller = new Controller();

        // Get the actual training data and targets from the controller
        double[][] trainingData = controller.getTrainingData();
        double[] regressionTargets = controller.getRegressionTargets();

        // Check if data is available
        if (trainingData != null && regressionTargets != null) {
            // Create an instance of AnalysisEngine
            AnalysisEngine analysisEngine = new AnalysisEngine();
            
            // Train models
            analysisEngine.trainModel(trainingData, regressionTargets);

            // Generate report for Linear Regression
            ML_Model linearRegressionModel = new LinearRegression();
            linearRegressionModel.trainModel(trainingData, regressionTargets); // Train the model
            analysisEngine.generateReport("Linear Regression Report", trainingData, regressionTargets, linearRegressionModel);

            // Generate report for Random Forest Regression
            ML_Model randomForestModel = new RandomForestRegression();
            randomForestModel.trainModel(trainingData, regressionTargets); // Train the model
            analysisEngine.generateReport("Random Forest Regression Report", trainingData, regressionTargets, randomForestModel);

            // Generate report for Decision Tree Regression
            ML_Model decisionTreeModel = new DecisionTreeRegression();
            decisionTreeModel.trainModel(trainingData, regressionTargets); // Train the model
            analysisEngine.generateReport("Decision Tree Regression Report", trainingData, regressionTargets, decisionTreeModel);

            // Generate report for Logistic Regression (if applicable)
            ML_Model logisticRegressionModel = new LogisticRegressionModel();
            logisticRegressionModel.trainModel(trainingData, regressionTargets); // Train the model
            analysisEngine.generateReport("Logistic Regression Report", trainingData, regressionTargets, logisticRegressionModel);
        } else {
            System.out.println("No training data available.");
        }
    }
}