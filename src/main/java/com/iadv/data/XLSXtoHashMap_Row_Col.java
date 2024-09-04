package com.iadv.data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class XLSXtoHashMap_Row_Col {

	public static HashMap<Integer, ArrayList<String>> getConvertedMap(String xlsxpath) {
		HashMap<Integer, ArrayList<String>> commonMap = new HashMap<Integer, ArrayList<String>>();
		try {
			FileInputStream file = new FileInputStream(new File(xlsxpath));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);

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
