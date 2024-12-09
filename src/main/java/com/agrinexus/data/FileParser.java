package com.agrinexus.data;

public abstract class FileParser {
    abstract int[][] parseFile(String filePath) throws Exception; // Method to parse and convert data
    public static FileParser getParser(String filePath) {
        if (filePath.endsWith(".csv")) {
            return new CSVParser();
        } else if (filePath.endsWith(".xlsx")) {
            return new ExcelParser();
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + filePath);
        }
    }
}