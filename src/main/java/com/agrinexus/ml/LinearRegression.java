package com.agrinexus.ml;

import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;
import smile.regression.LinearModel;
import smile.regression.OLS;

public class LinearRegression implements ML_Model {
    private LinearModel model;
    private Formula formula;

    @SuppressWarnings("override")
    public Formula getFormula() {
        return formula;
    }

    @Override
    public void trainModel(double[][] trainingData, double[] target) {
        // // Convert training data to a Smile DataFrame
        // DataFrame data = DataFrame.of(trainingData, "Feature1", "Feature2");
        // data = data.merge(DoubleVector.of("Target", target));

        // // Set the formula for the regression
        // formula = Formula.lhs("Target");
        // Step 1: Generate dynamic feature column names
        String[] columnNames = new String[trainingData[0].length];
        for (int i = 0; i < trainingData[0].length; i++) {
            columnNames[i] = "Feature" + (i + 1); // Feature1, Feature2, ..., FeatureN
        }

        // Step 2: Convert training data to a Smile DataFrame
        DataFrame featureData = DataFrame.of(trainingData, columnNames);

        // Step 3: Add the target column to the DataFrame
        DataFrame data = featureData.merge(DoubleVector.of("Target", target));

        // Step 4: Build the formula dynamically: Target ~ Feature1 + Feature2 + ...
        StringBuilder formulaBuilder = new StringBuilder("Target ~ ");
        for (int i = 0; i < columnNames.length; i++) {
            formulaBuilder.append(columnNames[i]);
            if (i < columnNames.length - 1) {
                formulaBuilder.append(" + ");
            }
        }
        formula = Formula.of(formulaBuilder.toString());

        // Train the linear regression model
        model = OLS.fit(formula, data);
        System.out.println("Linear Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }
        return model.predict(input);
    }
}
