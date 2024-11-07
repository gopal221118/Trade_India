package com.iadv.ts;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hyd_PT_No_extractor_last {
    public static void main(String[] args) {
    	try {
			String csvFile = args[0];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "SL_no", "PTIN_no", "Owner_Name", "DOOR_NUMBER", "LOCALITY", "CIRCLE", "EXISTING_Number" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
        // Define the folder path
        String folderPath = "E:\\Telengana_TL\\Property_Tax\\last6";

        // Create a folder object
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        // Check if the folder is valid and not empty
        if (listOfFiles != null && listOfFiles.length > 0) {
            // Iterate through each file in the folder
            for (File file : listOfFiles) {
                // Process only HTML files
                if (file.isFile() && file.getName().endsWith(".html")) {
                   // System.out.println("Processing file: " + file.getName());
                    List<List<String>> tableData = processHtmlFile(file,writer);
                    
                    // Print the data stored in the list
                    if (tableData != null) {
                        for (List<String> row : tableData) {
                            System.out.println(row);  // You can print it or use it further
                        }
                    }
                }
            }
        } else {
            System.out.println("The folder is empty or doesn't exist.");
        }
        writer.close();
   	}
    	catch (Exception e) {
        	e.printStackTrace();
        }
    }

    // Method to process each HTML file and extract table data
    private static List<List<String>> processHtmlFile(File inputFile,CSVWriter writer) {
        List<List<String>> tableData = new ArrayList<>();  // To store rows of data
        try {
            // Parse the HTML file using Jsoup
            Document doc = Jsoup.parse(inputFile, "UTF-8");

            // Select the table with the given ID
            Element table = doc.getElementById("ContentPlaceHolder1_grd_SearchResult");
            if (table != null) {
                // Select all rows from the table body
                Elements rows = table.select("tbody > tr");

                // Loop through rows, starting from index 1 to skip the header row
                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements cells = row.select("td");

                    // Create a list to store the data of the current row
                    List<String> rowData = new ArrayList<>();

                    // Loop through cells and add the text to the rowData list
                    for (Element cell : cells) {
                        rowData.add(cell.text());
                    }
                    System.out.println(rowData);
                    String[] temparray = rowData.toArray(new String[0]);
                    writer.writeNext(temparray);

                    // Add the row data to the main tableData list
                   
                }
            } else {
                System.out.println("Table not found in file: " + inputFile.getName());
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + inputFile.getName());
            e.printStackTrace();
        }

        return tableData;  // Return the extracted data
    }
}
 
