package com.iadv.jh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trade_Reader_Jh {
    
    public ArrayList<String> readLicenceNumbersFromCSV(String csvFilePath) {
        ArrayList<String> licenceNumbers = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                licenceNumbers.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return licenceNumbers;
    }
}
