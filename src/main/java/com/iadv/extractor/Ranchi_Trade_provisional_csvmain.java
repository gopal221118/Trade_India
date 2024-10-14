package com.iadv.extractor;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Ranchi_Trade_provisional_csvmain {

    public static void main(String[] args) {
        // File path of the CSV
        String csvFilePath = "C:/Users/rajap/Downloads/JH_Trade_X2.csv";
        
        // HashMap to store the CSV data where Application_No is the key
        Map<String, Map<String, String>> csvDataMap = new HashMap<>();
        
        // Counter to count the number of valid Application_No entries
        int applicationCount = 0;

        // OpenCSV reader to read the CSV file
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
                // Use the Application_No column as the key
                String applicationNo = row[applicationNoIndex];
                
                // Check if Application_No is not empty
                if (applicationNo != null && !applicationNo.trim().isEmpty()) {
                    // Increment the counter for each valid Application_No
                    applicationCount++;

                    // Store the row data in a map with the header as the key
                    Map<String, String> rowDataMap = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        rowDataMap.put(headers[i], row[i]);
                    }

                    // Add this row to the main HashMap
                    csvDataMap.put(applicationNo, rowDataMap);
                }
            }

            // Print the data in the HashMap
            for (Map.Entry<String, Map<String, String>> entry : csvDataMap.entrySet()) {
                System.out.println("Application_No: " + entry.getKey());
                for (Map.Entry<String, String> rowEntry : entry.getValue().entrySet()) {
                    System.out.println(rowEntry.getKey() + ": " + rowEntry.getValue());
                }
                System.out.println("---------------------------------------------------");
            }

            // Print the total number of valid Application_No entries
            System.out.println("Total number of Application Numbers: " + applicationCount);
            
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
