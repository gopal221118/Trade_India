package com.iadv.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Writer_TN_property_2 {
    public static void writeToCSV(HashMap<String, HashMap<String, String>> hashmapwithcontactandassesment, String filepath) {
        // Define the headers for the CSV
        String[] headers = {"Assesment_no", "LandmarkTamil", "Area", "mobilenumber", "Ward", "Zone", "DoorNo", "locality", "Street", "Pincode"};

        try (FileWriter csvWriter = new FileWriter(filepath)) {
            // Write the headers
            csvWriter.append(String.join(",", headers));
            csvWriter.append("\n");

            // Loop through the outer HashMap
            for (String assesmentNo : hashmapwithcontactandassesment.keySet()) {
                HashMap<String, String> contactDetails = hashmapwithcontactandassesment.get(assesmentNo);

                // Start with the assessment number (the key of the outer HashMap)
                csvWriter.append(assesmentNo);

                // Write values from the contactDetails (the inner HashMap) for each header
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("LandmarkTamil", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("Area", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("mobilenumber", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("Ward", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("Zone", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("DoorNo", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("locality", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("Street", ""));
                csvWriter.append(",");
                csvWriter.append(contactDetails.getOrDefault("Pincode", ""));

                // New line for each record
                csvWriter.append("\n");
            }

            System.out.println("Data successfully written to " + filepath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
