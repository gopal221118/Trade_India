package com.iadv.extractor;

import java.util.*;
import java.io.*;
import java.util.Set;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MP_Trade_Web_Extractor {



	public static boolean  extractMPTradeValues(WebDriver driver,Map<String,ArrayList<String>> data)  {

		 XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet

		 XSSFSheet sheet = workbook.createSheet("MP_TREADER_DATA SHEET");



		Set<String> keyset = data.keySet();
		System.out.println(keyset);
		int rownum = 0;
		for (String key : keyset) {

			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key).toArray();
			int cellnum = 0;
			for (Object obj : objArr)
			{
				Cell cell = row.createCell(cellnum++);
				if(obj instanceof String)
					cell.setCellValue((String)obj);
				else if(obj instanceof Integer)
					cell.setCellValue((Integer)obj);
			}
		}

//Write the workbook in file system

		try {
			FileOutputStream out = new FileOutputStream(new File("MP_TRADE_DATA.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}




		return true;

	}

}
