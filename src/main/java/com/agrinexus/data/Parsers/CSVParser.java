package com.agrinexus.data.Parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParser extends FileParser {
    public double[][] parseFile(String filePath) throws IOException {
        List<double[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                try {
                    double[] row = Arrays.stream(values)
                                         .mapToDouble(Double::parseDouble)
                                         .toArray();
                    rows.add(row);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid row: " + line);
                }
            }
        }
        return rows.toArray(new double[0][]);
    }
}