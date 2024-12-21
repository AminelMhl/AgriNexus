package com.agrinexus.ml;

import smile.data.formula.Formula;

public interface ML_Model {
    void trainModel(double[][] trainingData, double[] target);
    double predict(double[] input);

    public Formula getFormula();
}

/*testing for the regression models 
 * package com.agrinexus.app;

import com.agrinexus.analysis.Forecast;
import com.agrinexus.ml.DecisionTreeRegression;
import com.agrinexus.ml.LinearRegression;
import com.agrinexus.ml.LogisticRegressionModel;
import com.agrinexus.ml.ML_Model;
import com.agrinexus.ml.RandomForestRegression;


public class MainApp {
    public static void main(String[] args) {
        System.setProperty("java.library.path", "C:\\Program Files\\Java\\jdk-23\\bin");

        System.setProperty("smile.math.blas", "smile.math.blas.NativeBlas");

        // Sample training data
        double[][] trainingData = {
            {1.0, 2.0},
            {2.0, 4.0},
            {3.0, 6.0},
            {4.0, 8.0},
            {5.0, 10.0}
        };
        
        // Targets for regression models
        double[] regressionTargets = {5.0, 10.0, 15.0, 20.0, 25.0};

        // Targets for classification models (Logistic Regression)
        double[] classificationTargets = {0.0, 0.0, 1.0, 1.0, 1.0};

        // Test Linear Regression
        System.out.println("\n--- Linear Regression ---");
        ML_Model linearRegressionModel = new LinearRegression();
        Forecast linearForecast = new Forecast(linearRegressionModel);
        linearForecast.trainModel(trainingData, regressionTargets);
        double linearPrediction = linearForecast.predict(new double[]{6.0, 12.0});
        String linearFormula = linearForecast.getFormula();
        System.out.println("Linear Regression Prediction: " + linearPrediction);
        System.out.println("Linear Regression Formula: " + linearFormula);

        // Test Logistic Regression
        System.out.println("\n--- Logistic Regression ---");
        ML_Model logisticRegressionModel = new LogisticRegressionModel();
        Forecast logisticForecast = new Forecast(logisticRegressionModel);
        logisticForecast.trainModel(trainingData, classificationTargets);
        double logisticPrediction = logisticForecast.predict(new double[]{6.0, 12.0});
        String logisticFormula = logisticForecast.getFormula();
        System.out.println("Logistic Regression Prediction: " + logisticPrediction);
        System.out.println("Logistic Regression Formula: " + logisticFormula);
        
        // Test Decision Tree Regression
        System.out.println("\n--- Decision Tree Regression ---");
        ML_Model decisionTreeModel = new DecisionTreeRegression();
        Forecast decisionTreeForecast = new Forecast(decisionTreeModel);
        decisionTreeForecast.trainModel(trainingData, regressionTargets);
        double decisionTreePrediction = decisionTreeForecast.predict(new double[]{6.0, 12.0});
        String decisionTreeFormula = decisionTreeForecast.getFormula();
        System.out.println("Decision Tree Prediction: " + decisionTreePrediction);
        System.out.println("Decision Tree Formula: " + decisionTreeFormula);

        

        // Test Random Forest Regression
        System.out.println("\n--- Random Forest Regression ---");
        ML_Model randomForestModel = new RandomForestRegression();
        Forecast randomForestForecast = new Forecast(randomForestModel);
        randomForestForecast.trainModel(trainingData, regressionTargets);
        double randomForestPrediction = randomForestForecast.predict(new double[]{6.0, 12.0});
        String randomForestFormula = randomForestForecast.getFormula();
        System.out.println("Random Forest Prediction: " + randomForestPrediction);
        System.out.println("Random Forest Formula: " + randomForestFormula);
    }
 */