package com.agrinexus.ml;

import smile.data.DataFrame;
import smile.data.vector.DoubleVector;
import smile.regression.LinearModel;
import smile.regression.OLS;

public class LinearRegression implements ML_Model {
    private LinearModel model;

    @Override
    public void trainModel(double[][] trainingData, double[] target) {
        // Convert training data to a Smile DataFrame
        DataFrame data = DataFrame.of(trainingData, "Feature1", "Feature2");

        // Add the target column to the DataFrame
        data = data.merge(DoubleVector.of("Target", target));

        // Train the linear regression model
        model = OLS.fit(smile.data.formula.Formula.lhs("Target"), data);
        System.out.println("Linear Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }

        // Predict the target value
        return model.predict(input); // Get the first prediction
    }
}
