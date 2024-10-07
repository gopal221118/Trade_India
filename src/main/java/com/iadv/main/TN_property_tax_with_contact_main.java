package com.iadv.main;
import java.util.*;

import com.iadv.data.TN_property_reader1;
public class TN_property_tax_with_contact_main {
    public static void main(String[] args) {
        // Check if the file path is provided in the command-line arguments
        if (args.length == 0) {
            System.out.println("Error: No file path provided. Please provide the path to the Excel file as a command-line argument.");
            return;
        }

        // Get the file path from the first argument
        String filePath = args[0];
        // Create an instance of the reader class
        TN_property_reader1 reader = new TN_property_reader1();
        
        // Read the data from the Excel file and store it in a HashMap
        HashMap<String, String> municipalityMap = reader.readExcel(filePath);

        // Check if the map is empty (i.e., if the file reading failed or is empty)
        if (municipalityMap.isEmpty()) {
            System.out.println("Error: No data found in the Excel file or unable to read the file.");
            return;
        }

        // Print the contents of the HashMap
        for (String municipalityCode : municipalityMap.keySet()) {
            System.out.println("Municipality_Code: " + municipalityCode +
                    ", Municipality: " + municipalityMap.get(municipalityCode));
            
        }
        HashMap<String, ArrayList<String>> h2=new HashMap<>();
        
    }
}