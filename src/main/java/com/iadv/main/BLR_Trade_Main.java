package com.iadv.main;



import java.util.ArrayList;
import java.util.List;

import com.iadv.api.BLR_Trade_API;
import com.iadv.data.ReadFileToArrayList;

public class BLR_Trade_Main {
    public static void main(String[] args) {
        // Check if file path is provided
        if (args.length < 1) {
            System.err.println("Please provide the CSV file path as an argument.");
            return;
        }
    
        try {
            // Send the POST request and get the response
            ArrayList<String> zowardlist=ReadFileToArrayList.getZoneNWard(args[0]);
            for (String zl : zowardlist) {
                System.out.println(zl);
                String response = BLR_Trade_API.sendPostRequest(zl,args[1]);
                System.out.println(response);
         }
         } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

