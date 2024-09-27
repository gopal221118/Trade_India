package com.iadv.api;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Trade_Mobile_API {
	
	public static String getHTMLResponse(String mobno)
	{
		String html="";
		try
		{
			float num1 = Float.parseFloat(mobno); 
			long num = (long) num1;
			System.out.println(String.valueOf(num));
			 RestAssured.baseURI = "https://tgsouthernpower.org";

		        // Send the POST request with headers and form data
		        Response response = given()
		            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
		            .header("accept-language", "en-US,en;q=0.9,hi;q=0.8,te;q=0.7")
		            .header("cache-control", "max-age=0")
		            .header("content-type", "application/x-www-form-urlencoded")
		            .header("cookie", "_ga=GA1.1.2125635047.1726126723; JSESSIONID=B2A7274B661D913C77258EF678A98BD6; _ga_EHQ3N9V3P3=GS1.1.1726909597.18.1.1726909700.0.0.0")
		            .header("origin", "https://tgsouthernpower.org")
		            .header("priority", "u=0, i")
		            .header("referer", "https://tgsouthernpower.org/searchRegsByMobile")
		            .header("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Google Chrome\";v=\"128\"")
		            .header("sec-ch-ua-mobile", "?0")
		            .header("sec-ch-ua-platform", "\"Windows\"")
		            .header("sec-fetch-dest", "document")
		            .header("sec-fetch-mode", "navigate")
		            .header("sec-fetch-site", "same-origin")
		            .header("sec-fetch-user", "?1")
		            .header("upgrade-insecure-requests", "1")
		            .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
		            .formParam("mobile_no", String.valueOf(num))  // Form data for mobile number
		        .when()
		            .post("/getRegs")  // The relative path for the request
		        .then()
		            .extract()
		            .response();

		        // Print the response status and body
		        System.out.println("Response Status Code: " + response.getStatusCode());
		        html=html+response.getBody().asString();
		        //System.out.println("Response Body: " + response.getBody().asString());
	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}
}
