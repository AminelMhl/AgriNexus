package com.agrinexus.ml;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;
import smile.data.type.StructType;
import smile.data.vector.DoubleVector;
import smile.regression.RandomForest;

public class RandomForestRegression implements ML_Model {
    private RandomForest model;
    private Formula formula;
    private StructType schema; // Save the schema of the training DataFrame

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
        int ntrees = 5;       // Number of trees
        int maxDepth = 2;      // Maximum depth
        int maxNodes = 10;     // Maximum number of nodes
        int nodeSize = 2;       // Minimum size of nodes
        int totalFeatures = columnNames.length;  // Total number of features in your data
        int mtry = Math.min(1, totalFeatures);   // Ensures mtry <= total features
        double subsample = 1.0;
        model = RandomForest.fit(formula, data, ntrees, maxDepth, maxNodes, nodeSize, mtry, subsample);
        System.out.println("Random Forest Regression model trained successfully.");
    }

    @Override
    public double predict(double[] input) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained yet!");
        }
        if (schema == null) {
            throw new IllegalStateException("Schema has not been initialized!");
        }

        // Step 6: Create a Tuple using the input and the schema
        Tuple tuple = Tuple.of(input, schema);

        // Predict the target value using the trained model
        double prediction = model.predict(tuple);
        System.out.println("Prediction: " + prediction);
        return prediction;
    }

    @Override
    public Formula getFormula() {
        return formula;
    }
}
