package com.iadv.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDataExtractor {

	public static void main(String[] args) {
		try {
			String csvFile = args[1];
			CSVWriter writer = null;
			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "DataUrl", "CIN", "Name", "ROC", "Status" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			try {
				List<String> filepaths = FileFetcher.getAllFilePaths(args[0]);
				String ranges[] = args[2].split("-");
				for (int x = Integer.parseInt(ranges[0].trim()); x < Integer.parseInt(ranges[1].trim()); x++) {
					try {
						String html = HTMLSourcefromFile.getSouce(filepaths.get(x));
						Document document = Jsoup.parse(html);
						Elements companyCards = document.select("div.company-card-body--cl.boxSearch");
						for (int i = 0; i < companyCards.size(); i++) {
							try {
								Element card = companyCards.get(i);
								String dataUrl = "https://www.planetexim.net" + card.attr("data-url");
								String cin = card.select("p.font--weight-roc-cl").first().text();
								String name = card.select("p.cname-heading").text();
								String roc = card.select("div.col-md-6 p").first().text();
								String status = card.select("div.col-md-6 p.text-success").text();
								String companydata[] = { dataUrl, cin, name, roc, status };
								System.out.println(x + "_" + i + "---->" + (dataUrl + "------" + cin + "------" + name
										+ "------" + roc + "------" + status));
								writer.writeNext(companydata);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
