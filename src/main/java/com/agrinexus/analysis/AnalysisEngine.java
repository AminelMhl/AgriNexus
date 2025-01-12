package com.agrinexus.analysis;

import com.agrinexus.ml.DecisionTreeRegression;
import com.agrinexus.ml.LinearRegression;
import com.agrinexus.ml.LogisticRegressionModel;
import com.agrinexus.ml.ML_Model;
import com.agrinexus.ml.RandomForestRegression;

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
}