package com.agrinexus.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVParser extends FileParser {
@Override
public int[][] parseFile(String filePath) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(filePath));
    ArrayList<int[]> data = new ArrayList<>();
    String line;

    while ((line = br.readLine()) != null) {
        String[] values = line.split(","); // Split by commas
        int[] row = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            row[i] = Integer.parseInt(values[i].trim()); // Convert to integers
        }
        data.add(row);
    }
    br.close();

    // Convert ArrayList<int[]> to int[][]
    return data.toArray(new int[data.size()][]);
}
}