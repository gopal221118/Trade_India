package com.iadv.api;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class FoSCOSRefAPI {
    public static String getFoSCOSResponse(String url) {
    	String resp="";
    	try
    	{
        // Setting the base URI
        RestAssured.baseURI = "https://iadv.in";

        // Sending the request and getting the response
        Response response = given()
			.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .header("Accept-Language", "en-US,en;q=0.9")
            .header("Cache-Control", "no-cache")
            .header("Connection", "keep-alive")
            .header("Cookie", "<your_cookie_here>")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
            .when()
            .get(url);

        // Print the response
        resp=resp+response.getBody().asString();
        System.out.println(response.getBody().asString());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
		return resp;
    }
}
