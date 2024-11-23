package com.iadv.tn;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ROT_Step1_Execution {

	public static void getAssesmentNumber(String mcode, List<String> optionValues, WebDriver driver, WebDriverWait wait,
			JavascriptExecutor js, String fpath) {
		try {
			for (int i = 1; i < optionValues.size(); i++) {
				System.out.println("*************************************************");
				ArrayList<String> temparray = new ArrayList<String>();
				System.out.println(optionValues.get(i));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drporg")));
				WebElement dropdown1 = driver.findElement(By.id("PageContent_drporg"));
				Select select1 = new Select(dropdown1);
				select1.selectByValue(mcode);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drporg")));
				System.out.println(mcode + "----------" + optionValues.get(i).trim());
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drporg")));
				WebElement dropdownElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_drpward")));
				Select dropdown = new Select(dropdownElement);
				dropdown.selectByValue(optionValues.get(i).trim());
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_btnSearch")));
				driver.findElement(By.id("PageContent_btnSearch")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("altrowstable")));
				File file = new File(fpath + File.separator + mcode + "_" + optionValues.get(i).trim() + "_1.html");
				FileWriter fw = new FileWriter(file);
				fw.write(driver.getPageSource());
				fw.close();
				System.out.println(1 + ": HTML Downlaoded at :" + fpath + File.separator + mcode + "_"
						+ optionValues.get(i).trim() + "_" + 1 + ".html");

				WebElement targetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//tr[contains(@class,'GridPager table-striped table-hover')]")));
				js.executeScript("arguments[0].scrollIntoView(true);", targetElement);
				String pagecounts = driver.findElement(By.id("PageContent_lblview")).getText();
				List<WebElement> tdElements = driver.findElements(By.cssSelector(
						"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td"));
				String cssSelectorNext = "//td/input[contains(@alt,'Next')]";
				List<WebElement> elements = driver.findElements(By.xpath(cssSelectorNext));
				if (!elements.isEmpty()) {
					System.out.println("Element exists.");
					// String cssSelector = "#PageContent_dgvDetails > tbody >
					// tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td > a";
					while (true) {
						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
									"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td")));
							List<WebElement> tdElementsx = driver.findElements(By.cssSelector(
									"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td"));
							int nextx = (tdElementsx.size()) - 1;
							System.out.println("Loop Size:" + tdElementsx.size());
							if (elements.size() > 0) {
								for (int j = 1; j < tdElements.size() - 1; j++) {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
											"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
													+ j + ")")));
									String tempval = driver.findElement(By.cssSelector(
											"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
													+ j + ")"))
											.getText();
									if (!(temparray.contains(tempval))) {
										temparray.add(tempval);
										driver.findElement(By.cssSelector(
												"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
														+ j + ")"))
												.click();
										Thread.sleep(3000);
										File filey = new File(fpath + File.separator + mcode + "_"
												+ optionValues.get(i).trim() + "_" + j + ".html");
										FileWriter fwy = new FileWriter(filey);
										fwy.write(driver.getPageSource());
										fwy.close();
										System.out.println(j + ": HTML Downlaoded at :" + fpath + File.separator + mcode
												+ "_" + optionValues.get(i).trim() + "_" + j + ".html");
									}
								}
								
								temparray.add(String.valueOf(Integer.parseInt((temparray.get((temparray.size())-1)).trim())+1));	
								driver.findElement(By.xpath(
										"(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)[" +  String.valueOf((tdElementsx.size()) - 1) + "]"))
										.click();
								Thread.sleep(3000);
								File filex = new File(fpath + File.separator + mcode + "_" + optionValues.get(i).trim()
										+ "_" + String.valueOf((tdElementsx.size()) - 1) + ".html");
								FileWriter fwx = new FileWriter(filex);
								fwx.write(driver.getPageSource());
								fwx.close();
								System.out.println(fpath + File.separator + mcode + "_" + optionValues.get(i).trim()
										+ "_" + String.valueOf((tdElementsx.size()) - 1) + ".html");
								}
								int nxsize = driver.findElements(By.xpath("//td/input[contains(@alt,'Next')]")).size();
								int prsize = driver.findElements(By.xpath("//td/input[contains(@alt,'Previous')]"))
										.size();
								int tdsize = driver.findElements(By.cssSelector(
										"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td"))
										.size();

								if (prsize > 0 && nxsize == 0) {
									ROT_Clicks.onlyPrevious(tdsize, driver, wait, fpath, mcode,
											optionValues.get(i).trim(), "OPA", temparray);
									break;
								} else if (prsize > 0 && nxsize > 0) {
									ROT_Clicks.bothpreviousNNext(tdsize, driver, wait, fpath, mcode,
											optionValues.get(i).trim(), "BPNA", temparray);
									Thread.sleep(1000);
									String tempval2 = driver.findElement(
											By.xpath("(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)["
													+ String.valueOf(tdsize - 1) + "]"))
											.getText();
									if (!(temparray.contains(tempval2))) {
										driver.findElement(
												By.xpath("(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)["
														+ String.valueOf(tdsize-1) + "]"))
												.click();
										Thread.sleep(3000);
										int nxs = driver.findElements(By.xpath(cssSelectorNext)).size();
										if (nxs == 0) {
											int prsizex = driver
													.findElements(By.xpath("//td/input[contains(@alt,'Previous')]"))
													.size();
											if (prsizex > 0) {
												ROT_Clicks.onlyPrevious(tdsize, driver, wait, fpath, mcode,
														optionValues.get(i).trim(), "OPB", temparray);
												break;
											}
										}
									 else {
										ROT_Clicks.bothpreviousNNext(tdsize, driver, wait, fpath, mcode,
												optionValues.get(i).trim(), "BPNB", temparray);
										Thread.sleep(1000);
										driver.findElement(
												By.xpath("(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)["
														+ String.valueOf(tdsize - 1) + "]"))
												.click();
										Thread.sleep(3000);
										if((driver.findElements(By.xpath(cssSelectorNext)).size()) == 0)
										{
											int tdsizex = driver.findElements(By.cssSelector(
													"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td"))
													.size();
											ROT_Clicks.onlyPrevious(tdsizex, driver, wait, fpath, mcode,
													optionValues.get(i).trim(), "OPB", temparray);
											break;	
										}
										else
										{
										while((driver.findElements(By.xpath(cssSelectorNext)).size()) == 1)
										{
											int nxsizex = driver.findElements(By.xpath("//td/input[contains(@alt,'Next')]")).size();
											int prsizex = driver.findElements(By.xpath("//td/input[contains(@alt,'Previous')]"))
													.size();
											int tdsizex = driver.findElements(By.cssSelector(
													"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td"))
													.size();
											if(nxsizex > 0 && prsizex >0)
											{
												ROT_Clicks.bothpreviousNNext(tdsizex, driver, wait, fpath, mcode,
														optionValues.get(i).trim(), "BPNB", temparray);
												Thread.sleep(1000);
												driver.findElement(
														By.xpath("(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)["
																+ String.valueOf(tdsize - 1) + "]"))
														.click();
												Thread.sleep(3000);	
											}
											else if(nxsizex == 0 && prsizex > 0)
											{
												ROT_Clicks.onlyPrevious(tdsizex, driver, wait, fpath, mcode,
														optionValues.get(i).trim(), "OPB", temparray);
												break;
											}
											else if(nxsizex > 0 && prsizex == 0)
											{
												ROT_Clicks.onlyNext(tdsizex, driver, wait, fpath, mcode, optionValues.get(i).trim(),
														"ONA", temparray);
												Thread.sleep(1000);
												driver.findElement(
														By.xpath("(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)["
																+ String.valueOf(tdsizex-1) + "]"))
														.click();
												Thread.sleep(3000);	
											}
										}
										}

									}
								}

								else if (prsize == 0 && nxsize > 0) {
									ROT_Clicks.onlyNext(tdsize, driver, wait, fpath, mcode, optionValues.get(i).trim(),
											"ONA", temparray);
									Thread.sleep(1000);
									driver.findElement(
											By.xpath("(//td/input[contains(@alt,'Next')]/parent::td/parent::tr/td)["
													+ String.valueOf(tdsize-1) + "]"))
											.click();
									Thread.sleep(3000);
								}
							}
						}
						 catch (Exception e) {
							// If the element is no longer visible, exit the loop
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
							System.out.println("Element is no longer visible, stopping the loop.");
							elements.clear();
							break;
						}
					}
				} else {
					for (int j = 2; j <= tdElements.size(); j++) {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
								"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
										+ j + ")")));
						driver.findElement(By.cssSelector(
								"#PageContent_dgvDetails > tbody > tr.GridPager.table-striped.table-hover > td > table > tbody > tr > td:nth-child("
										+ j + ")"))
								.click();
						Thread.sleep(3000);
						File filex = new File(
								fpath + File.separator + mcode + "_" + optionValues.get(i).trim() + "_" + j + ".html");
						FileWriter fwx = new FileWriter(filex);
						fwx.write(driver.getPageSource());
						fwx.close();
						System.out.println(j + ": HTML Downlaoded at :" + fpath + File.separator + mcode + "_"
								+ optionValues.get(i).trim() + "_" + j + ".html");
					}
				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PageContent_btnCancel")));
				driver.findElement(By.id("PageContent_btnCancel")).click();
				Thread.sleep(3000);
				temparray.clear();
				System.out.println(temparray);
				System.out.println("*************************************************");
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
