package com.agrinexus.ml;

import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;
import smile.regression.RandomForest;

public class RandomForestRegression implements ML_Model {
    private RandomForest model;

    @Override
    public void trainModel(double[][] trainingData, double[] target) {
        // Convert training data to a Smile DataFrame
        DataFrame data = DataFrame.of(trainingData, "Feature1", "Feature2");

        // Add the target column to the DataFrame
        data = data.merge(DoubleVector.of("Target", target));

        // Create a Formula for the dependent (target) and independent (features) variables
        Formula formula = Formula.lhs("Target");

        // Train the random forest regression model using the formula and data
        model = RandomForest.fit(formula, data);
        System.out.println("Random Forest Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }

        // Convert the input to a DataFrame (the same structure as the training data)
        DataFrame inputData = DataFrame.of(new double[][]{input}, "Feature1", "Feature2");

        // Predict the target value for the input using the trained model
        double[] prediction = model.predict(inputData);  // This returns a single predicted value
        
        return prediction[0];  // Return the predicted value
    }
}
