package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTML_Attribute_Extractor {
	
	 public static String getOnclickValue(String html) {
	        Document doc = Jsoup.parse(html);
	        Element link = doc.selectFirst("a");
	        return (link != null && link.hasAttr("onclick")) ? link.attr("onclick") : null;
	    }
	 
	 public static String getHrefValue(String html) {
	        Document doc = Jsoup.parse(html);
	        Element link = doc.selectFirst("a");
	        return (link != null && link.hasAttr("href")) ? link.attr("href") : null;
	    }

}
