package com.iadv.mp;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVWriter;

public class MP_Trade_Selenium {

	public static void extractTradeValues(String driverpath, String url, String city, WebDriver driver,
			WebDriverWait wait) {

		System.setProperty("webdriver.chrome.driver", driverpath);

		driver.get(url);
		driver.manage().window().maximize();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD38-btn")));
			WebElement dropdown = driver.findElement(By.id("WD38"));
			dropdown.click();
			driver.findElement(By.id("WD38")).sendKeys("j");
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),\"" + city + "\")]")));
			WebElement option = driver.findElement(By.xpath("//td[contains(text(),\"" + city + "\")]"));
			scrollIntoView(driver, option);
			option.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static int temp = 1;

	public static void getSearchValues(WebDriver driver, WebDriverWait wait, String licno, String city, String url,
			CSVWriter writer,String folderpath) {
		try {
			WebElement inputBox = driver.findElement(By.xpath("(//span[contains(@class,\"urEdf2Whl\")]/input)[1]"));
			inputBox.sendKeys(licno);
			WebElement searchButton = driver.findElement(By.xpath("//span[contains(text(),'Search')]"));
			searchButton.click();

			try {
				Thread.sleep(2000);
			} catch (Exception e) {

			}

			List<WebElement> tablevalue = driver
					.findElements(By.xpath("(//table[contains(@class,\"urMatrixLayout\")])[16]/tbody/tr/td"));

			if (tablevalue.size() == 18) {
				System.out.println(tablevalue.size());
				ArrayList<String> list = new ArrayList<String>();
				File file=new File(folderpath+File.separator+licno+".txt");
	            FileWriter fw=new FileWriter(file);
				for (int i = 0; i < tablevalue.size(); i++) {
					if (i % 2 != 0) {
						list.add(tablevalue.get(i).getText().toString());
						fw.write(tablevalue.get(i-1).getText().toString()+" :: "+tablevalue.get(i).getText().toString()+"\r\n");
					}
				}
				System.out.println("Running Now for child:"+ licno);
				String[] temparray = list.toArray(new String[0]);
                writer.writeNext(temparray);
                fw.close();
			}

			driver.findElement(By.id("WD38")).clear();
			Thread.sleep(1000);
		} catch (Exception e) {
			driver.navigate().refresh();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD38-btn")));
			WebElement dropdown = driver.findElement(By.id("WD38"));
			dropdown.click();
			driver.findElement(By.id("WD38")).sendKeys("j");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),\"" + city + "\")]")));

			WebElement option = driver.findElement(By.xpath("//td[contains(text(),\"" + city + "\")]"));
			scrollIntoView(driver, option);
			option.click();
			e.printStackTrace();
		}
	}

	public static void scrollIntoView(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
