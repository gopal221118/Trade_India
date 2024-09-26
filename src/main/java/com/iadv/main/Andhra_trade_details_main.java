package com.iadv.main;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Andhra_trade_details_main {
    public static void main(String[] args) {
   
        String[] arr = args[0].split("-");
        for(int i=Integer.parseInt(arr[0]);i<=Integer.parseInt(arr[1]);i++)
        {
        String url = "https://anantapur.emunicipal.ap.gov.in/tl/viewtradelicense/viewTradeLicense-view.action?id="+String.valueOf(i);
        String filePath = "C:\\Users\\ajay9\\Downloads\\andhradata\\pageSource"+String.valueOf(i)+".html"; // Specify your file path here

        try {
            // Fetch the HTML document
            Document document = Jsoup.connect(url).get();
            // Get the page source as a string
            String pageSource = document.outerHtml();

            // Write the page source to a text file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(pageSource);
                System.out.println("Page source written to " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error fetching the page source: " + e.getMessage());
        }
        }
    }
}

