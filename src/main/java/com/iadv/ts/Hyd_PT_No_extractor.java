package com.iadv.ts;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Hyd_PT_No_extractor {
	public static void extractData(String fpath, CSVWriter writer) {
		try {
			File input = new File(fpath);
			Document doc = Jsoup.parse(input, "UTF-8");
			String html = (doc.html());
			String htmlx = (html.substring(html.indexOf("<body>") + 7, html.indexOf("<table>"))).replace("<input", "~");
			String htmls[] = htmlx.split("~");
			for (String htmlp : htmls) {
				if (htmlp.contains("XXXX")) {
					String ptin = "PT-"+(((htmlp.substring(htmlp.indexOf("</span>") - 10, htmlp.indexOf("</span>") + 7)
							.trim())).replace("</span>", "")).trim();
					String htmlr = (htmlp.substring(htmlp.indexOf("</span>") + 7, htmlp.length()).trim());
					String pno = htmlr.substring(htmlr.length() - 10, htmlr.length());
					System.out.println(ptin + "---" + htmlr.substring(0, htmlr.length() - 10) + "---" + pno);
					String tempdata[] = { ptin, htmlr.substring(0, htmlr.length() - 10), pno };
					writer.writeNext(tempdata);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
