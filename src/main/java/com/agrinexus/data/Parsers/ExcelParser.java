

package com.agrinexus.data.Parsers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ExcelParser extends FileParser {
    @Override
    public double[][] parseFile(String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
        ArrayList<double[]> data = new ArrayList<>();

        for (Row row : sheet) {
            double[] rowData = new double[row.getLastCellNum()];
            int i = 0;
            for (Cell cell : row) {
                rowData[i++] = cell.getNumericCellValue(); // Convert to double
            }
            data.add(rowData);
        }
        workbook.close();

        // Convert ArrayList<double[]> to double[][]
        double[][] dataArray = new double[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i);
        }
        return dataArray;
    }
}