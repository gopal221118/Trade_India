package com.iadv.data;

import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;

public class Writer_TN_property_1 {

    // Function to write ArrayList<String> to a CSV file
    public static void writeToCSV(ArrayList<String> assessmentList, String fileName) {
        // Try-with-resources to automatically close FileWriter
        try (FileWriter writer = new FileWriter(fileName)) {

            // Writing the header (Assesment_no)
            writer.append("Assesment_no").append("\r\n");

            // Writing each assessment number from the ArrayList
            for (String assessmentNo : assessmentList) {
                writer.append(assessmentNo).append("\r\n");
            }

            System.out.println("Data successfully written to " + fileName);

        } catch (IOException e) {
            // Catch any I/O exception and print the error message
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
    }