package com.iadv.cin;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iadv.data.ReadfromTxt;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GST_IND {

	public static void main(String[] args) {
		try
		{
			List<String> gsts = ReadfromTxt.readFileAsList(args[0]);
			String range[] = args[3].split("-");
			for(int i=Integer.parseInt(range[0].trim());i<=Integer.parseInt(range[1].trim());i++)
			{
				try
				{
				  File file=new File(args[2]+File.separator+(gsts.get(i)).trim()+".txt");
				  FileWriter fw=new FileWriter(file);
				  Response response = getGSTSearchResponse((gsts.get(i)).trim());
        	      fw.write(response.getBody().asString()+"\r\n");
			      Response responsex = getTaxpayerReturnDetails((gsts.get(i)).trim(), args[1]);
                  System.out.println(i+":"+(gsts.get(i)).trim()+":"+"Status Code: " + responsex.getStatusCode() + ":"+ response.getStatusCode());
                  fw.write("**************************************************"+"\r\n");
			      fw.write(responsex.getBody().asString()+"\r\n");
			      
			      System.out.println(Math.random());
			      fw.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	 public static Response getGSTSearchResponse(String gstin) {
	        // Base URI
	        RestAssured.baseURI = "https://plapi-corp.pinelabs.com";

	        // Headers
	        Map<String, String> headers = new HashMap<>();
	        headers.put("accept", "*/*");
	        headers.put("accept-language", "en-US,en;q=0.9");
	        headers.put("cache-control", "no-cache");
	        headers.put("content-type", "application/json");
	        headers.put("origin", "https://www.pinelabs.com");
	        headers.put("pragma", "no-cache");
	        headers.put("priority", "u=1, i");
	        headers.put("referer", "https://www.pinelabs.com/");
	        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
	        headers.put("sec-ch-ua-mobile", "?0");
	        headers.put("sec-ch-ua-platform", "\"Windows\"");
	        headers.put("sec-fetch-dest", "empty");
	        headers.put("sec-fetch-mode", "cors");
	        headers.put("sec-fetch-site", "same-site");
	        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
	        headers.put("Cookie", "SetCookie=1c0d592c68ffa826e1e4c9010b057bab; connect.sid=s%3A4wfkDedSIOPcQ9fJpxuDn-cYfpfjjfUD.1CQscZzOOwSnvk5VKph%2BIhL%2F8wjw1MS21RTh6y9%2BQhs");

	        String requestBody = "{\"gstin\":\"" + gstin + "\"}";

	        return RestAssured
	                .given()
	                .headers(headers)
	                .body(requestBody)
	                .when()
	                .post("/api/gst-search");
	    }
	 
	 public static Response getTaxpayerReturnDetails(String gstin, String fy) {
	        // Base URI
	        RestAssured.baseURI = "https://services.gst.gov.in";

	        // Headers
	        Map<String, String> headers = new HashMap<>();
	        headers.put("Accept", "application/json, text/plain, */*");
	        headers.put("Accept-Language", "en-US,en;q=0.9");
	        headers.put("Cache-Control", "no-cache");
	        headers.put("Connection", "keep-alive");
	        headers.put("Content-Type", "application/json;charset=UTF-8");
	        headers.put("Cookie", "Lang=en; ak_bmsc=DC02B3316A08F666D37518A212C2A946~000000000000000000000000000000~YAAQZUxhaN94ri6TAQAAJGMtkRqfgGMIvI8LtdCxIkSTePqrmM9fVQ7DzcpSTkX1va/6LAkUQ8nCMwxFH57JZ7UPT6ua8tK+Sr3JnSuzAaeK0cajucLtmxQfgzjRExAX+zVmqSsMNrBLQxfzGgP8zKlVTjaQtumA4yZj82f9eH8z/rGAWb5oHTCYt/fVpEDc85JPpknXT+C3bs3k5q1Oev7xwU71IzD6OrePbNXOwjk9Ztcyj/kZ84uCLgHmKhCI/ovKJPhYsUhmBTY6dvqPX1+Itrca2z5DbMVQKy6sTnPKxAqGfwe1vIC7UblbbSoo4g0FLhKNlKG/EU08f6y+tRR7jIYZMPrgUwPbk2eeCZICo5sgyzXXIUd0Asdpini6; TS01b8883c=0140752c73b6f8b250c347b19bcddcc9c3a1964b80fea99059d20e2a11a2575a57fd61a7fead357bc327ccdc5cef3e6206534e8242; TS01b8883c=0140752c731ca333747f13a1e28927c9772b71383ea996810a9fae17789dbd82d972af63c19c3bc21ce218c339796069aa1f84b343");
	        headers.put("Origin", "https://services.gst.gov.in");
	        headers.put("Pragma", "no-cache");
	        headers.put("Referer", "https://services.gst.gov.in/services/searchtp");
	        headers.put("Sec-Fetch-Dest", "empty");
	        headers.put("Sec-Fetch-Mode", "cors");
	        headers.put("Sec-Fetch-Site", "same-origin");
	        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
	        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
	        headers.put("sec-ch-ua-mobile", "?0");
	        headers.put("sec-ch-ua-platform", "\"Windows\"");

	        // JSON Body
	        String requestBody = String.format("{\"gstin\":\"%s\",\"fy\":\"%s\"}", gstin, fy);

	        // Sending the POST request and returning the response
	        return RestAssured
	                .given()
	                .headers(headers)
	                .body(requestBody)
	                .when()
	                .post("/services/api/search/taxpayerReturnDetails");
	    }
	 
	}


