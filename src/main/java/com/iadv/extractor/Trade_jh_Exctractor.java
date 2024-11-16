package com.iadv.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Trade_jh_Exctractor {

    public static String extractDataFromHtml(String response) {
        String textData = "";

        // CSS selector for the specific div
        String cssSelector = "div.panel-body > form > div:nth-child(2) > div";

        // Parse the HTML using JSoup
        Document doc = Jsoup.parse(response);
            
        // Select the elements matching the given CSS selector
        Elements elements = doc.select(cssSelector);

        // If no elements are found, set textData to "NA"
        if (elements.isEmpty()) {
            textData = "NA";
        } else {
            // Iterate over the selected elements and extract the text
            for (Element element : elements) {
                textData += element.text() + " "; // Append each element's text
            }
            textData = textData.trim(); // Remove any trailing spaces
        }

        // Return the text data
        return textData;
    }
}
