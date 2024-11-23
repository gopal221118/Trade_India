package com.iadv.ts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ROT_Step3_extractor {
    public static void main(String[] args) {
        String folderPath = args[0];
        HashMap<String, String> tinToMobileMap = new HashMap<>();
        try {
            File folder = new File(folderPath);
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".html")) {
                    Document doc = Jsoup.parse(file, "UTF-8");
                    Element tinElement = doc.selectFirst(".ghmc_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1)");
                    Element mobileElement = doc.selectFirst(".ghmc_table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(5)");
                    if (tinElement != null && mobileElement != null) {
                        String tinNumber = tinElement.text();
                        String mobileNumber = mobileElement.text();
                        tinToMobileMap.put(tinNumber, mobileNumber);
                    }
                }
            }

            System.out.println("TIN to Mobile Number Map:");
            for (String tin : tinToMobileMap.keySet()) {
                System.out.println("TIN Number: " + tin + " -> Mobile Number: " + tinToMobileMap.get(tin));
            }
            System.out.println("Total count of entries: " + tinToMobileMap.size());
            String csvFilePath = args[1];
            try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
                writer.println("TIN_Number,Mobile_Number");
                for (String tin : tinToMobileMap.keySet()) {
                    String mobile = tinToMobileMap.get(tin);
                    writer.println(tin + "," + mobile);
                }
                System.out.println("Data successfully written to " + csvFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
