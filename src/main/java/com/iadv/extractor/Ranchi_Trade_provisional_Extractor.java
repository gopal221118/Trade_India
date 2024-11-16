package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ranchi_Trade_provisional_Extractor {

    public static void main(String[] args) {
    	
    	ArrayList<String> al =FilePathCollector.seshaGan();
    	System.out.println(al);
        // Path to the local HTML file
        File input = new File("D:\\Jharkhand_Ranchi\\_154138101218030025_100096.html");

        // Initialize the HashMap to store extracted data
        Map<String, String> extractedData = new HashMap<>();

        try {
            // Parse the HTML file using JSoup
            Document doc = Jsoup.parse(input, "UTF-8");

            // Extracting specific data using the CSS selector
            Elements dataElements = doc.select("#print_watermark > table:nth-child(5) > tbody > tr > td > span");

            // Adding column headers and their corresponding values
            extractedData.put("Application No", doc.selectFirst("td:contains(Application No) > span").text());
            extractedData.put("Provisional License No", doc.selectFirst("td:contains(Provisional License No) > span").text());
            extractedData.put("Mr/Mrs", doc.selectFirst("td:contains(Mr/Mrs) > span").text());
            extractedData.put("Apply Date", doc.selectFirst("td:contains(Apply Date) > span").text());
            extractedData.put("Establishment Date", doc.selectFirst("td:contains(Establishment Date) > span").text());
            extractedData.put("Valid Upto", doc.selectFirst("td:contains(Valid Upto) > span").text());

            // Print the extracted data to the console
            for (Map.Entry<String, String> entry : extractedData.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
     