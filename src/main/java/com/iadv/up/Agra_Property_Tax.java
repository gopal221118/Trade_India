package com.iadv.up;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.opencsv.CSVWriter;

public class Agra_Property_Tax {

	public static void main(String args[]) {
		try
		{

			 String csvFile = args[1];
		        CSVWriter writer = null;

		        try {
		            writer = new CSVWriter(new FileWriter(csvFile));
		            writer.writeNext(new String[]{
		            		"Property_No",
		            	    "Assessment Date. (DD-MM-YYYY)",
		            	    "Calculation Date. (DD-MM-YYYY)",
		            	    "Form No.",
		            	    "Tax Ward Name",
		            	    "Corporator Ward No. & Name",
		            	    "Block Name",
		            	    "Mohalla Name",
		            	    "Ownership Type",
		            	    "Owner/Occupier Name",
		            	    "House No.",
		            	    "Property/House Address",
		            	    "Mobile No.",
		            	    "Total Area Of Property in Sq Feet",
		            	    "Area Rates",
		            	    "Road Width",
		            	    "Depreciation %",
		            	    "Appreciation %",
		            	    "Final Annual Rental Value(ARV)",
		            	    "Yearly Tax",
		            	    "Current Tax",
		            	    "Arrear",
		            	    "Interest",
		            	    "0% Discount On Current Tax",
		            	    "Total tax Due"
		            	});
		        } catch (IOException e) {
		            e.printStackTrace();
		            return; 
		        }
		HashMap<Integer, ArrayList<String>> propertyidlist = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
		String ranges[]=args[2].split("-");
		for (int i = Integer.parseInt(ranges[0].trim()); i < Integer.parseInt(ranges[1].trim()); i++) {
			try {
				String tempurl = "https://agrapropertytax.com/detailsByPropnonew.php?property_no="
						+ propertyidlist.get(i).get(0);
				System.out.println(i + "---->" + tempurl);
				Document document = Jsoup.connect(tempurl).get();
				HashMap<String, String> tempMap=getMapDetails(document);
				String temparray[] = {
						propertyidlist.get(i).get(0),
					    tempMap.get("Assessment Date. (DD-MM-YYYY)"),
					    tempMap.get("Calculation Date. (DD-MM-YYYY)"),
					    tempMap.get("Form No."),
					    tempMap.get("Tax Ward Name"),
					    tempMap.get("Corporator Ward No. & Name"),
					    tempMap.get("Block Name"),
					    tempMap.get("Mohalla Name"),
					    tempMap.get("Ownership Type"),
					    tempMap.get("Owner/Occupier Name"),
					    tempMap.get("House No."),
					    tempMap.get("Property/House Address"),
					    tempMap.get("Mobile No."),
					    tempMap.get("Total Area Of Property in Sq Feet"),
					    tempMap.get("Area Rates"),
					    tempMap.get("Road Width"),
					    tempMap.get("Depreciation %"),
					    tempMap.get("Appreciation %"),
					    tempMap.get("Final Annual Rental Value(ARV)"),
					    tempMap.get("Yearly Tax"),
					    tempMap.get("Current Tax"),
					    tempMap.get("Arrear"),
					    tempMap.get("Interest"),
					    tempMap.get("0% Discount On Current Tax"),
					    tempMap.get("Total tax Due")
					};
				writer.writeNext(temparray);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, String> getMapDetails(Document document) {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {
			// Select all rows in the table
			Elements rows = document.select("#table3 > tbody > tr");

			// Loop through each row and extract data based on the selectors
			for (Element row : rows) {
				Element keyElement = row.selectFirst("td:nth-child(2)");
				Element valueElement = row.selectFirst("td:nth-child(3)");

				if (keyElement != null && valueElement != null) {
					String key = keyElement.text().trim();
					String value = valueElement.text().trim();

					// Add the extracted data to the HashMap
					if (!key.isEmpty() && !value.isEmpty()) {
						dataMap.put(key, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}
}
