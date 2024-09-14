package com.iadv.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TGSPDCL_Mobile_data {
	 public void writeDataToCSV(HashMap<String, HashMap<String, String>> dataMap,String filepath) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
	            // Write the header
	            writer.write("Consumer Name,Unique Service Number,Service Number,Address,ERO,Mobile Number");
	            writer.newLine();

	            // Write data rows
	            for (Map.Entry<String, HashMap<String, String>> entry : dataMap.entrySet()) {
	                String uniqueServiceNumber = entry.getKey();
	                HashMap<String, String> details = entry.getValue();

	                String consumerName = escapeCSV(details.get("Consumer Name"));
	                String serviceNumber = escapeCSV(details.get("Service Number"));
	                String address = escapeCSV(details.get("Address"));
	                String ero = escapeCSV(details.get("ERO"));
	                String mobileNumber = escapeCSV(details.get("Mobile Number"));

	                // Write a row
	                writer.write(String.format("%s,%s,%s,%s,%s,%s",
	                        consumerName, uniqueServiceNumber, serviceNumber, address, ero, mobileNumber));
	                writer.newLine();
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private String escapeCSV(String value) {
	        if (value == null) {
	            return "";
	        }
	        // Escape quotes by doubling them and wrap the value in quotes
	        return "\"" + value.replace("\"", "\"\"") + "\"";
	    }

}
