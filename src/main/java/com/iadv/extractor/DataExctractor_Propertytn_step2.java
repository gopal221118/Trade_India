package com.iadv.extractor;




import java.util.HashMap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class DataExctractor_Propertytn_step2 {
    public static HashMap<String, String> data_Exctractor_propertytn_step2(String response) {
        HashMap<String, String> map = new HashMap<>();

        try {
            // Parse the response HTML using Jsoup
            Document doc = Jsoup.parse(response);

            // Select elements based on the provided CSS selector
            Elements elements = doc.select("#PageContent_assessment > div > fieldset:nth-child(4) > div > div > span");

            // Loop through the elements and populate the HashMap
            for (int i = 0; i < elements.size(); i += 2) {
                // Get the key from the odd-indexed element
                String key = elements.get(i).text();

                // Check if the next element exists to avoid IndexOutOfBoundsException
                if (i + 1 < elements.size()) {
                    // Get the value from the even-indexed element
                    String value = elements.get(i + 1).text();

                    // Add the key-value pair to the HashMap
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

