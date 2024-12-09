package com.agrinexus.data;
import java.util.ArrayList;


public class Data {
    ArrayList<Integer> address; // an array of two cells: longitude and latitude
    String cropType; // a string of either: "wheat" "corn" "olives"
    boolean pesticideUsed; // true(yes), false(no)
    int landSize; // no explanation needed
    int[][] yearlyYield; // yearly yield
    
    // Default constructor
    public Data() {
    }

    // Setter methods
    public void setAddress(ArrayList<Integer> address) {
        this.address = address;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public void setPesticideUsed(boolean pesticideUsed) {
        this.pesticideUsed = pesticideUsed;
    }

    public void setLandSize(int landSize) {
        this.landSize = landSize;
    }

    public void setYearlyYield(int[][] yearlyYield) {
        this.yearlyYield = yearlyYield;
    }

    WeatherDataFetcher w = new WeatherDataFetcher();

    ArrayList<Integer> weatherData=w.fetchWeatherData(address); //arraylist to hold weather api data (humidity, rainfall, sun-hours)

}
