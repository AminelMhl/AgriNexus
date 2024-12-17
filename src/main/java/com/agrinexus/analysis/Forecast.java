package com.agrinexus.analysis;

import com.agrinexus.ml.ML_Model;

import smile.data.formula.Formula;
public class Forecast {
    @SuppressWarnings("FieldMayBeFinal")
    private ML_Model model;
    private Formula formula;

    public Forecast(ML_Model model) {
        this.model = model;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public double predict(double[] input) {
        return model.predict(input);
    }

    public double correlation(ML_Model model, double[][] trainingData, double[] target) {
        // the correlation logic is changed here no longer in analysis engine
        double meanY = 0.0;
        for (double t : target) {
            meanY += t;
        }
        meanY /= target.length;

        double ssTotal = 0.0, ssResidual = 0.0;
        for (int i = 0; i < target.length; i++) {
            double predicted = model.predict(trainingData[i]);
            ssResidual += Math.pow(target[i] - predicted, 2);
            ssTotal += Math.pow(target[i] - meanY, 2);
        }
        return 1 - (ssResidual / ssTotal); // R-squared value
    }

    public void trainModel(double[][] trainingData, double[] regressionTargets) {
            model.trainModel(trainingData, regressionTargets);
            
    }
    public String getFormula(ML_Model model) {
        this.formula = model.getFormula();
        
        return formula.toString();
    }

    // we need the formula returned to be able to use it for drawing graphs
    //Actual vs. Predicted Chart
    //Sales: Forecast demand to plan inventory
}

