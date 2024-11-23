package com.iadv.ka;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class KA_HtmlParser {
	
	public static void main(String args[])
	{
	try
	{
		
	}
	catch (Exception e) {
	e.printStackTrace();	
	}
	}
	
	public static void extractNWrite(String filePath) {
		String[] headers = { "S.No", "Salutation", "First Name", "Middle Name", "Last Name", "Mobile No", "Email ID" };
		ArrayList<String> values = new ArrayList<>();

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

			System.out.println("Extracted Values:");
			for (String value : values) {
				System.out.println(value);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}