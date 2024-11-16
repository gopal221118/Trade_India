package com.iadv.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class ExtractTableFromHTML {
    public static String alltables(String htmlpath) {
    	String htmltable="";
        try {
            // Load the HTML file
            File input = new File(htmlpath);

            // Parse the HTML using Jsoup
            Document doc = Jsoup.parse(input, "UTF-8");

            // Select all table elements
            Elements tables = doc.select("table");

            // Iterate through each table and extract the HTML
            for (Element table : tables) {
                System.out.println("Extracted Table:");
                htmltable=htmltable+table.outerHtml();
                System.out.println(table.outerHtml());
                System.out.println("--------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return htmltable;
    }
}
