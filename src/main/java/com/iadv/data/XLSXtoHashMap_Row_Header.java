package com.iadv.data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class XLSXtoHashMap_Row_Header {

	public static  HashMap<Integer, HashMap<String, String>> getConvertedMap(String xlsxpath) {
		 HashMap<Integer, HashMap<String, String>> commonMap = new  HashMap<Integer, HashMap<String, String>>();
		try {
			FileInputStream file = new FileInputStream(new File(xlsxpath));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);
			
			 Row headerRow = sheet.getRow(0);
	            List<String> headers = new ArrayList<>();
	            for (Cell headerCell : headerRow) {
	                headers.add(headerCell.toString());
	            }

	            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	                Row row = sheet.getRow(i);
	                HashMap<String, String> cellMap = new HashMap<>();
	                
	                // Iterate through each cell in the row
	                for (int j = 0; j < row.getLastCellNum(); j++) {
	                    Cell cell = row.getCell(j);
	                    String cellValue = (cell != null) ? cell.toString() : "";
	                    cellMap.put(headers.get(j), cellValue);
	                }
	                
	                // Put the row number and cell map into the main HashMap
	                commonMap.put(i, cellMap);
	            }
			workbook.close();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonMap;

	}

}
