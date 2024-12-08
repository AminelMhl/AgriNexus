package com.agrinexus.ml;

public interface ML_Model {
    void trainModel(double[][] trainingData, double[] target);
    double predict(double[] input);
}