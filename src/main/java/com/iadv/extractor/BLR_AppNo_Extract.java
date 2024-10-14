package com.iadv.extractor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

public class BLR_AppNo_Extract {

	public static void main(String[] args) {
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "Application_Number", "Applicant_Name", "Trade_Name", "Major_Trade",
						"Minor_Trade", "Sub_Trade", "Paid_amount" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			List<String> filePaths = new ArrayList<>();
			getAllFiles(new File(args[0]), filePaths);
			for (String path : filePaths) {
				System.out.println("*******************************");
				System.out.println(path);
				String html = readHtmlFile(path);
				System.out.println(html);
				getRowVals(html,writer);
				System.out.println("*******************************");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getAllFiles(File directory, List<String> filePaths) {
		File[] files = directory.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					filePaths.add(file.getAbsolutePath());
				} else if (file.isDirectory()) {
					getAllFiles(file, filePaths);
				}
			}
		}
	}

	public static String readHtmlFile(String filePath) {
		try {
			File input = new File(filePath);
			Document doc = Jsoup.parse(input, "UTF-8");
			return doc.html();
		} catch (IOException e) {
			System.err.println("Error reading the HTML file: " + e.getMessage());
		}

		return null;
	}

	public static void getRowVals(String html, CSVWriter writer) {
		try {
			Document doc = Jsoup.parse(html);
			HashMap<Integer, List<String>> tableData = new HashMap<>();
			Elements rows = doc.select("#ContentPlaceHolder1_gvApplicationInformation > tbody > tr");
			for (int i = 1; i < rows.size(); i++) { // Start from 1 to skip the header row
				Element row = rows.get(i);
				Elements columns = row.select("td"); // Get all columns in the current row

				List<String> rowData = new ArrayList<>();
				for (Element column : columns) {
					rowData.add(column.text()); // Extract and store cell text
				}
				tableData.put(i, rowData);
			}
			for (Integer key : tableData.keySet()) {
				System.out.println("Row " + key + ": " + tableData.get(key));
				String[] temparray = tableData.get(key).toArray(new String[0]);
				writer.writeNext(temparray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
