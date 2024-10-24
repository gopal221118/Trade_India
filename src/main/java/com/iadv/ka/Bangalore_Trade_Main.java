package com.iadv.ka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iadv.data.XLSXtoHashMap_Row_Col;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Bangalore_Trade_Main {

	public static void main(String args[]) {
		HashMap<Integer, ArrayList<String>> blrappno = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", args[2]);
		prefs.put("plugins.always_open_pdf_externally", true);
		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get(args[1]);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder1_txtApplicationNumber")));
		WebElement dropdownElement = driver.findElement(By.id("ContentPlaceHolder1_ddlReprintType"));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText("Receipt");
		for (int i = 1; i < blrappno.size(); i++) {
			Bangalore_Trade_Download.downloadreciept(driver, wait,alertWait, (blrappno.get(i).get(0)).trim());
			System.out.println(blrappno.get(i).get(0));

		}
	}

}
