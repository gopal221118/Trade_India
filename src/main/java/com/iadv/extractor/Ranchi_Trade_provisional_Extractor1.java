package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ranchi_Trade_provisional_Extractor1 {

    public static void main(String[] args) {
    	
    	  String folderPath = "D:/Jharkhand_Ranchi"; // Folder path containing the HTML files
          File folder = new File(folderPath);
        //  collectFilePaths(folder);
    	//FilePathCollector1.collectFilePaths("D:/Jharkhand_Ranchi");
        // Get all file paths from FilePathCollector
          FilePathCollector1.collectFilePaths(folder);
          
        ArrayList<String> filePaths = FilePathCollector1.getFilePaths();
//        System.out.println(filePaths);

        // HashMap to store extracted data from all files
        Map<String, Map<String, String>> allExtractedData = new HashMap<>();

        // Loop through each file path and extract data
        for (String filePath : filePaths) {
            File input = new File(filePath);

            // Initialize the HashMap to store extracted data for each file
            Map<String, String> extractedData = new HashMap<>();

            try {
                // Parse the HTML file using JSoup
                Document doc = Jsoup.parse(input, "UTF-8");

                // Extracting specific data using the CSS selector
                extractedData.put("Application No", doc.selectFirst("td:contains(Application No) > span").text());
                extractedData.put("Provisional License No", doc.selectFirst("td:contains(Provisional License No) > span").text());
                extractedData.put("Mr/Mrs", doc.selectFirst("td:contains(Mr/Mrs) > span").text());
                extractedData.put("Apply Date", doc.selectFirst("td:contains(Apply Date) > span").text());
                extractedData.put("Establishment Date", doc.selectFirst("td:contains(Establishment Date) > span").text());
                extractedData.put("Valid Upto", doc.selectFirst("td:contains(Valid Upto) > span").text());

                // Store extracted data in the allExtractedData map
                allExtractedData.put(filePath, extractedData);

            } catch (IOException e) {
                System.out.println("Failed to extract data from: " + filePath);
                e.printStackTrace();
            }
        }

        // Print all extracted data from all files
        for (Map.Entry<String, Map<String, String>> entry : allExtractedData.entrySet()) {
            System.out.println("Data from file: " + entry.getKey());
            for (Map.Entry<String, String> dataEntry : entry.getValue().entrySet()) {
                System.out.println(dataEntry.getKey() + " : " + dataEntry.getValue());
            }
            System.out.println("---------------------------------------------------");
        }
    }
}
