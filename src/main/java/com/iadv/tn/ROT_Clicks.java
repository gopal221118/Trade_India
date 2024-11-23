package com.iadv.tn;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ROT_Clicks {

	public static void bothpreviousNNext(int tdsize, WebDriver driver, WebDriverWait wait, String fpath, String mcode,
			String opv,String pn, ArrayList<String> temparray) {
		try {
			for (int j = tdsize - 5; j <= tdsize - 2; j++) {
				String tempval = driver.findElement(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")")).getText();
				if(!(temparray.contains(tempval)))
				{
					temparray.add(tempval);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")")));
				driver.findElement(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")"))
						.click();
				Thread.sleep(3000);
				File filey = new File(fpath + File.separator + mcode + "_" + opv + "_" + j + "_"+pn+ ".html");
				FileWriter fwy = new FileWriter(filey);
				fwy.write(driver.getPageSource());
				fwy.close();
				System.out.println(
						j + ": HTML Downlaoded at :" + fpath + File.separator + mcode + "_" + opv + "_" + j + "_"+pn+ ".html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void onlyNext(int tdsize, WebDriver driver, WebDriverWait wait, String fpath, String mcode,
			String opv,String on, ArrayList<String> temparray) {
		try {
			for (int j = 1; j < tdsize - 2; j++) {
				String tempval = driver.findElement(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")")).getText();
				if(!(temparray.contains(tempval)))
				{
					temparray.add(tempval);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")")));
				driver.findElement(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")"))
						.click();
				Thread.sleep(3000);
				File filey = new File(fpath + File.separator + mcode + "_" + "_" + j + "_"+on+ ".html");
				FileWriter fwy = new FileWriter(filey);
				fwy.write(driver.getPageSource());
				fwy.close();
				System.out.println(
						j + ": HTML Downlaoded at :" + fpath + File.separator + mcode + "_" + "_" + j + "_"+on+ ".html");
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void onlyPrevious(int tdsize, WebDriver driver, WebDriverWait wait, String fpath, String mcode,
			String opv, String op, ArrayList<String> temparray) {
		try {
			for (int j = 3; j <= tdsize; j++) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")")));
				String tempval1 = driver.findElement(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")")).getText();
								
				if(!(temparray.contains(tempval1)))
				{
					temparray.add(tempval1);
				driver.findElement(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
								+ j + ")"))
						.click();
				Thread.sleep(3000);
				File filey = new File(fpath + File.separator
						+ mcode + "_" + opv + "_" + (j) + "_"+op+".html");
				FileWriter fwy = new FileWriter(filey);
				fwy.write(driver.getPageSource());
				fwy.close();
				System.out.println(j + ": HTML Downlaoded at :" + fpath + File.separator
						+ mcode + "_" + opv + "_" + (j) + "_"+op+".html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
