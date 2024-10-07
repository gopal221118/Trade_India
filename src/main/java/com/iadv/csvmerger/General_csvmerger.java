package com.iadv.csvmerger;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class General_csvmerger {

    public static void main(String[] args) {
        // Directory containing the CSV files
        String csvDirectory = "C:\\Rocjar\\inputfile";
        
        // Output file path
        String outputFilePath = "C:\\Rocjar\\inputfile\\output1.xlsx";

        // List to store CSV file names
        List<String> csvFiles = new ArrayList<>();
        
        // Read the directory and get the list of CSV files
        File folder = new File(csvDirectory);
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".csv")) {
                csvFiles.add(file.getAbsolutePath());
            }
        }

        // Merge the CSV files
        mergeCSVFiles(csvFiles, outputFilePath);
    }

    private static void mergeCSVFiles(List<String> csvFiles, String outputFilePath) {
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            pw = new PrintWriter(new FileWriter(outputFilePath));

            for (int i = 0; i < csvFiles.size(); i++) {
                String file = csvFiles.get(i);
                br = new BufferedReader(new FileReader(file));
                String line;

                // If not the first file, skip the header
                if (i != 0) {
                    br.readLine(); // Skip the header line
                }

                // Write each line from the current CSV to the output file
                while ((line = br.readLine()) != null) {
                    pw.println(line);
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) pw.close();
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
