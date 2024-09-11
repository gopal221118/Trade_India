package com.iadv.main;

import java.util.HashMap;

import com.iadv.api.Hyd_Trade_API;
import com.iadv.data.HYD_writer;
import com.iadv.extractor.HYD_Extractor;

public class hyderabad_main {
    public static void main(String[] args) {
        try {
          
            // CSV file path from command line arguments

            // Take input from the user
            for (char letter = 'a'; letter <= 'z'; letter++) {
            	 String filePath ="E:\\Telengana_TL\\Hyderabad\\30-Begumpet\\Begumpet-"+letter+".csv";
           
            

            // Create instances of Apiclass, Extractor, and Writer1
            	 Hyd_Trade_API api = new Hyd_Trade_API();
            	 HYD_writer writer = new HYD_writer();

            // Get the response for the input letter
            String response = api.sendRequest(String.valueOf(letter));

            // Extract data and store it in the HashMap
            HashMap<String, String[]> dataMap = HYD_Extractor.extractData(response);

            // Print all the data in the HashMap
            System.out.println("Extracted Data:");
            for (String slNo : dataMap.keySet()) {
                String[] row = dataMap.get(slNo);
                System.out.println("Sl No: " + slNo);
                System.out.println("TIN NO: " + row[0]);
                System.out.println("Owner Name: " + row[1]);
                System.out.println("Owner Address: " + row[2]);
                System.out.println("Trade Name: " + row[3]);
                System.out.println("Trade Address: " + row[4]);
                System.out.println("Business Type: " + row[5]);
                System.out.println("Date of Commencement: " + row[6]);
                System.out.println("Fee to Pay: " + row[7]);
                System.out.println("------------------------------------");
            }

            // Write the data to the CSV file
            writer.writeDataToCSV(dataMap, filePath);
 
            System.out.println("Data has been successfully written to the CSV file: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
