package com.iadv.mp;

import java.time.Duration;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iadv.extractor.MP_Trade_Web_Extractor;

public class MP_Trade_Selenium {

	public static void extractTradeValues(String driverpath, String url, String city, WebDriver driver,
										  WebDriverWait wait) {

		// Set ChromeDriver path
		System.setProperty("webdriver.chrome.driver", driverpath);

		driver.get(url);
		driver.manage().window().maximize();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD38-btn")));
			WebElement dropdown = driver.findElement(By.id("WD38"));
			dropdown.click();

			// Wait for the dropdown options to appear.
			driver.findElement(By.id("WD38")).sendKeys("j");
			Thread.sleep(1000);
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),\"" + city + "\")]")));

			// Scroll the dropdown options until the desired option is visible
			WebElement option = driver.findElement(By.xpath("//td[contains(text(),\"" + city + "\")]"));
			scrollIntoView(driver, option);

			// Click on the option with visible text "INDORE NAGAR NIGAM"
			option.click();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static int temp =1;

	public static void getSearchValues(WebDriver driver, WebDriverWait wait, String licno, String city, String url,Map<String,ArrayList<String>> data) {
		String conxsecval = "";
		try {
			// Send keys to input box
			WebElement inputBox = driver.findElement(By.xpath("(//span[contains(@class,\"urEdf2Whl\")]/input)[1]"));
			inputBox.sendKeys(licno);
			// Click on the search button
			WebElement searchButton = driver.findElement(By.xpath("//span[contains(text(),'Search')]"));
			searchButton.click();

//			int errorsize = driver.findElements(By.xpath("//span[contains(text(),\"freezed\")]")).size();
//			if (errorsize >= 1) {
//				driver.navigate().refresh();
//				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD38-btn")));
//				WebElement dropdown = driver.findElement(By.id("WD38"));
//				dropdown.click();
//				// Wait for the dropdown options to appear.
//				driver.findElement(By.id("WD38")).sendKeys("j");
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				wait.until(
//						ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),\"" + city + "\")]")));
//
//				// Scroll the dropdown options until the desired option is visible
//				WebElement option = driver.findElement(By.xpath("//td[contains(text(),\"" + city + "\")]"));
//				scrollIntoView(driver, option);
//
//				// Click on the option with visible text "INDORE NAGAR NIGAM"
//				option.click();
//			}


			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD0194-r")));






			try{
				Thread.sleep(2000);
			}catch (Exception e)
			{

			}

			List<WebElement> tablevalue=  driver.findElements(By.xpath("(//table[contains(@class,\"urMatrixLayout\")])[16]/tbody/tr/td"));

			if(tablevalue.size()==18)
			{
				System.out.println(tablevalue.size());

//				if (data.isEmpty()){
//					data.put("Registration Number",new ArrayList<>(Arrays.asList("lic no.","Trader Name" , "Firm Name" , "Mobile", "House No./Name","Street/Locality" , "City" , "Ward","Zone")));
//				}


				ArrayList<String> list = new ArrayList<>();
				for(int i=0;i<tablevalue.size();i++)
				{
					if(i%2!=0)
					{
						list.add(tablevalue.get(i).getText().toString());
					}
				}

				data.put(String.valueOf(temp),list);
				temp++;
			}




			driver.findElement(By.id("WD38")).clear();


			Thread.sleep(1000);


		} catch (Exception e) {
//			int internalerrorsize = driver.findElements(By.xpath("//span[contains(text(),\"500\")]")).size();
//			if (internalerrorsize >= 1) {
				driver.navigate().refresh();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD38-btn")));
				WebElement dropdown = driver.findElement(By.id("WD38"));
				dropdown.click();
				// Wait for the dropdown options to appear.
				driver.findElement(By.id("WD38")).sendKeys("j");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),\"" + city + "\")]")));

				// Scroll the dropdown options until the desired option is visible
				WebElement option = driver.findElement(By.xpath("//td[contains(text(),\"" + city + "\")]"));
				scrollIntoView(driver, option);

				// Click on the option with visible text "INDORE NAGAR NIGAM"
				option.click();
//			}

				e.printStackTrace();
			}
			//return conxsecval;
		}

		// Method to scroll an element into view
		public static void scrollIntoView (WebDriver driver, WebElement element){
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		}


	//}


}
