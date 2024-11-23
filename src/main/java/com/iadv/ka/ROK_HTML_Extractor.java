package com.iadv.ka;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iadv.data.FileFetcher;
import com.iadv.data.ReadfromTxt;
import com.iadv.extractor.HTML_Attribute_Extractor;
import com.opencsv.CSVWriter;

public class ROK_HTML_Extractor {

	public static void extractNWrite(String fpath, String url, CSVWriter writer) {
		try {
			String html = ReadfromTxt.readFileAsString(fpath);
			try {
				Document doc = Jsoup.parse(html);
				Elements rows = doc.select("#txt > tbody > tr");
				int rowCount = rows.size();
				System.out.println("********************************************************");
				System.out.println("Number of rows: " + rowCount);
				ArrayList<String> tableData = new ArrayList<String>();

				for (int i = 1; i <= rowCount; i++) {
					System.out.println("Processing Row: " + i);
					Elements columns = doc.select("#txt > tbody > tr:nth-child(" + i + ") > td");
					String[] rowData = new String[columns.size()];

					for (int j = 0; j < columns.size(); j++) {
						Element column = columns.get(j);
						if ((columns.get(j).text()).contains("View Fee")
								|| (columns.get(j).text()).contains("View Documents")
								|| (columns.get(j).text()).contains("View File Note")) {
							String tempval = extractUrl(HTML_Attribute_Extractor.getOnclickValue(column.outerHtml()));
							rowData[j] = url + tempval;
							tableData.add(rowData[j]);
						} else if ((columns.get(j).text()).contains("View License")) {
							rowData[j] = url + HTML_Attribute_Extractor.getHrefValue(column.outerHtml());
							tableData.add(rowData[j]);
						} else {
							rowData[j] = columns.get(j).text();
							tableData.add(rowData[j]);
						}

						System.out.println("Extracted Value: " + rowData[j]);
					}
					System.out.println(tableData);
					String[] tablearray = tableData.toArray(new String[0]);
					writer.writeNext(tablearray);
					System.out.println(tableData.size());
					tableData.clear();
					System.out.println("********************************************************");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String extractUrl(String input) {
		String regex = "window\\.open\\(\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		return matcher.find() ? matcher.group(1) : null;
	}

	public static void main(String args[]) {
		try {
			try {
				String csvFile = args[2];
				CSVWriter writer = null;

				try {
					writer = new CSVWriter(new FileWriter(csvFile));
					writer.writeNext(new String[] { "APPLICATION NUMBER", "APPLICANT NAME", "DATE OF SUBMISSION",
							"DATE OF ISSUE", "FEE DETAILS", "View Trade License", "Documents Attached", "FILE NOTE" });
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				List<String> filepaths = FileFetcher.getAllFilePaths(args[0]);
				for (String filepath : filepaths) {
					extractNWrite(filepath,args[1],writer);
				}
              writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
