package com.iadv.extractor;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ranchi_Trade_Provisional_Merge {

    public static void main(String[] args) {
        // Correct file path using forward slashes
        String csvFilePath = "C:/Users/rajap/Downloads/JH_Trade_X2.csv";  // Corrected path

        String folderPath = "D:/Jharkhand_Ranchi"; // Folder path containing the HTML files

        // HashMap to store the CSV data where Application_No is the key
        Map<String, Map<String, String>> csvDataMap = new HashMap<>();

        // Step 1: Read CSV File and populate csvDataMap
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] headers = reader.readNext(); // Read the header row

            if (headers == null) {
                System.out.println("The CSV file is empty!");
                return;
            }

            // Find the index of Application_No in the header row
            int applicationNoIndex = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase("Application_No")) {
                    applicationNoIndex = i;
                    break;
                }
            }

            if (applicationNoIndex == -1) {
                System.out.println("Application_No column not found in the CSV file.");
                return;
            }

            String[] row;
            while ((row = reader.readNext()) != null) {
                // Use the Application_No column as the key (remove "Val:" prefix and trim)
                String applicationNo = row[applicationNoIndex].replace("Val:", "").trim().toLowerCase();

                // Debug print to check CSV application numbers
                System.out.println("CSV Application No (trimmed): " + applicationNo);

                // Check if Application_No is not empty
                if (!applicationNo.isEmpty()) {
                    // Store the row data in a map with the header as the key
                    Map<String, String> rowDataMap = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        rowDataMap.put(headers[i], row[i]);
                    }

                    // Add this row to the main HashMap
                    csvDataMap.put(applicationNo, rowDataMap);
                }
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        // Step 2: Process HTML Files and populate htmlDataMap
        File folder = new File(folderPath);
        ArrayList<String> filePaths = FilePathCollector1.getFilePaths();  // Adjust as necessary

        // HashMap to store HTML data where Application_No is the key
        Map<String, Map<String, String>> htmlDataMap = new HashMap<>();

        for (String filePath : filePaths) {
            File input = new File(filePath);

            try {
                // Parse the HTML file using JSoup
                Document doc = Jsoup.parse(input, "UTF-8");

                // Extract the application number (remove "Val:" prefix and trim)
                String applicationNo = doc.selectFirst("td:contains(Application No) > span").text().replace("Val:", "").trim().toLowerCase();

                // Debug print to check HTML application numbers
                System.out.println("HTML Application No (trimmed): " + applicationNo);

                // Store extracted data in a map
                Map<String, String> extractedData = new HashMap<>();
                extractedData.put("Provisional License No", doc.selectFirst("td:contains(Provisional License No) > span").text());
                extractedData.put("Mr/Mrs", doc.selectFirst("td:contains(Mr/Mrs) > span").text());
                extractedData.put("Apply Date", doc.selectFirst("td:contains(Apply Date) > span").text());
                extractedData.put("Establishment Date", doc.selectFirst("td:contains(Establishment Date) > span").text());
                extractedData.put("Valid Upto", doc.selectFirst("td:contains(Valid Upto) > span").text());

                // Add this data to htmlDataMap
                htmlDataMap.put(applicationNo, extractedData);

            } catch (IOException e) {
                System.out.println("Failed to extract data from: " + filePath);
                e.printStackTrace();
            }
        }

        // Step 3: Merge the data
        Map<String, Map<String, String>> mergedDataMap = new HashMap<>();
        int matchedCount = 0;

        for (Map.Entry<String, Map<String, String>> entry : csvDataMap.entrySet()) {
            String csvApplicationNo = entry.getKey();  // Already trimmed and lowercased in Step 1
            Map<String, String> csvData = entry.getValue();

            // Check if this Application_No exists in htmlDataMap (both trimmed and lower-cased)
            if (htmlDataMap.containsKey(csvApplicationNo)) {
                matchedCount++;

                // Debug print to check matched Application_No
                System.out.println("Matched Application_No: " + csvApplicationNo);

                // Get HTML data and merge with CSV data
                Map<String, String> htmlData = htmlDataMap.get(csvApplicationNo);

                // Merge both CSV and HTML data
                Map<String, String> mergedData = new HashMap<>(csvData);  // Start with CSV data
                mergedData.putAll(htmlData);  // Merge HTML data

                // Add merged data to the final merged map
                mergedDataMap.put(csvApplicationNo, mergedData);
            } else {
                // Debug print for unmatched application numbers
                System.out.println("No match found for Application_No: " + csvApplicationNo);
            }
        }

        // Step 4: Print the merged data
        for (Map.Entry<String, Map<String, String>> entry : mergedDataMap.entrySet()) {
            System.out.println("Application_No: " + entry.getKey());
            for (Map.Entry<String, String> dataEntry : entry.getValue().entrySet()) {
                System.out.println(dataEntry.getKey() + ": " + dataEntry.getValue());
            }
            System.out.println("---------------------------------------------------");
        }

        // Print the count of matched application numbers
        System.out.println("Total matched application numbers: " + matchedCount);
    }
}
