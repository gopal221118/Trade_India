package com.iadv.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TN_property_reader2 {

    // Function to read the CSV file and store Assesment_no in ArrayList
    public static ArrayList<String> tn_Property_Reader_2(String filename) {
        ArrayList<String> assesment_nos = new ArrayList<>();

        try {
            // Read all lines from the file, skipping the header
            List<String> lines = Files.lines(Paths.get(filename))
                                      .skip(1) // Skip the header line
                                      .collect(Collectors.toList());

            // Add each line to the ArrayList
            assesment_nos.addAll(lines);

        } catch (IOException e) {
            // Handle the exception in case of file reading issues
            System.out.println("Error while reading the CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        return assesment_nos;
    }
}

