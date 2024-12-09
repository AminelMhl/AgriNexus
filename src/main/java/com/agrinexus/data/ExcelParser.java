

package com.agrinexus.data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ExcelParser extends FileParser {
@Override
public int[][] parseFile(String filePath) throws Exception {
    FileInputStream fis = new FileInputStream(filePath);
    Workbook workbook = new XSSFWorkbook(fis);
    Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
    ArrayList<int[]> data = new ArrayList<>();

    for (Row row : sheet) {
        int[] rowData = new int[row.getLastCellNum()];
        int i = 0;
        for (Cell cell : row) {
            rowData[i++] = (int) cell.getNumericCellValue(); // Convert to integer
        }
        data.add(rowData);
    }
    workbook.close();

    // Convert ArrayList<int[]> to int[][]
    return data.toArray(new int[data.size()][]);
}
}