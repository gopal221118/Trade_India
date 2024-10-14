package com.iadv.main;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import com.iadv.api.TN_PropertyTax_API;
import com.iadv.data.ReadfromTxt;
import com.iadv.data.TN_property_reader1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TN1_property_tax_with_contact_main {
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
        HashMap<String, ArrayList<String>> munci_no_with_ward_no = new HashMap<>();

        // Check if the map is empty (i.e., if the file reading failed or is empty)
        if (municipalityMap.isEmpty()) {
            System.out.println("Error: No data found in the Excel file or unable to read the file.");
            return;
        }
        for(int i=0;i<municipalityMap.keySet().size();i++)
        {
        System.out.println(new ArrayList<String>(municipalityMap.keySet()).get(i));
        
        try {
            // Prepare the output file
            File file = new File("C:\\ajay\\qatar1" + new ArrayList<String>(municipalityMap.keySet()).get(i) + ".html");
            FileWriter fw = new FileWriter(file);
            
            // Read form parameters
            String vs = ReadfromTxt.readFileAsString(args[1]);
            String ev = ReadfromTxt.readFileAsString(args[2]);
            
            // Get API response
            String resp = TN_PropertyTax_API.getAPIResponse(vs, ev, new ArrayList<String>(municipalityMap.keySet()).get(i));
            // Write response to HTML file
            fw.write(resp);
            fw.close();
            
            // Now, let's parse the HTML response and extract the <option> values
            Document doc = Jsoup.parse(resp);
            
            // Select all <option> elements within #PageContent_drpward
            Elements options = doc.select("#PageContent_drpward > option");
            
            // List to store the extracted values
            ArrayList<String> wardValues = new ArrayList<>();
            
            // Iterate over each <option> element, skipping the first one
            for (int i1 = 1; i1 < options.size(); i1++) {
                Element option = options.get(i1);
                String value = option.attr("value");
                if (!value.isEmpty()) { // Ensure value is not empty
                    wardValues.add(value);
                    
                }
            }
            
            // Print extracted values to the console
            munci_no_with_ward_no.put(new ArrayList<String>(municipalityMap.keySet()).get(i), wardValues);
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
    }
}
