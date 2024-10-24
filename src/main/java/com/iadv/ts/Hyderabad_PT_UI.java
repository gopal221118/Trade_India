package com.iadv.ts;

import com.iadv.data.ReadfromTxt;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hyderabad_PT_UI {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", args[1]);
		prefs.put("plugins.always_open_pdf_externally", true);
		options.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		try {
			driver.get(args[0]);
			driver.manage().window().maximize();

			List<String> ccs = ReadfromTxt.readFileAsList(args[2]);
			for (String cc : ccs) {
				WebElement dropdown = driver.findElement(By.id("ContentPlaceHolder1_ddlCircle"));
				Select select = new Select(dropdown);
				select.selectByValue(cc);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder1_txtOwnerName")));

				for (char i = 'a'; i <= 'z'; i++) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder1_txtOwnerName")));
					driver.findElement(By.id("ContentPlaceHolder1_txtOwnerName")).clear();
					driver.findElement(By.id("ContentPlaceHolder1_txtOwnerName")).sendKeys(String.valueOf(i));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder1_btnSearch")));
					driver.findElement(By.id("ContentPlaceHolder1_btnSearch")).click();

					// Waiting for table to appear
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//table[contains(@class,'table table-striped table-bordered table-hover')]")));
					String htmlx = driver
							.findElement(By.xpath(
									"//table[contains(@class,'table table-striped table-bordered table-hover')]"))
							.getAttribute("innerHTML");
					writetoText(htmlx, args[3], cc + "+" + i + "1.html");
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//a[text()='...']/parent::td/parent::tr/td/a")));
					int dotsize=driver
							.findElements(By.xpath("//a[text()='...']")).size();
					ArrayList<Integer> tempint=new ArrayList<Integer>();
					while(dotsize>=1)
					{
						Thread.sleep(500);
					List<WebElement> elementsx = driver
							.findElements(By.xpath("//a[text()='...']/parent::td/parent::tr/td/a"));
					repeatSteps(elementsx,js,driver,wait,args[3],cc,i);
					Thread.sleep(1000);
					int dsize=driver
							.findElements(By.xpath("//a[text()='...']")).size();
					System.out.println(dsize);
					js.executeScript("arguments[0].scrollIntoView(true);",
							driver.findElement(By.xpath("(//a[text()='...'])["+dsize+"]")));
					Thread.sleep(500); // Optional wait to ensure visibility

					js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//a[text()='...'])["+dsize+"]")));
					dotsize=dsize;
					tempint.add(dsize);
					int exitcount = Collections.frequency(tempint, 1);
					if(exitcount==2)
					{
						break;
					}
					}
					Thread.sleep(5000); // Optional wait to ensure visibility
					
					
			}

				System.out.println("****************************************************");
			}
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		} }

	private static void writetoText(String htmlx, String folderpath, String filename) {
		try {
			File file = new File(folderpath + File.separator + filename);
			FileWriter fw = new FileWriter(file);
			fw.write(htmlx);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void repeatSteps(List<WebElement> elementsx, JavascriptExecutor js, WebDriver driver, WebDriverWait wait, String fp, String cc, char i)

	{
		try {
			int countx = 1;
			for (int x = 2; x < elementsx.size(); x++) {
				countx = countx + 1;
				js.executeScript("arguments[0].scrollIntoView(true);",
						driver.findElement(By.xpath("(//a[text()='...']/parent::td/parent::tr/td/a)[" + x + "]")));
				Thread.sleep(500); // Optional wait to ensure visibility

				js.executeScript("arguments[0].click();",
						driver.findElement(By.xpath("(//a[text()='...']/parent::td/parent::tr/td/a)[" + x + "]")));

				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//table[contains(@class,'table table-striped table-bordered table-hover')]")));
				String html = driver
						.findElement(
								By.xpath("//table[contains(@class,'table table-striped table-bordered table-hover')]"))
						.getAttribute("innerHTML");
				writetoText(html, fp, cc + "_" + i + "_" + countx + ".html");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
