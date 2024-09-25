package com.iadv.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TGSPDCL_mobile_api {
	 private static final String REQUEST_URL = "https://tgsouthernpower.org/updatemobilenuminfo";
	    
	    public String getResponse(String uniqueServiceNumber) throws Exception {
	        String urlWithParams = REQUEST_URL + "?ukscno=" + uniqueServiceNumber;
	        @SuppressWarnings("deprecation")
			URL url = new URL(urlWithParams);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST"); // Adjust to POST as per the cURL request
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder response = new StringBuilder();
	        String line;
	        while ((line = in.readLine()) != null) {
	            response.append(line);
	        }
	        in.close();
	        
	        return response.toString();

}
}
