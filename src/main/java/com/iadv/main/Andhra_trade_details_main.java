package com.iadv.main;



import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Andhra_trade_details_main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] municipalities = {
        	    // Municipal Corporations (17)
        	    "visakhapatnam", "vijayawada", "guntur", "nellore", "kurnool", "tirupati",
        	    "rajahmundry", "kakinada", "anantapur", "eluru", "kadapa", "ongole",
        	    "chittoor", "machilipatnam", "srikakulam", "vizianagaram", "bhimavaram",

        	    // Municipalities (80)
        	    "adoni", "amalapuram", "anakapalle", "bapatla", "bhimavaram", "bobbili",
        	    "chilakaluripet", "dharmavaram", "gudivada", "hindupur", "ichchapuram",
        	    "jaggayyapeta", "kadiri", "kandukur", "kavali", "kovvur", "mandapeta",
        	    "markapur", "nagari", "nandyal", "narasapuram", "narasaraopet", "narsipatnam",
        	    "nuzvid", "palacole", "palasa", "parvathipuram", "peddapuram", "pithapuram",
        	    "proddatur", "pulivendula", "punganur", "puttur", "rajampet", "rayachoti",
        	    "rayadurg", "salur", "samalkot", "sattenapalle", "siddipet", "srikalahasti",
        	    "tadepalligudem", "tadpatri", "tanuku", "tenali", "tuni", "uravakonda",
        	    "venkatagiri", "vinukonda", "yemmiganur", "yerraguntla", "kuppam", "addanki",
        	    "atmakur", "banaganapalle", "bestavaripeta", "chebrole", "chintalapudi",
        	    "darsi", "dhone", "giddalur", "gokavaram", "gorantla", "gudur", "jammalamadugu",
        	    "kakinada rural", "kanigiri", "korukonda", "mylavaram", "nellimarla", "paderu",
        	    "palamaner", "pamarru", "pedana", "podili", "rampachodavaram", "sullurpet",
        	    "tekkali", "vuyyuru", "vepagunta", "mandasa", "malikipuram", "manubolu"
        	};


        System.out.println("0-16 will be the municipal corporation so provide id between 1-9999");
        ArrayList<String> municipalitiesList = new ArrayList<>(Arrays.asList(municipalities));
        
        
        int start = scan.nextInt();
        int end = scan.nextInt();
        scan.nextLine(); // Clear the newline character

        System.out.println("Enter the range for id in the URL (format: start-end):");
        String input = scan.nextLine(); // Use nextLine to read the entire line
        String[] arr = input.split("-");

        // Check if arr has exactly two elements
        if (arr.length != 2) {
            System.err.println("Invalid input. Please enter a valid range in the format 'start-end'.");
            return; // Exit if the input is not valid
        }

        int idStart, idEnd;
        try {
            idStart = Integer.parseInt(arr[0]);
            idEnd = Integer.parseInt(arr[1]);
        } catch (NumberFormatException e) {
            System.err.println("Please enter valid integers for the ID range.");
            return; // Exit if parsing fails
        }

        for (int m = start; m <= end; m++) {
            for (int i = idStart; i <= idEnd; i++) {
                String url = "https://" + municipalitiesList.get(m) + ".emunicipal.ap.gov.in/tl/viewtradelicense/viewTradeLicense-view.action?id=" + i;
                String filePath = args[0] + "\\pageSource" + municipalitiesList.get(m) + i + ".html"; // Specify your file path here

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
        scan.close();
    }
}
