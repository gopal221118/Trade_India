package com.iadv.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.iadv.api.TGSPDCL_mobile_api;
import com.iadv.data.TGSPDCL_Mobile_data;
import com.iadv.extractor.TGSPDCL_mobile_Extractor;

public class TGSPDCL_mobile_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
   // Get the range of unique service numbers
        
        int startRange = Integer.parseInt(args[1]);
        
        int endRange = Integer.parseInt(args[2]);
        String filepath = args[0];
        
        TGSPDCL_mobile_api api = new TGSPDCL_mobile_api();
        TGSPDCL_mobile_Extractor extractor = new TGSPDCL_mobile_Extractor();
        TGSPDCL_Mobile_data dataWriter = new TGSPDCL_Mobile_data();
        
        // ArrayList to store responses (optional)
        ArrayList<String> responses = new ArrayList<>();
        // HashMap to store extracted data
        HashMap<String, HashMap<String, String>> dataMap = new HashMap<>();
        
        for (int uniqueServiceNumber = startRange; uniqueServiceNumber <= endRange; uniqueServiceNumber++) {
            try {
                // Fetch the response for each unique service number
                String response = api.getResponse(String.valueOf(uniqueServiceNumber));
                responses.add(response);
                
                // Extract the data from the response
                HashMap<String, String> extractedData = extractor.extractData(response);
                
                // Store the extracted data in the HashMap
                dataMap.put(String.valueOf(uniqueServiceNumber), extractedData);
            } catch (Exception e) {
                System.out.println("Error fetching data for unique service number: " + uniqueServiceNumber);
                e.printStackTrace();
            }
        }
        
        // Write data to CSV file
        dataWriter.writeDataToCSV(dataMap,filepath);
        
        // Print the extracted data
        for (String uniqueServiceNumber : dataMap.keySet()) {
            System.out.println("Unique Service Number: " + uniqueServiceNumber);
            HashMap<String, String> details = dataMap.get(uniqueServiceNumber);
            System.out.println("Consumer Name: " + details.get("Consumer Name"));
            System.out.println("Service Number: " + details.get("Service Number"));
            System.out.println("Address: " + details.get("Address"));
            System.out.println("ERO: " + details.get("ERO"));
            System.out.println("Mobile Number: " + details.get("Mobile Number"));
            System.out.println();
        }
        
     
	}

}
