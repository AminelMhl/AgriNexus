package com.agrinexus.ml;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;
import smile.data.type.StructType;
import smile.data.vector.DoubleVector;
import smile.regression.RegressionTree;

public class DecisionTreeRegression implements ML_Model {
    private RegressionTree model;
    private Formula formula;
    private StructType schema;

    @Override
    public Formula getFormula() {
        return formula;
    }

    @Override
    public void trainModel(double[][] trainingData, double[] target) {
            // Step 1: Generate dynamic feature column names
        String[] columnNames = new String[trainingData[0].length];
        for (int i = 0; i < trainingData[0].length; i++) {
            columnNames[i] = "Feature" + (i + 1); // Feature1, Feature2, ..., FeatureN
        }

        // Step 2: Convert training data to a Smile DataFrame
        DataFrame featureData = DataFrame.of(trainingData, columnNames);

        // Step 3: Add the target column to the DataFrame
        DataFrame data = featureData.merge(DoubleVector.of("Target", target));
        schema = data.schema();

        // Step 4: Build the formula dynamically: Target ~ Feature1 + Feature2 + ...
        StringBuilder formulaBuilder = new StringBuilder("Target ~ ");
        for (int i = 0; i < columnNames.length; i++) {
            formulaBuilder.append(columnNames[i]);
            if (i < columnNames.length - 1) {
                formulaBuilder.append(" + ");
            }
        }
        formula = Formula.of(formulaBuilder.toString());

        // Train the decision tree regression model
        model = RegressionTree.fit(formula, data, 5,3, 10);
        System.out.println("Decision Tree Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }
        if (schema == null) {
            throw new IllegalStateException("Schema has not been initialized!");
        }
        Tuple tuple = Tuple.of(input, schema);
        double prediction = model.predict(tuple);
        return prediction;
    }
}
