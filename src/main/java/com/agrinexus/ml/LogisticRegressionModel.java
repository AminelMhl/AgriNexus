package com.agrinexus.ml;

import smile.classification.LogisticRegression;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;

public class LogisticRegressionModel implements ML_Model {
    private LogisticRegression model;
    private Formula formula;

    

    // 
    @Override
    public void trainModel(double[][] trainingData, double[] target) {
    // Step 1: Convert training data into a DataFrame
    String[] columnNames = new String[trainingData[0].length];
    for (int i = 0; i < trainingData[0].length; i++) {
        columnNames[i] = "Feature" + (i + 1); // Feature1, Feature2, ...
    }
    DataFrame featureData = DataFrame.of(trainingData, columnNames);

    // Step 2: Add the target column to the DataFrame
    DataFrame data = featureData.merge(DoubleVector.of("Target", target));

    // Step 3: Create the formula: Target ~ Feature1 + Feature2 + ...
    StringBuilder formulaBuilder = new StringBuilder("Target ~ ");
    for (int i = 0; i < columnNames.length; i++) {
        formulaBuilder.append(columnNames[i]);
        if (i < columnNames.length - 1) {
            formulaBuilder.append(" + ");
        }
    }
    formula = Formula.of(formulaBuilder.toString());

    // Step 4: Train the logistic regression model
    int[] intTarget = new int[target.length];
    for (int i = 0; i < target.length; i++) {
        intTarget[i] = (int) target[i]; // Convert target to integer values
    }

    model = LogisticRegression.fit(trainingData, intTarget);
    System.out.println("Logistic Regression model trained successfully.");
}

    @Override
    public Formula getFormula() {
        return formula;
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

     

        