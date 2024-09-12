package com.iadv.main;

import java.time.Duration;
import java.util.*;
import com.iadv.extractor.MP_Trade_Web_Extractor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.iadv.mp.MP_Trade_Selenium;

public class MP_Trade_Main {

	public static void main(String args[]) {

		try {
			Map<String, ArrayList<String>> data = new TreeMap<String, ArrayList<String>>();
			data.put("Registration Number", new ArrayList<>(Arrays.asList("lic no.", "Trader Name", "Firm Name",
					"Mobile", "House No./Name", "Street/Locality", "City", "Ward", "Zone")));
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
					MP_Trade_Selenium.getSearchValues(driver, wait, String.valueOf(j), args[3], args[2], data);
				}
				System.out.println("****************************************************");
			}
		

			wait.until(d -> MP_Trade_Web_Extractor.extractMPTradeValues(driver, data,args[5]));
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
