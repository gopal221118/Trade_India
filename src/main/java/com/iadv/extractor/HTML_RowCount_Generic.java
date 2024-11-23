package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTML_RowCount_Generic {

	public static int getRowCount(String html, String cssselector) {
		int count = 0;
		try {
			Document doc = Jsoup.parse(html, "UTF-8");
			Elements rows = doc.select(cssselector);
			count = count + (rows.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}
}
