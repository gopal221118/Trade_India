package com.iadv.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.iadv.api.TradeLicenseFetcher;
import com.iadv.data.Trade_Reader_Jh;
import com.iadv.extractor.Trade_jh_Exctractor;


public class Trade_main_JH {

    public static void main(String[] args) {
        ArrayList<String> responseList = new ArrayList<String>();
        HashMap<String, String> finalData = new HashMap<String, String>();
        int i = 0;

        if (args.length > 0) {
            String csvFilePath = args[0];
            String outputCsvPath = args[1]; 

            if (!outputCsvPath.endsWith(".csv")) {
                outputCsvPath += ".csv";  
            }

            Trade_Reader_Jh reader = new Trade_Reader_Jh();
            ArrayList<String> licenceNumbers = reader.readLicenceNumbersFromCSV(csvFilePath);

            for (String licenceNo : licenceNumbers) {
                String response = TradeLicenseFetcher.tradeApi(licenceNo);
                responseList.add(response);
                String textData = Trade_jh_Exctractor.extractDataFromHtml(response);
                finalData.put(licenceNo, textData);
                System.out.println("Data Fetched successfully " + i++);
            }

          
            File outputDir = new File(outputCsvPath).getParentFile();
            if (!outputDir.exists()) {
                if (outputDir.mkdirs()) {
                    System.out.println("Directory created: " + outputDir.getAbsolutePath());
                } else {
                    System.err.println("Failed to create directory: " + outputDir.getAbsolutePath());
                    return;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsvPath))) {
                writer.write("Licence Number,Valid Upto Date");
                writer.newLine();

                for (Entry<String, String> entry : finalData.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
                System.out.println("Data successfully written to " + outputCsvPath);

            } catch (IOException e) {
                System.err.println("Error writing to the CSV file: " + e.getMessage());
            }

        } else {
            System.err.println("Please provide the CSV file path as an argument.");
        }
    }
}
