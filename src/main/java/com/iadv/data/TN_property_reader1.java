package com.iadv.data;


import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class TN_property_reader1 {

    public HashMap<String, String> readExcel(String filePath) {
        HashMap<String, String> municipalityMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            // Iterate over the rows
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }

                // Get the Municipality_Code (key) and Municipality (value) cells
                Cell municipalityCodeCell = row.getCell(1);
                Cell municipalityCell = row.getCell(0);

                // Convert Municipality_Code (Cell 1) to String, handling numeric and string types
                String municipalityCode = getCellValueAsString(municipalityCodeCell);

                // Convert Municipality (Cell 0) to String
                String municipality = getCellValueAsString(municipalityCell);

                // Add to HashMap
                if (municipalityCode != null && municipality != null) {
                    municipalityMap.put(municipalityCode, municipality);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return municipalityMap;
    }

    // Helper method to get cell value as a String, handling both String and Numeric types
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toString(); // Handle date if needed
            } else {
                // Convert numeric value to String without decimal places
                return String.valueOf((int) cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return Boolean.toString(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            return cell.getCellFormula();
        }

        return null; // Fallback for unsupported or blank cells
    }
}