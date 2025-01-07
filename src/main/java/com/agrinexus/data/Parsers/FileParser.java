package com.agrinexus.data.Parsers;

public abstract class FileParser {
    public abstract double[][] parseFile(String filePath) throws Exception;

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