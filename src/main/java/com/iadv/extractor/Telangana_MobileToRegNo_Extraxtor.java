package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Telangana_MobileToRegNo_Extraxtor {
    public static void extractHTMLResponse(String html, CSVWriter writer) throws IOException {
        // Sample HTML
    	try {
        Document doc = Jsoup.parse(html);

        // Select the table rows (ignoring the header row)
        Elements rows = doc.select("table tbody tr");

        // HashMap to store the result
        HashMap<Integer, ArrayList<String>> tableData = new HashMap<>();

        // Iterate through each row (skip the first one as it is the header)
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");

            // Create an ArrayList to store the column data for the current row
            ArrayList<String> rowData = new ArrayList<>();

            // Iterate through each column and extract the text
            for (Element col : cols) {
                // If the column has an <a> tag, extract the href or the text
                Element link = col.selectFirst("a");
                if (link != null) {
                    rowData.add(link.text()); // You can also get link.attr("href") if needed
                    rowData.add("https://tgsouthernpower.org/regappstatus?ltappstatusregno="+link.text());
                    rowData.add("https://tgsouthernpower.org/ops/complaint_acknowledge.jsp?regid="+link.text());
                } else {
                    rowData.add(col.text());
                }
            }

            // Add the row data to the HashMap, using the row number (i) as the key
            tableData.put(i, rowData);
        }

        // Print the HashMap
        for (Integer key : tableData.keySet()) {
            System.out.println("Row " + key + ": " + tableData.get(key));
            String[] temparray = tableData.get(key).toArray(new String[0]);
            writer.writeNext(temparray);
        }
    	}
    	catch (Exception e) {
        e.printStackTrace();		}
    }
}
