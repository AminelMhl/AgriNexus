package com.agrinexus.analysis;

import java.util.ArrayList;
import java.util.List;

import com.agrinexus.ml.DecisionTreeRegression;
import com.agrinexus.ml.LinearRegression;
import com.agrinexus.ml.LogisticRegressionModel;
import com.agrinexus.ml.ML_Model;
import com.agrinexus.ml.RandomForestRegression;
import com.agrinexus.reporting.ReportGenerator;

import smile.data.formula.Formula;

public class AnalysisEngine { 
    private Forecast forecast;
    private LinearRegression linearRegression;
    private RandomForestRegression randomForestRegression;
    private DecisionTreeRegression decisionTreeRegression;
    private LogisticRegressionModel logisticRegression;

    public AnalysisEngine() {
        // Initialize models
        linearRegression = new LinearRegression();
        randomForestRegression = new RandomForestRegression();
        decisionTreeRegression = new DecisionTreeRegression();
        logisticRegression = new LogisticRegressionModel();
    }

    public void trainModel(double[][] trainingData, double[] target) {
        // Train all models
        linearRegression.trainModel(trainingData, target);
        randomForestRegression.trainModel(trainingData, target);
        decisionTreeRegression.trainModel(trainingData, target);
        logisticRegression.trainModel(trainingData, target);
    }

    public Forecast getLinearRegressionForecast() {
        forecast = new Forecast(linearRegression);
        return forecast;
    }

    public Forecast getRandomForestRegressionForecast() {
        forecast = new Forecast(randomForestRegression);
        return forecast;
    }

    public Forecast getDecisionTreeRegressionForecast() {
        forecast = new Forecast(decisionTreeRegression);
        return forecast;
    }

    public Forecast getLogisticRegressionForecast() {
        forecast = new Forecast(logisticRegression);
        return forecast;
    }

    public void setLinearRegressionFormula() {
        Formula formula = linearRegression.getFormula();
        forecast.setFormula(formula);
    }

    public void setRandomForestRegressionFormula() {
        Formula formula = randomForestRegression.getFormula();
        forecast.setFormula(formula);
    }

    public void setDecisionTreeRegressionFormula() {
        Formula formula = decisionTreeRegression.getFormula();
        forecast.setFormula(formula);
    }

    public void setLogisticRegressionFormula() {
        Formula formula = logisticRegression.getFormula();
        forecast.setFormula(formula);
    }

    public double correlation(ML_Model model, double[][] trainingData, double[] target) {
        return forecast.correlation(model, trainingData, target);
    }

    public void generateReport(String reportTitle, double[][] trainingData, double[] regressionTargets, ML_Model model) {
        double[] predictions = new double[trainingData.length];        
        
        // Make predictions for each training data point
        for (int i = 0; i < trainingData.length; i++) {
            predictions[i] = model.predict(trainingData[i]);
            System.out.println("Predicted value for input " + arrayToString(trainingData[i]) + ": " + predictions[i]);
        }
        
        // Prepare report content
        List<String> reportContent = new ArrayList<>();
        reportContent.add("Model Performance Report");
        reportContent.add("=========================");
        reportContent.add("Model: " + model.getClass().getSimpleName());
        reportContent.add("Predictions: ");
        
        for (int i = 0; i < predictions.length; i++) {
            reportContent.add("Input: " + arrayToString(trainingData[i]) + " => Prediction: " + predictions[i]);
        }
        
        // Prepare categories and values for the chart
        List<String> categories = new ArrayList<>();
        List<double[]> values = new ArrayList<>();
        
        for (int i = 0; i < predictions.length; i++) {
            categories.add(arrayToString(trainingData[i]));
            values.add(new double[] {predictions[i]}); // Wrap the prediction in an array
        }
        
        // Export the report to PDF
        ReportGenerator.exportPDF(reportTitle, reportContent, categories, values);
    }

    private String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (double value : array) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }
}