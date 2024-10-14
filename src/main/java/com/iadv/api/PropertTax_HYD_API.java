package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.internal.support.FileReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PropertTax_HYD_API {

    public static void main(String[] args) {
        // Set the base URI for GHMC
    	File file = new File("C:\\Users\\Superuser\\Desktop\\MP_Trade\\Input_Data\\key.txt");
        
        // Reading the file content
        String filecontent="";
		try {
			filecontent = filecontent + new Scanner(new FileInputStream(file), "UTF-8").useDelimiter("\\A").next();
	        System.out.println(filecontent);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    	//BufferedReader reader = new BufferedReader(fr);
        RestAssured.baseURI = "https://ghmc.gov.in";

        // Sending POST request with headers and form data
        Response response = given()
				.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .header("accept", "*/*")
                .header("accept-language", "en-US,en;q=0.9")
                .header("cache-control", "no-cache")
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("cookie", "ASP.NET_SessionId=fgywfrivzakld4szpigrbt1l; ghmc-gov-in_xc=\"cb95d934be858454\"; TS01dc4fc6=014b0c213ee62d48c15fd038f54c980cc6ac51848d46fb0e27c66968fc6ac9941d1169551c9be6b95ba4cbd1d42ff67818b93d6a4f; ac0d03=ZQCf1ejeSrG58uYJwGMtsD6Pks5dU4pYUnK2Vi7Uk02uTJcyx52phegDmgXsQnqzUAl+cnmITX+t8rGmdnsL/GjSBCKWoDpv8f0J9V8OHNuK0uIoLqgXL+R78xUew/AJsUGF36SSXdaL5wf8N93W2Pil37g3Kuc6Ds6xrScD4JyVv4Lj")
                .header("origin", "https://ghmc.gov.in")
                .header("pragma", "no-cache")
                .header("priority", "u=1, i")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
                .header("x-microsoftajax", "Delta=true")
                .header("x-requested-with", "XMLHttpRequest")
                .formParam("ctl00$ContentPlaceHolder1$script", "ctl00$ContentPlaceHolder1$upPanel|ctl00$ContentPlaceHolder1$btnsubmitotp")
                .formParam("__EVENTTARGET", "")
                .formParam("__EVENTARGUMENT", "")
                .formParam("__VIEWSTATE", filecontent)  // Set __VIEWSTATE as empty
                .formParam("__EVENTVALIDATION", "")
                .formParam("ctl00$ContentPlaceHolder1$ddlcircle", "1049")
                .formParam("ctl00$ContentPlaceHolder1$txtptinno", "")
                .formParam("ctl00$ContentPlaceHolder1$txtowner", "d")
                .formParam("ctl00$ContentPlaceHolder1$txtdoorno", "")
                .formParam("__ASYNCPOST", "true")
                .formParam("ctl00$ContentPlaceHolder1$btnsubmitotp", "Submit")
                .when()
                .post("/Search.aspx");
        
        

        // Print the response status and body
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }
}

