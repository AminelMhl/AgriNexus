package com.agrinexus.analysis;

import com.agrinexus.ml.ML_Model;

public class AnalysisEngine {

    private ML_Model model;
    public void trainModel(){
        //train model or something...???
    }
    
    public Forecast forecastModel(){
        Forecast forecast= new Forecast(model);
        return forecast;
    }

    public double correlation(ML_Model model){
        

        return 0;
    }

    public static void generateReport(){

    }
}

// // Use LinearRegression
// ML_Model linearRegression = new LinearRegression();
// linearRegression.trainModel(trainingData, target);

// Forecast forecast = new Forecast(linearRegression);
// System.out.println("Linear Regression Prediction: " + forecast.makePrediction(input));

// // Use RandomForestRegression
// ML_Model randomForest = new RandomForestRegression();
// randomForest.trainModel(trainingData, target);

// Forecast forecast2 = new Forecast(randomForest);
// System.out.println("Random Forest Prediction: " + forecast2.makePrediction(input));
// }


//this should be added in the interface when clicking on any of the forecasting techniques buttons