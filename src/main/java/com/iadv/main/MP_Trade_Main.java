package com.iadv.main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

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
			HashMap<Integer, ArrayList<String>> mpMap= XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
			System.out.println(mpMap);
			// Initialize Chrome WebDriver
			WebDriver driver = new ChromeDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
			MP_Trade_Selenium.extractTradeValues(args[1], args[2], args[3],driver,wait);
			for(int i=1;i<mpMap.size();i++)
			{
				double licenseno = Double.valueOf((mpMap.get(i).get(0)));
				long licensenox = (long) licenseno;
				System.out.println("****************************************************");
				System.out.println(licensenox);
				for(long j=licensenox-100;j<licensenox+100;j++)
				{
					MP_Trade_Selenium.getSearchValues(driver,wait,String.valueOf(j),args[3]);
					System.out.println(j);
				}
				System.out.println("****************************************************");
			}
			driver.quit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
