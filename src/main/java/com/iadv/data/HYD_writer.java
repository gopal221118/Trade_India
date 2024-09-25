package com.iadv.data;


import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class HYD_writer {

    // Write data to a CSV file
    public void writeDataToCSV(HashMap<String, String[]> dataMap, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Writing the header
            String[] header = {"TIN NO", "Owner Name", "Owner Address", "Trade Name", "Trade Address", "Bussiness Type", "Date of Commencement", "Fee to Pay"};
            writer.writeNext(header);

            // Writing the data
            for (String key : dataMap.keySet()) {
                String[] data = dataMap.get(key);

                // Ensure addresses are properly quoted to prevent comma misalignment
                data[3] = "\"" + data[3] + "\""; // Quote Owner Address
                data[5] = "\"" + data[5] + "\""; // Quote Trade Address
                
                // Writing each row
                writer.writeNext(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
