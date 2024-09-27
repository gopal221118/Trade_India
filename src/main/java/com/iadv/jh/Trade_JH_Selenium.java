package com.iadv.jh;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Trade_JH_Selenium {

	public static String getWardHTML(WebDriver driver, WebDriverWait wait, String wardArray) {
		String html = "";
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ward_mstr_id")));
			WebElement dropdownElement = driver.findElement(By.id("ward_mstr_id"));
			Select dropdown = new Select(dropdownElement);
			dropdown.selectByValue(wardArray);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn_search")));
			driver.findElement(By.id("btn_search")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loadingDiv")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("demo_dt_basic")));
			html = html + driver.getPageSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

}
