package com.iadv.tn;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iadv.data.ReadfromTxt;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ROT_Step1 {

	public static void main(String[] args) 
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", args[2]);
		prefs.put("plugins.always_open_pdf_externally", true);
		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try
		{
			List<String> mncodes = ReadfromTxt.readFileAsList(args[0]);
			driver.get(args[1]);
			driver.manage().window().maximize();
			for (String mncode : mncodes) {
				System.out.println(mncode);
				String mnc[] = mncode.split(":");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drporg")));
				WebElement dropdown = driver.findElement(By.id("PageContent_drporg"));
				Select select = new Select(dropdown);
				select.selectByValue(mnc[0].trim());
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drpward")));
				WebElement dropdownx = driver.findElement(By.id("PageContent_drpward"));
				Select selectx = new Select(dropdownx);
				List<WebElement> optionsx = selectx.getOptions();
				List<String> optionValues = new ArrayList<String>();
				for (WebElement option : optionsx) {
					optionValues.add(option.getAttribute("value"));
				}
				ROT_Step1_Execution.getAssesmentNumber(mnc[0].trim(), optionValues,driver,wait,js,args[2]);
			}
		} catch (Exception e) {
			try
			{
			Thread.sleep(10000);
			int fs= driver.findElements(By.xpath("//h4[contains(text(),'Sorry! Your Request could not be processed. Please Try again Later')]")).size();
			if(fs > 0) {
				driver.navigate().back();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drporg")));
			}
			}
			catch (Exception e1) {
				int fs= driver.findElements(By.xpath("//h4[contains(text(),'Sorry! Your Request could not be processed. Please Try again Later')]")).size();
				if(fs > 0) {
					driver.navigate().back();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drporg")));
				}
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
