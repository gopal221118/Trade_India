package com.iadv.main;

import com.iadv.mp.MP_Trade_Lic_Download_Selenium;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MP_Trade_Lic_Downlaod_Main {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", args[2]);
		prefs.put("plugins.always_open_pdf_externally", true);
		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			driver.get(args[0]);
			driver.manage().window().maximize();
			int count = 1;
			String ranges[]=args[1].split("-");
			for (long i = (Integer.parseInt(ranges[0].trim())); i < (Integer.parseInt(ranges[1].trim())); i++) {
				System.out.println("****************************************************");
				String currentdata = "8000" + i;
				System.out.println(count + "---------------->" + currentdata);
				MP_Trade_Lic_Download_Selenium.getSearchValues(driver, wait, currentdata, args[0]);
				count++;
				System.out.println("****************************************************");
			}

		} catch (Exception e) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<WebElement> fillsize=driver.findElements(By.xpath("//a[contains(text(),'Fill all required entry fields')]"));
			if(fillsize.size()>=1)
			{
				driver.navigate().refresh();
			}
			e.printStackTrace();
		}

	}

}
