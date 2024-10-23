package com.iadv.jh;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import org.apache.http.conn.ssl.NoopHostnameVerifier;

public class TradeLicenseFetcher {

    public static String tradeApi(String str) {
    	
    	String response1 = "";
        // Turn off SSL verification
        RestAssured.useRelaxedHTTPSValidation();

        // Set the base URI
        RestAssured.baseURI = "https://municipalservices.jharkhand.gov.in";

        // Send the POST request with form-data and necessary headers
        Response response = 
            given()
                .relaxedHTTPSValidation() // Disable SSL verification
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Cache-Control", "max-age=0")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", "_ga=GA1.3.1740947683.1727094649; _ga_FF714QK21S=GS1.3.1727094649.1.1.1727094663.0.0.0; payment=Oops%2C%20Payment%20Failed%21%21%21; heading_title=HOME; ci_session=frcn1dgbc2cu7df4oulefagcutv64l00; ci_session=vbpvk01hc9k72g645639gnb6pj0chlok")
                .header("Origin", "https://municipalservices.jharkhand.gov.in")
                .header("Referer", "https://municipalservices.jharkhand.gov.in/TradeCitizen/searchLicense/c81e728d9d4c2f636f067f89cc14862c")
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .formParam("Searchlicense", str) // Form data
            .when()
                .post("/TradeCitizen/searchLicense/c81e728d9d4c2f636f067f89cc14862c");

        // Print the response
     response1 = response.getBody().asString();
     return response1;
    }
}
