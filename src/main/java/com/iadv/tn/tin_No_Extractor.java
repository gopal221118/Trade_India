package com.iadv.tn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class tin_No_Extractor {
    public static void main(String[] args) {
        File inputFolder = new File("C:/TL_TN/Output_Data/fdsa");
        File outputFile = new File("C:/TL_TN/TL_Number/assessmentData.csv");
       try {
    	   FileWriter writer = new FileWriter(outputFile);
        writer.write("Assessment No,Old Assessment No,Owner Name,Address\n");
        HashMap<String, String[]> assessmentData = new HashMap<>();
        try {
                for (File inputFile : inputFolder.listFiles()) {
                    if (inputFile.isFile() && inputFile.getName().endsWith(".html")) {
                        Document doc = Jsoup.parse(inputFile, "UTF-8");
                        Elements rows = doc.select("tr");
                        for (Element row : rows) {
                            Elements columns = row.select("td");
                            if (columns.size() >0) {
                                String assessmentNo = columns.get(1).text();
                                String oldAssessmentNo = columns.get(2).text().isEmpty() ? "NA" : columns.get(2).text();
                                String ownerName = columns.get(3).text().isEmpty() ? "NA" : columns.get(3).text();
                                StringBuilder addressBuilder = new StringBuilder();
                                for (int i = 4; i < columns.size(); i++) {
                                    if (!columns.get(i).text().isEmpty()) {
                                        addressBuilder.append(columns.get(i).text()).append(" ");
                                    }
                                }
                                String address = addressBuilder.toString().trim();
                                if (address.isEmpty()) address = "NA";
                                String[] details = {oldAssessmentNo, ownerName, address};
                                assessmentData.put(assessmentNo, details);
                                	 String[] values1 = null;
                                 values1 = assessmentData.get(assessmentNo);
                                writer.write(assessmentNo + "," + values1[0] + "," + values1[1] + ",\"" + values1[2] + "\"\n");
                                System.out.println(assessmentNo+"  "+ values1);
                                
                            } else {
                                System.out.println("Row in " + inputFile.getName() + " skipped due to insufficient columns.");
                            }
                        }
                    } else {
                        System.out.println("Skipped file: " + inputFile.getName());
                    }
                }

        } catch (IOException e) {
            System.out.println("An error occurred while processing files: " + e.getMessage());
            e.printStackTrace();
        }
    } catch(Exception e)
    {
    	e.printStackTrace();
    }
}}
