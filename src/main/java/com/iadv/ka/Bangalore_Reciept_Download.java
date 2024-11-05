package com.iadv.ka;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileReader;
import java.io.IOException;

public class Bangalore_Reciept_Download {

    public static void main(String[] args) {
           // CSV file path
        String csvFilePath = "C:\\Users\\abhis\\OneDrive\\Desktop\\csv\\Feb2016.csv";

        try {
            // Initialize CSVParser to read the CSV file
            CSVParser parser = new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withHeader());

            // Initialize the counter
            int count = 1;

            // Loop through each record in the CSV
            for (CSVRecord record : parser) {
                try {
                    // Read the Application Number from the CSV (assuming it's in a column named "Application Number")
                    String applicationNumber = record.get("ApplicationNumber");

                    // Open the URL
                    String url = "https://trade.bbmpgov.in/Forms/FrmReDownloadAcknowledgementReceipt.aspx";
                    driver.get(url);

                    // Locate the dropdown element using XPath
                    WebElement dropdown = driver.findElement(By.xpath("//*[@id='ContentPlaceHolder1_ddlReprintType']"));

                    // Create an instance of the Select class to interact with the dropdown
                    Select select = new Select(dropdown);

                    // Select the option "Receipt" by visible text
                    select.selectByVisibleText("Receipt");

                    // Locate the text field for the application number
                    WebElement applicationNumberField = driver.findElement(By.xpath("//*[@id='ContentPlaceHolder1_txtApplicationNumber']"));

                    // Clear any pre-existing text in the field (just in case)
                    applicationNumberField.clear();

                    // Enter the application number
                    applicationNumberField.sendKeys(applicationNumber);

                    // Locate the "Download" button and click it
                    WebElement downloadButton = driver.findElement(By.xpath("//*[@id='ContentPlaceHolder1_btnDownload']"));
                    downloadButton.click();

                    // Print the download progress to the console
                    System.out.println(count + " PDF Downloaded: " + applicationNumber);
                    System.out.println("********************");
                    count++;

                    // Wait for the download to complete before proceeding (adjust the sleep time as needed)
                    Thread.sleep(10); // Example delay; adjust as necessary

                } catch (Exception e) {
                    // Log the exception and continue with the next application number
                    System.err.println("Error processing application number: " + record.get("Application Number"));
                    e.printStackTrace();
                }
            }

            // Close the CSVParser
            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
