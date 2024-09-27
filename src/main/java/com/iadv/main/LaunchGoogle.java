package com.iadv.main;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LaunchGoogle {
    public static void main(String[] args) {
        // Use WebDriverManager to set up ChromeDriver automatically
        WebDriverManager.chromedriver().setup();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Launch Google
        driver.get("https://www.google.com");

        // Print the title of the page
        System.out.println("Page Title: " + driver.getTitle());

        // Optionally close the browser
        driver.quit();
    }
}
