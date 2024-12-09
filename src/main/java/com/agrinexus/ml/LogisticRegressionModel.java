package com.agrinexus.ml;

import smile.classification.LogisticRegression;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.IntVector;

public class LogisticRegressionModel implements ML_Model {
    private LogisticRegression model;

    @Override
    public void trainModel(double[][] trainingData, double[] target) {
        // Convert training data to a Smile DataFrame
        DataFrame data = DataFrame.of(trainingData, "Feature1", "Feature2");

        // Convert target (double[]) to int[] for binary classification (0 or 1)
        int[] binaryTarget = new int[target.length];
        for (int i = 0; i < target.length; i++) {
            binaryTarget[i] = target[i] > 0.5 ? 1 : 0;  // Threshold to create binary labels (0 or 1)
        }

        // Add the target column to the DataFrame
        data = data.merge(IntVector.of("Target", binaryTarget));

        // Use a formula to specify the target column for logistic regression
        Formula formula = Formula.lhs("Target");  // 'lhs' refers to the target column

        // Train the logistic regression model
        model = LogisticRegression.fit(formula, data);
        System.out.println("Logistic Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }

        // Predict the class label (0 or 1) for the input
        return model.predict(input); // Returns 0 or 1
    }
}
