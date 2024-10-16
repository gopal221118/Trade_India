package com.iadv.main;

import java.util.ArrayList;
import java.util.HashMap;
import com.iadv.api.Api_response_step2_property_tn;
import com.iadv.data.TN_property_reader2;
import com.iadv.extractor.DataExctractor_Propertytn_step2;

public class TN1_property_tax_with_contact_main_step2 {
    public static void main(String[] args) {
        // CSV file path passed as an argument
        String csvfile = args[0];
        String txt = args[1];
        HashMap<String,HashMap<String,String>> hashmapwithcontsctandassesment = new HashMap<>();
        // Reading the assessment numbers from the CSV file
        ArrayList<String> assesment_nos = TN_property_reader2.tn_Property_Reader_2(csvfile);
        for(String assesment_no:assesment_nos)
        {
        // Get the HTML response for the first assessment number
        String response = Api_response_step2_property_tn.api_Response_From_Assesmentno(assesment_no, txt);

        // Extract data from the HTML response and store it in a HashMap
        HashMap<String, String> hashmapwithcontact1 = DataExctractor_Propertytn_step2.data_Exctractor_propertytn_step2(response);

        // Print the extracted HashMap
        System.out.println(hashmapwithcontact1);
   
        hashmapwithcontsctandassesment.put(assesment_no, hashmapwithcontact1);
        }
       for(String assesmentno:hashmapwithcontsctandassesment.keySet())
       {
    	   System.out.println(assesmentno+":"+hashmapwithcontsctandassesment.get(assesmentno));
       }
        
    }
}
