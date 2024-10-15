package com.iadv.extractor;

import java.util.ArrayList;
import java.util.HashMap;


import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
    // For representing an HTML element
import org.jsoup.select.Elements;   // For selecting elements from the document

import com.iadv.api.*;
public class TN_Propery_extractor {
        public static ArrayList<String> extractorAndhra(String vs,String ev,HashMap<String, ArrayList<String>> munci_no_with_ward_no)
        {
           ArrayList<String> assesment_no = new ArrayList<>();
           for(String munci_no: munci_no_with_ward_no.keySet())
           {   for(int i=0;i<munci_no_with_ward_no.get(munci_no).size();i++)
                {
        	       String response1=TN_PropertyTax_API2.getAPIResponse(vs, ev, munci_no,munci_no_with_ward_no.get(munci_no).get(i));
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
       	        	String response2=TN_PropertyTax_API3.getAPIResponse(vs, ev, munci_no,munci_no_with_ward_no.get(munci_no).get(i) , pages);
       	        	
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
           
           }return assesment_no;
        }
}
