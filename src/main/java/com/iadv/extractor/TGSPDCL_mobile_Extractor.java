package com.iadv.extractor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TGSPDCL_mobile_Extractor {
	  public HashMap<String, String> extractData(String response) {
	        HashMap<String, String> dataMap = new HashMap<>();
	        
	        // Define patterns for each field
	        Pattern[] patterns = {
	            Pattern.compile("<th class=\"text-right\">Consumer Name</th>\\s*<td><b>(.*?)</b></td>"),
	            Pattern.compile("<th class=\"text-right\">Unique Service Number</th>\\s*<td>.*?<b>(.*?)</b></td>"),
	            Pattern.compile("<th class=\"text-right\">Service Number</th>\\s*<td>.*?<b>(.*?)</b></td>"),
	            Pattern.compile("<th class=\"text-right\">Address</th>\\s*<td><b>(.*?)</b></td>"),
	            Pattern.compile("<th class=\"text-right\">ERO</th>\\s*<td>.*?<b>(.*?)</b></td>"),
	            Pattern.compile("<th class=\"text-right\">Mobile Number </th>\\s*<td>.*?<b>(.*?)</b></td>")
	        };
	        
	        String[] keys = {"Consumer Name", "Unique Service Number", "Service Number", "Address", "ERO", "Mobile Number"};
	        
	        for (int i = 0; i < patterns.length; i++) {
	            Matcher matcher = patterns[i].matcher(response);
	            if (matcher.find()) {
	                dataMap.put(keys[i], matcher.group(1));
	            }
	        }
	        
	        return dataMap;

}
}
