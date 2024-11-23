package com.iadv.tn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iadv.data.FileFetcher;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TN_HtmlParser {

	public static void main(String args[]) {
		try {
			try {
				String csvFile = args[1];
				CSVWriter writer = null;

				try {
					writer = new CSVWriter(new FileWriter(csvFile));
					writer.writeNext(new String[] { "Assesment_Number","S.No", "Salutation", "First Name", "Middle Name", "Last Name",
							"Mobile No", "Email ID", "Zone", "Area", "Locality", "Door No", "LandmarkTamil", "Email ID",
							"Ward", "Street", "Pincode", "Landmark", "Mobile Number", "Property Reason",
							"Approval Number", "Location", "Building Age", "Reference Date", "Is Plan Approve",
							"PlotArea", "ApprovedPlotArea", "Reference No" });
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}

				List<String> paths = FileFetcher.getAllFilePaths(args[0]);
				for (String path : paths) {
					extractNWrite(path, writer);
				}
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void extractNWrite(String filePath, CSVWriter writer) {
		String[] headers = { "S.No", "Salutation", "First Name", "Middle Name", "Last Name", "Mobile No", "Email ID" };
		ArrayList<String> values = new ArrayList<>();
        values.add(((filePath.substring(filePath.lastIndexOf("\\")+1,filePath.lastIndexOf("."))).trim()).replace('_', '/'));
		try {
			File input = new File(filePath);
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements ownerDetails = doc.select("#PageContent_grvEownerDetails > tbody > tr:nth-child(2) > td");
			Elements assessmentFieldset4 = doc
					.select("#PageContent_assessment > div > fieldset:nth-child(4) > div > div");
			Elements assessmentFieldset5 = doc
					.select("#PageContent_assessment > div > fieldset:nth-child(5) > div > div");
			if (ownerDetails.size() == headers.length) {
				for (int i = 0; i < ownerDetails.size(); i++) {
					String head = headers[i].trim();
					String text = ownerDetails.get(i).text().trim();
					if (text.isEmpty()) {
						values.add(head + " : " + "NULL");
					} else {
						values.add(head + " : " + text);
					}
				}

				for (Element element : assessmentFieldset4) {
					String text = element.text().trim();
					if (text.contains(":")) {
						if (text.substring(text.length() - 1, text.length()).equalsIgnoreCase(":")) {
							values.add(text.isEmpty() ? "NULL" : text + "NULL");
						} else {
							values.add(text.isEmpty() ? "NULL" : text);
						}
					}
				}

				for (Element element : assessmentFieldset5) {
					String text = element.text().trim();
					if (text.contains(":")) {
						if (text.substring(text.length() - 1, text.length()).equalsIgnoreCase(":")) {
							values.add(text.isEmpty() ? "NULL" : text + "NULL");
						} else {
							values.add(text.isEmpty() ? "NULL" : text);
						}
					}
				}
			}
			String[] temparray = values.toArray(new String[0]);
			writer.writeNext(temparray);
			System.out.println("Extracted Values:");
			for (String value : values) {
				System.out.println(value);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}