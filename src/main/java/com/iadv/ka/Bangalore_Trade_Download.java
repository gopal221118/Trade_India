package com.iadv.ka;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Bangalore_Trade_Download {

	public static void downloadreciept(WebDriver driver, WebDriverWait wait, WebDriverWait alertWait, String appno) {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder1_txtApplicationNumber")));
			driver.findElement(By.id("ContentPlaceHolder1_txtApplicationNumber")).clear();
			driver.findElement(By.id("ContentPlaceHolder1_txtApplicationNumber")).sendKeys(appno);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder1_btnDownload")));
			driver.findElement(By.id("ContentPlaceHolder1_btnDownload")).click();
			
			try {
	            if (alertWait.until(ExpectedConditions.alertIsPresent()) != null) {
	                Alert alert = driver.switchTo().alert();
	                alert.accept();
	                System.out.println("Alert was present and accepted.");
	            }
	        } catch (Exception e) {
	            System.out.println("No alert present, downloading the license");
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
