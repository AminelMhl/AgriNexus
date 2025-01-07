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

        // controller for UI
        new Controller();

        double[][] trainingData = {
            {1.0, 2.0},
            {2.0, 4.0},
            {3.0, 6.0},
            {4.0, 8.0},
            {5.0, 10.0}
        };
        
        // Targets for regression models
        double[] regressionTargets = {5.0, 10.0, 15.0, 20.0, 25.0};

        // Create an instance of AnalysisEngine
        AnalysisEngine analysisEngine = new AnalysisEngine();
        
        // Train models
        analysisEngine.trainModel(trainingData, regressionTargets);

        // Sample test data
        double[][] testData = {
            {6.0, 12.0},
            {7.0, 14.0},
            {8.0, 16.0}
        };
        
        // Actual values for the test data (for performance evaluation)
        double[] actualValues = {30.0, 35.0, 40.0}; // Example actual values

        // Generate report for Linear Regression
        ML_Model linearRegressionModel = new LinearRegression();
        linearRegressionModel.trainModel(trainingData, regressionTargets); // Train the model
        analysisEngine.generateReport("Linear Regression Report", testData, actualValues, linearRegressionModel);

        // Generate report for Random Forest Regression
        ML_Model randomForestModel = new RandomForestRegression();
        randomForestModel.trainModel(trainingData, regressionTargets); // Train the model
        analysisEngine.generateReport("Random Forest Regression Report", testData, actualValues, randomForestModel);

        // Generate report for Decision Tree Regression
        ML_Model decisionTreeModel = new DecisionTreeRegression();
        decisionTreeModel.trainModel(trainingData, regressionTargets); // Train the model
        analysisEngine.generateReport("Decision Tree Regression Report", testData, actualValues, decisionTreeModel);

        // Generate report for Logistic Regression (if applicable)
        ML_Model logisticRegressionModel = new LogisticRegressionModel();
        logisticRegressionModel.trainModel(trainingData, regressionTargets); // Train the model
        analysisEngine.generateReport("Logistic Regression Report", testData, actualValues, logisticRegressionModel);
    }
}