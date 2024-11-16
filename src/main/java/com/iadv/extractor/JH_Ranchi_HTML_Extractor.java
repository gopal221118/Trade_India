package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JH_Ranchi_HTML_Extractor {

	public static void extractNWritetoCSV(String html, CSVWriter writer) throws IOException {
		Document document = Jsoup.parse(html);
		Element table = document.select("#demo_dt_basic").first();
		Elements rows = table.select("tbody > tr");
		HashMap<Integer, List<String>> tableData = new HashMap<>();
		for (int i = 0; i < rows.size(); i++) {
			Element row = rows.get(i);
			Elements cells = row.select("td");
			List<String> cellValues = new ArrayList<>();
			for (Element cell : cells) {
				if (cell.text().equals("View")) {
					String href = cell.select("a").attr("href");
					cellValues.add(href);
				} else {
					cellValues.add("Val:"+cell.text());
				}
			}
			tableData.put(i + 1, cellValues);
		}
		for (int rowNum : tableData.keySet()) {
			System.out.println("Row " + rowNum + ": " + tableData.get(rowNum));
			String[] temparray = tableData.get(rowNum).toArray(new String[0]);
			writer.writeNext(temparray);
		}
	}
}
