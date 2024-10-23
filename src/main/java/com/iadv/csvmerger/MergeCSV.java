package com.iadv.csvmerger;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergeCSV {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java MergeCSV <input_directory> <output_file>");
            return;
        }

        String inputDirectory = args[0];
        String outputFilePath = args[1];

        File dir = new File(inputDirectory);
        if (!dir.isDirectory()) {
            System.out.println("The provided input path is not a directory.");
            return;
        }

        // Get all CSV files from the directory
        List<File> csvFiles = Stream.of(dir.listFiles())
                .filter(file -> file.getName().endsWith(".csv"))
                .collect(Collectors.toList());

        if (csvFiles.isEmpty()) {
            System.out.println("No CSV files found in the directory.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            boolean headerWritten = false;

            // Loop through each file
            for (File file : csvFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean isHeader = true;

                    // Write content to output file
                    while ((line = reader.readLine()) != null) {
                        // If it's the first file or a non-header line, write it to the output
                        if (!isHeader || !headerWritten) {
                            writer.println(line);
                        }
                        if (isHeader) {
                            headerWritten = true; // Ensure headers are only written once
                        }
                        isHeader = false;
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }

            System.out.println("Merged CSV files written to: " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Error writing to output file.");
            e.printStackTrace();
        }
    }
}
