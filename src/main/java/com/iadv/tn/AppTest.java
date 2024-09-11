package com.iadv.tn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

public class AppTest {

    public static void main(String[] args) {
        // Set the path to geckodriver
    	System.setProperty("webdriver.gecko.driver", "C:/Users/somas/Downloads/geckodriver-v0.35.0-win64/geckodriver.exe");

        // Optional: Set the path to Firefox binary (if necessary)
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

        // Initialize the WebDriver with FirefoxOptions
        WebDriver driver = new FirefoxDriver(options);

        // Loop through all pages (from p=1 to p=848)
        for (int pageNum = 1; pageNum <= 20; pageNum++) {
            // Construct the URL with the current page number
            String url = "https://erp.chennaicorporation.gov.in/egtradelicense/citizen/tradeSearchNoLogin.action?d-5394226-p=" 
                + pageNum 
                + "&oldLicenseNo=&method:searchLicense=Search&fromDate=&licenseNumber=&struts.token.name=struts.token"
                + "&establishmentName=a&struts.token=A28SQLGLGOCYOTW63F4TCLP9PC310Z35&toDate=&tempLicenseNumber=";

            // Open the URL
            driver.get(url);

            // Find all the "View Certificate" links
            List<WebElement> viewCertificateLinks = driver.findElements(By.linkText("View Certificate"));

            // Click each "View Certificate" link to download the PDF
            for (int i = 0; i < viewCertificateLinks.size(); i++) {
                try {
                    // Add a 5-second delay before clicking the next button
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Re-locate the link before clicking to avoid stale element reference
                viewCertificateLinks = driver.findElements(By.linkText("View Certificate"));
                viewCertificateLinks.get(i).click();

                // After the download is triggered, navigate back to the main page
                driver.navigate().back();
            }
        }

        // Optionally, close the browser after all actions
        driver.quit();
    }
}
