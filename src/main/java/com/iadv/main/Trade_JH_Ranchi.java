package com.iadv.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.iadv.extractor.JH_Ranchi_HTML_Extractor;
import com.iadv.jh.Trade_JH_Selenium;
import com.opencsv.CSVWriter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Trade_JH_Ranchi {

	public static void main(String[] args) {
		try {
			String csvFile = args[2];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] {"Ward_No","Application_No","Licence_No","Owner_Name","Firm_Name","Mobile_No","Link"
 });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			String[] wardArray = { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
					"16", "17", "18", "19", "77", "20", "21", "22", "155", "23", "24", "25", "26", "27", "28", "29",
					"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45",
					"46", "47", "48", "49", "74", "50", "67", "51", "52", "53", "54", "55", "79", "56", "57", "139",
					"58", "136", "59", "60", "61", "75", "62", "131", "140", "157", "159", "156", "100", "122", "70",
					"158", "72", "82", "83", "84", "85", "86", "87", "88", "89", "90", "124", "65", "109", "118", "119",
					"120", "121", "110", "111", "112", "113", "114", "115", "116", "117", "102", "68", "104", "105",
					"106", "107", "108", "103", "63", "80", "64", "81", "73", "98", "153", "99", "154", "127", "76",
					"97", "96", "66", "101", "152", "146", "147", "144", "145", "148", "149", "151", "150", "126", "69",
					"91", "94", "95", "133", "92", "93", "123", "71", "125", "129", "142", "138", "137", "141", "143",
					"130", "78", "132", "134", "135", "128" };
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", args[1]);
			prefs.put("plugins.always_open_pdf_externally", true);
			options.setExperimentalOption("prefs", prefs);
			WebDriver driver = new ChromeDriver(options);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			driver.manage().window().maximize();
			driver.get(args[0]);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loadingDiv")));
			for (int i = 0; i < wardArray.length; i++) {
				File file = new File(args[1] + File.separator + wardArray[i] + ".html");
				try {
					FileWriter fw = new FileWriter(file);
					String html = Trade_JH_Selenium.getWardHTML(driver, wait, wardArray[i]);
					JH_Ranchi_HTML_Extractor.extractNWritetoCSV(html,writer);
					System.out.println(html);
					fw.write(html);
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
