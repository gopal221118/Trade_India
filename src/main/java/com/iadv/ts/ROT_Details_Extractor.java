package com.iadv.ts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ROT_Details_Extractor {
    public static void main(String[] args) {
        File folder = new File("E:\\Telengana_TL\\ROT\\ROT_Details\\DetailsTS_DO_2009_1116");
        File csvOutputFile = new File("E:\\Telengana_TL\\ROT\\Details_Output\\output.csv");

        List<String> headers = Arrays.asList("TIN_NO", "Application_No", "Title_Of_Trade", "Trade_Valid_Upto", 
                                             "Trade_Address", "Created_Date", "Name_of_Owner", "Address_of_Owner", 
                                             "Category", "Sub_Category", "Length", "Width", "Plinth_Area", 
                                             "Unit_Rate", "Road_Type");
        int addressOfOwnerIndex = headers.indexOf("Address_of_Owner");
        try (FileWriter csvWriter = new FileWriter(csvOutputFile)) {
            csvWriter.append(String.join(",", headers));
            csvWriter.append("\n");
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".html")) {
                            System.out.println("Processing file: " + file.getName());

                            try {
                                Document document = Jsoup.parse(file, "UTF-8");
                                Elements elements = document.select("table.table:nth-child(2) > tbody:nth-child(2) > tr > td > strong");
                                HashMap<String, List<String>> dataMap = new HashMap<>();
                                if (!elements.isEmpty()) {
                                    String key = elements.get(0).text();
                                    List<String> values = new ArrayList<>();

                                    for (int i = 1; i < elements.size(); i++) {
                                        values.add(elements.get(i).text());
                                    }
                                    dataMap.put(key, values);

                                    List<String> row = new ArrayList<>();
                                    row.add(key); 

                                    // Add remaining values based on headers count
                                    for (int i = 0; i < headers.size() - 1; i++) {
                                        // If the column is "Address_of_Owner", add quotes around the value
                                        if (i == addressOfOwnerIndex - 1) {
                                            row.add("\"" + (i < values.size() ? values.get(i) : "") + "\"");
                                        } else {
                                            row.add(i < values.size() ? values.get(i) : "");
                                        }
                                    }

                                    csvWriter.append(String.join(",", row));
                                    csvWriter.append("\n");
                                } else {
                                    System.out.println("No matching elements found in file: " + file.getName());
                                }
                            } catch (IOException e) {
                                System.err.println("Error parsing file: " + file.getName());
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    System.out.println("Could not retrieve files from the specified directory.");
                }
            } else {
                System.out.println("The specified path is not a directory.");
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file.");
            e.printStackTrace();
        }
    }
}
