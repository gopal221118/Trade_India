package com.iadv.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.iadv.api.Trade_Mobile_API;
import com.iadv.data.Telangana_MobileToRegNo_input;
import com.iadv.extractor.Telangana_MobileToRegNo_Extraxtor;
import com.opencsv.CSVWriter;


public class Telangana_MobileToRegNo_Main {
    public static void main(String[] args) {
   
    		try {
    			String csvFile = args[1];
    	        CSVWriter writer = null;
    	        
    	        try {
    	            writer = new CSVWriter(new FileWriter(csvFile));
    	              writer.writeNext(new String[]{"SL No.", "Reg Number", "Status Link",
    	  					"Acknowledgement Link", "Registered Date", "Applicant Name", "Section Name", "Mobile"      
    	                });
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	            return; 
    	        }
    		HashMap<Integer, ArrayList<String>> mobileMap = Telangana_MobileToRegNo_input.getConvertedMap(args[0]);
    		System.out.println(mobileMap);
    	    String ranges[] = args[2].split("-") ;
    		for(int i=Integer.parseInt(ranges[0]);i<Integer.parseInt(ranges[1]);i++)
    	    {
    			System.out.println("*********************************");
    	    	String html=Trade_Mobile_API.getHTMLResponse(mobileMap.get(i).get(0));
    	    	Telangana_MobileToRegNo_Extraxtor.extractHTMLResponse(html,writer);
    	    	System.out.println("************************************************");
    	    }
    	writer.close();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

    	}
    		
			}


