package com.iadv.extractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class XLSXtoHashMap_Row_Header {

    public static HashMap<String, HashMap<String, String>> getConvertedMap(String xlsxpath) {
        HashMap<String, HashMap<String, String>> commonMap = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream(new File(xlsxpath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            String[] headers = new String[headerRow.getLastCellNum()];

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                headers[i] = headerRow.getCell(i).toString();
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                HashMap<String, String> cellMap = new HashMap<>();

                // Assuming Application_No is in the second column (index 1)
                String applicationNo = row.getCell(1).toString(); // Application_No as key

                // Iterate through each cell in the row
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = (cell != null) ? cell.toString() : "";
                    cellMap.put(headers[j], cellValue);
                }

                // Put the application number and cell map into the main HashMap
                commonMap.put(applicationNo, cellMap);
            }
            workbook.close();
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return commonMap;
    }
}
