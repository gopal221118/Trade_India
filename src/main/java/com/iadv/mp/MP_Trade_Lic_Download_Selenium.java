package com.iadv.mp;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.iadv.mp.MP_Trade_Selenium.scrollIntoView;

public class MP_Trade_Lic_Download_Selenium {

	public static void getSearchValues(WebDriver driver, WebDriverWait wait, String licno, String url) {
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("WD1A")));
			System.out.println("Entered Page");
			WebElement inputBox = driver.findElement(By.id("WD1A"));
			inputBox.sendKeys(licno);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=\"Get Certificate\"]")));
			WebElement Download_certificate = driver.findElement(By.xpath("//span[text()=\"Get Certificate\"]"));
			Download_certificate.click();
			//List<WebElement> list503=driver.findElements(By.xpath("//span[contains(text(),'Service Not Available')]"));
//            if(list503.size()>=1)
//            {
//    			driver.navigate().refresh();
//	        }
//            else
//            {
    			driver.findElement(By.id("WD1A")).clear();
          //  }
		} catch (Exception e) {
			//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[contains(text(),'Service Not Available')]")));
			driver.navigate().refresh();
//			List<WebElement> list503=driver.findElements(By.xpath("//span[contains(text(),'Service Not Available')]"));
//            if(list503.size()>=1)
//            {
//    			driver.navigate().refresh();
//	        }
//			driver.navigate().refresh();
			e.printStackTrace();
		}
	}
}
