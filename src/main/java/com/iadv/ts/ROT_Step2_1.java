package com.iadv.ts;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iadv.data.FileFetcher;
import com.iadv.data.ReadfromTxt;
import com.opencsv.CSVWriter;

public class ROT_Step2_1 {

	public static void main(String args[]) {
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "TIN_Number", "Trade_Title", "Owner_Name", "Door_Number", "Owner_Name",
						"Action" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			List<String> filepaths = FileFetcher.getAllFilePaths(args[0]);
			for (String filepath : filepaths) {
				try {
					String filecontent = ReadfromTxt.readFileAsString(filepath);
					System.out.println(filecontent);
					Document doc = Jsoup.parse(filecontent);
					Elements rows = doc.select("#tblTLSearch > tbody > tr");
					System.out.println("Number of rows: " + rows.size());

					extractVals(rows.size(), filecontent, writer);

					System.out.println(filepath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void extractVals(int limit, String html, CSVWriter writer) {
		try {
			Document doc = Jsoup.parse(html);
			for (int j = 0; j < limit; j++) {
				Elements rows = doc.select("#tblTLSearch > tbody > tr:nth-child(" + (j) + ") > td");
				String[] extractedData = new String[rows.size()];
				for (int i = 0; i < rows.size(); i++) {
					Element cell = rows.get(i);
					extractedData[i] = cell.text(); // Store the text of each cell
				}
				writer.writeNext(extractedData);
				for (String data : extractedData) {
					System.out.println(data);
				}
				System.out.println("Wait");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
