package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HTML_Extractor_Hyd {
	public static void extractnWrite(String html, CSVWriter writer) throws IOException {
		Document doc = Jsoup.parse(html);
		Elements rows = doc.select("#ContentPlaceHolder1_gvDetails > tbody > tr");
		HashMap<Integer, ArrayList<String>> tableData = new HashMap<>();
		for (int i = 2; i < rows.size()-1; i++) {
			Element row = rows.get(i); // Get the row
			Elements columns = row.select("td"); // Select all <td> within the row
			ArrayList<String> rowData = new ArrayList<>();
			for (Element column : columns) {
				rowData.add(column.text().trim()); // Add column text to ArrayList
			}
			tableData.put(i, rowData);
		}
		for (Integer key : tableData.keySet()) {
			System.out.println("Row " + key + ": " + tableData.get(key));
			String[] temparray = tableData.get(key).toArray(new String[0]);
			writer.writeNext(temparray);
		}
	}
}
