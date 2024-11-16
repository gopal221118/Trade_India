package com.iadv.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

public class TN_Trade_HTML_Extractor {
	
	public static HashMap<Integer, HashMap<String, String>> extractHTMLResponse(String response, CSVWriter writer)
	{
		HashMap<Integer, HashMap<String, String>> csvMap = new HashMap<Integer, HashMap<String, String>>();
		try
		{
	        Document document = Jsoup.parse(response); // Parse the HTML content
	        Element table = document.getElementById("results"); // Locate the table by ID

	        // Get the header row to use as keys for HashMap
	        Elements headerElements = table.select("tr").first().select("td, th"); // Accept both <td> and <th> for headers

	        // Store headers
	        List<String> headers = new ArrayList<>();
	        for (Element header : headerElements) {
	            headers.add(header.text());
	        }

	        // Iterate over the remaining rows to collect values
	        Elements rows = table.select("tr"); // Get all rows, including the header row

	        int rowCount = 0; // Initialize row count

	        // Start from index 1 to skip the header row
	        for (int i = 1; i < rows.size(); i++) {
	            Element row = rows.get(i); // Get each row (starting from the second row)
	            Elements cells = row.select("td"); // Select all cells in the row

	            // Only proceed if the row contains actual data (i.e., has the same number of columns as headers)
	            if (cells.size() == headers.size()) {
	            	// Create a new HashMap for each row to store the row data
	                HashMap<String, String> rowData = new HashMap<String, String>();

	                // Map each cell value to its corresponding header
	                for (int j = 0; j < cells.size(); j++) {
	                    rowData.put(headers.get(j), cells.get(j).text());
	                }

	                // Put the rowData HashMap into the main csvMap with the rowCount as the key
	                csvMap.put(++rowCount, rowData); // Increment row count and add the row data
	            }
	        }

	        // Optionally print each row's data
	        for (int rowKey : csvMap.keySet()) {
	            System.out.println("Row " + rowKey + ": " + csvMap.get(rowKey));
	            HashMap<String, String> rowDatax = (csvMap.get(rowKey));
	            String rowarray[] = {rowDatax.get("Sr. No."), rowDatax.get("TIN"), rowDatax.get("GSTIN/PID"), rowDatax.get("Firm Name"), rowDatax.get("Address"), 
      				  rowDatax.get("Assessment Circle"), rowDatax.get("Tax Type"), rowDatax.get("Return Form Type"), 
					  rowDatax.get("RC Effective Date"), rowDatax.get("Status"), rowDatax.get("Cancellation Date"), rowDatax.get("Type Of Dealer")};
	            writer.writeNext(rowarray);
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return csvMap; // Return the map containing all rows
	}
}