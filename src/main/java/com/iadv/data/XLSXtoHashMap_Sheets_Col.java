package com.iadv.data;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class XLSXtoHashMap_Sheets_Col {
	
	  public static int getNumberOfSheets(String xlsxpath) {
	        int sheetCount = 0;
	        try {
	            FileInputStream file = new FileInputStream(new File(xlsxpath));
	            Workbook workbook = new XSSFWorkbook(file);
	            sheetCount = workbook.getNumberOfSheets();
	            workbook.close();
	            file.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return sheetCount;
	    }

    public static HashMap<Integer, ArrayList<String>> getConvertedMap(String xlsxpath,int sheetcount) {
        HashMap<Integer, ArrayList<String>> commonMap = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream(new File(xlsxpath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(sheetcount);

            for (Row row : sheet) {
                ArrayList<String> cellValues = new ArrayList<>();
                for (Cell cell : row) {
                    String cellValue = cell.toString();
                    cellValues.add(cellValue);
                }
                commonMap.put(row.getRowNum(), cellValues);
            }

            workbook.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commonMap;
    }

  
}
