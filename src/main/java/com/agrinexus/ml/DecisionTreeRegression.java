package com.agrinexus.ml;

import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;
import smile.regression.RegressionTree;

public class DecisionTreeRegression implements ML_Model {
    private RegressionTree model;

    @Override
    public void trainModel(double[][] trainingData, double[] target) {
        // Convert training data to a Smile DataFrame
        DataFrame data = DataFrame.of(trainingData, "Feature1", "Feature2");

        // Add the target column to the DataFrame
        data = data.merge(DoubleVector.of("Target", target));

        // Create a Formula for the dependent (target) and independent (features) variables
        Formula formula = Formula.lhs("Target");

        // Train the decision tree regression model using the formula and data
        model = RegressionTree.fit(formula, data);
        System.out.println("Decision Tree Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }

        // Convert the input to a DataFrame
        DataFrame inputData = DataFrame.of(new double[][]{input}, "Feature1", "Feature2");

        // Predict the target value for the input using the trained model
        double[] prediction = model.predict(inputData);  // This returns a DataFrame with predictions
        
        // Extract the prediction value (double) from the first row of the prediction column
        return prediction[0];  // Get the first predicted value
    }
}
