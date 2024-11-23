package com.iadv.tn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iadv.data.FileFetcher;
import com.iadv.data.HTMLSourcefromFile;
import com.iadv.extractor.HTML_RowCount_Generic;
import com.opencsv.CSVWriter;

public class TN_HTML_Extractor {

	public static void main(String args[]) {
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				 writer.writeNext(new String[] {
		                    "S.No", "Assessment No", "Old Assessment No", "Owner Name",
		                    "Address", "Half Year Tax", "Balance Value", "Action"
		                });
				 } catch (IOException e) {
				e.printStackTrace();
				return;
			}
		try {
			List<String> htmllists = FileFetcher.getAllFilePaths(args[0]);
			for (String htmlpath : htmllists) {
				System.out.println(htmlpath);
				String fsource = HTMLSourcefromFile.getSouce(htmlpath);
				int rowcount = HTML_RowCount_Generic.getRowCount(fsource, "#PageContent_dgvDetails > tbody > tr");
				System.out.println(rowcount);
				for (int i = 2; i < rowcount; i++) {
					ArrayList<String> tempvals = new ArrayList<>();
					try {
						Document doc = Jsoup.parse(fsource, "UTF-8");
						Elements tds = doc.select("#PageContent_dgvDetails > tbody > tr:nth-child("+i+") > td");
						for (Element td : tds) {
							tempvals.add(td.text());
						}
						System.out.println("TD Values: " + tempvals);
						String[] temparray = tempvals.toArray(new String[0]);
						writer.writeNext(temparray);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
