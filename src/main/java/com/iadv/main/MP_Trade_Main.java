package com.iadv.main;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.*;

import com.iadv.extractor.MP_Trade_Web_Extractor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.iadv.mp.MP_Trade_Selenium;

public class MP_Trade_Main {

	public static void main(String args[])
	{






		try
		{
			Map<String, ArrayList<String>> data = new TreeMap<String, ArrayList<String>>();
			HashMap<Integer, ArrayList<String>> mpMap= XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
			System.out.println(mpMap);
			// Initialize Chrome WebDriver
			WebDriver driver = new ChromeDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			MP_Trade_Selenium.extractTradeValues(args[1], args[2], args[3],driver,wait);
			for(int i=1;i<3;i++)
			{
				double licenseno = Double.valueOf((mpMap.get(i).get(0)));
				long licensenox = (long) licenseno;
				System.out.println("****************************************************");

				boolean check = false;
				for(long j=licensenox-100;j<licensenox+100;j++)
				{
					 MP_Trade_Selenium.getSearchValues(driver,wait,String.valueOf(j),args[3],args[2],data);


				}

				System.out.println("****************************************************");
			}
			data.put("Registration Number",new ArrayList<>(Arrays.asList("lic no.","Trader Name" , "Firm Name" , "Mobile", "House No./Name","Street/Locality" , "City" , "Ward","Zone")));

			wait.until(d -> MP_Trade_Web_Extractor.extractMPTradeValues(driver,data));
			driver.quit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
