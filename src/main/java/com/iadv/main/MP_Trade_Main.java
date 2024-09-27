package com.iadv.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import com.iadv.extractor.MP_Trade_Web_Extractor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.iadv.mp.MP_Trade_Selenium;
import com.opencsv.CSVWriter;

public class MP_Trade_Main {

	public static void main(String args[]) {

		try {
			String csvFile = args[5];
	        CSVWriter writer = null;
	        
	        try {
	            writer = new CSVWriter(new FileWriter(csvFile));
	              writer.writeNext(new String[]{"lic no.", "Trader Name", "Firm Name",
	  					"Mobile", "House No./Name", "Street/Locality", "City", "Ward", "Zone"         
	                });
	        } catch (IOException e) {
	            e.printStackTrace();
	            return; 
	        }
		
			HashMap<Integer, ArrayList<String>> mpMap = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
			System.out.println(mpMap);
			WebDriver driver = new ChromeDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			MP_Trade_Selenium.extractTradeValues(args[1], args[2], args[3], driver, wait);
			for (int i = 1; i < mpMap.size(); i++) {
				double licenseno = Double.valueOf((mpMap.get(i).get(0)));
				long licensenox = (long) licenseno;
				System.out.println("****************************************************");
				for (long j = licensenox - (Integer.parseInt(args[4])); j < licensenox + (Integer.parseInt(args[4])); j++) {
					System.out.println("Entering into parent: "+licensenox);
					System.out.println(j);
					MP_Trade_Selenium.getSearchValues(driver, wait, String.valueOf(j), args[3], args[2], writer,args[6]);
				}
				System.out.println("****************************************************");
			}
			driver.quit();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
