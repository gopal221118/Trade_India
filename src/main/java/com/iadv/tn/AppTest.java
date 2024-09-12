package com.iadv.tn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AppTest {

    public static void main(String[] args) {
        // Set the path to geckodriver
    	System.setProperty("webdriver.gecko.driver", "C:\\geckodriver_new\\geckodriver.exe");


        // Optional: Set the path to Firefox binary (if necessary)
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        // Initialize the WebDriver with FirefoxOptions
        WebDriver driver = new FirefoxDriver(options);

        // Create a WebDriverWait object with a 10-second timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Loop through all pages (from p=1 to p=398)
        for (int pageNum = 1; pageNum <= 398; pageNum++) {
            // Construct the URL with the current page number
            String url = "https://erp.chennaicorporation.gov.in/egtradelicense/citizen/tradeSearchNoLogin.action?d-5394226-p="
                + pageNum
                + "&oldLicenseNo=&method:searchLicense=Search&fromDate=&licenseNumber=&struts.token.name=struts.token"
                + "&establishmentName=d&struts.token=A28SQLGLGOCYOTW63F4TCLP9PC310Z35&toDate=&tempLicenseNumber=";

            // Open the URL
            driver.get(url);

            // Wait until the "View Certificate" links are present on the page
            List<WebElement> viewCertificateLinks = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("View Certificate"))
            );

            // Check if there are any links to click
            if (viewCertificateLinks.size() > 0) {
                // Click each "View Certificate" link to download the PDF
                for (int i = 0; i < viewCertificateLinks.size(); i++) {
                    try {
                        // Add a 5-second delay before clicking the next button
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Re-locate the link before clicking to avoid stale element reference
                    viewCertificateLinks = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("View Certificate"))
                    );
                    
                    // Click the link
                    viewCertificateLinks.get(i).click();

                    // After the download is triggered, navigate back to the main page
                    driver.navigate().back();

                    // Wait for the page to load and ensure the "View Certificate" links are present again
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("View Certificate")));
                }
            } else {
                System.out.println("No 'View Certificate' links found on page " + pageNum);
            }
        }

        // Optionally, close the browser after all actions
        driver.quit();
    }
}
