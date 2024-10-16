package com.iadv.main;

import java.util.ArrayList;
import java.util.HashMap;
import com.iadv.api.Api_response_step2_property_tn;
import com.iadv.data.TN_property_reader2;
import com.iadv.extractor.DataExctractor_Propertytn_step2;
import com.iadv.data.Writer_TN_property_2;

public class TN1_property_tax_with_contact_main_step2 {
    public static void main(String[] args) {
        // CSV file path passed as an argument
        String csvfile = args[0];
        String txt = args[1];
        String outputFilePath = args[2];  // File path for the CSV output

        HashMap<String, HashMap<String, String>> hashmapwithcontactandassesment = new HashMap<>();
        
        // Reading the assessment numbers from the CSV file
        ArrayList<String> assesment_nos = TN_property_reader2.tn_Property_Reader_2(csvfile);
        
        // Loop through each assessment number and extract the contact details
        for (String assesment_no : assesment_nos) {
            // Get the HTML response for each assessment number
            String response = Api_response_step2_property_tn.api_Response_From_Assesmentno(assesment_no, txt);

            // Extract data from the HTML response and store it in a HashMap
            HashMap<String, String> hashmapwithcontact1 = DataExctractor_Propertytn_step2.data_Exctractor_propertytn_step2(response);

            // Print the extracted HashMap for debugging
            System.out.println(hashmapwithcontact1);
       
            // Add the assessment number and its corresponding contact details to the main HashMap
            hashmapwithcontactandassesment.put(assesment_no, hashmapwithcontact1);
        }
        
        // Write the HashMap data to a CSV file
        Writer_TN_property_2.writeToCSV(hashmapwithcontactandassesment, outputFilePath);
    }
}
