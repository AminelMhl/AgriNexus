package com.agrinexus.ml;

import smile.data.formula.Formula;

public interface ML_Model {
    void trainModel(double[][] trainingData, double[] target);
    double predict(double[] input);

    public Formula getFormula();
}
