package com.iadv.extractor;

import java.io.File;




import java.util.ArrayList;


import java.util.HashMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
    // For representing an HTML element
import org.jsoup.select.Elements;   // For selecting elements from the document

import com.iadv.api.*;
import com.iadv.data.ReadfromTxt;
public class TN_Propery_extractor {
        public static ArrayList<String> extractorTn(HashMap<String, ArrayList<String>> munci_no_with_ward_no,String filepath,HashMap<String,String> mncmap)
        {  ArrayList<String> assesment_no = new ArrayList<>();
          for(String munci_no:munci_no_with_ward_no.keySet())
          {     String mncname="";
        	   for(String key: mncmap.keySet())
        	   {
        		   String filePath = filepath+ File.separator +mncmap.get(key)+ ".txt";

        	        String searchString =munci_no;  // Replace with the string you're searching for

        	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	            String line;
        	            boolean found = false;

        	            // Read each line of the file
        	            while ((line = br.readLine()) != null) {
        	                if (line.contains(searchString)) {
        	                    found = true;
        	                    break;
        	                }
        	            }

        	            if (found) {
        	               mncname=mncmap.get(key);
        	               System.out.println("founded");
        	            } else {
        	                System.out.println("Trying.......");
        	            }

        	        } catch (IOException e) {
        	            System.out.println("An error occurred while reading the file.");
        	            e.printStackTrace();
        	        }

        	   }
        	   for(String munci_nos: munci_no_with_ward_no.keySet())
               {  
            	   
                   String postdata = ReadfromTxt.readFileAsString(filepath+ File.separator +mncname+ ".txt");
                   String[] postvals = postdata.split("&");
                   String vsx = "";
                   String evx = "";
                   String etx = "";
                   String ecode="";
               for (String postval : postvals) {
            	   if (postval.startsWith("__VIEWSTATE=")) {
                       vsx = postval.split("=")[1].trim();
                   } 
                   else if (postval.startsWith("__EVENTTARGET=")) {
                     etx=postval.split("=")[1].trim();
                      
                   } 
                   else if(postval.startsWith("__EVENTVALIDATION=")) {
                  	 evx   =postval.split("=")[1].trim();
                     
                  }
                   else if (postval.startsWith("ctl00%24PageContent%24drporg=")) {
                       ecode=postval.split("=")[1].trim();
                        
                     } 
                   else {
                       System.out.println(postval);
                   }
               }
            	   for(int i=0;i<munci_no_with_ward_no.get(munci_nos).size();i++)
                    {
            		   String response1="";
            	       //String response1=TN_PropertyTax_API2.getAPIResponse(vsx, evx,etx,ecode,munci_no_with_ward_no.get(munci_no).get(i));
            	       Document doc = Jsoup.parse(response1);
            	       Element element = doc.selectFirst("#asda > div:nth-child(2) > div:nth-child(2)>i");

            	    // Get the text inside the <i> tag
            	    String text = null;
            	    if (element != null) {
            	          text = element.text();  // if the text is null or element is null then add continue statement for this i variable iteration
            	        System.out.println(text);  // Prints: You are viewing page 1 of 4
            	    } else {
            	        continue;
            	    }
            	    char lastChar = text.charAt(text.length() - 1);
            	    Integer pages = Character.getNumericValue(lastChar);
           	        for(int j=1;j<=pages;j++)
           	        {
           	        	String response2=TN_PropertyTax_API3.getAPIResponse(vsx, evx,etx,   ecode,munci_no_with_ward_no.get(munci_no).get(i) , j);
           	        	
           	      // Parse the HTML response
           	        	Document doc2 = Jsoup.parse(response2);

           	        	// Use the CSS selector to select the elements
           	        	Elements elements = doc2.select("#PageContent_dgvDetails > tbody > tr > td:nth-child(2) > span");

           	        	// Loop through the selected elements and extract the text
           	        	for (Element element1 : elements) {
           	        	    String text1 = element1.text();  // Extract the text from each element
           	        	      // Print the text or process it as needed
           	        	 assesment_no.add(text1);
           	        	}

           	        	
           	        }
                    
                    }
               
               }
        	   
        	   
          }return assesment_no;
        }
}
