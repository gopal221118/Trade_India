package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TN_PropertyTax_API {

    public static String getAPIResponse(String vs,String ev) {
    	String resp="";
    	try
    	{
        RestAssured.baseURI = "https://tnurbanepay.tn.gov.in";

        // Sending the POST request and capturing the response
        Response response = given()
				.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .header("accept-language", "en-US,en;q=0.8")
                .header("cache-control", "max-age=0")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("cookie", "ASP.NET_SessionId=bxygfvvqahhrfopleo50evei; ASP.NET_SessionId=bxygfvvqahhrfopleo50evei")
                .header("origin", "https://tnurbanepay.tn.gov.in")
                .header("priority", "u=0, i")
                .header("referer", "https://tnurbanepay.tn.gov.in/PT_PropertySearch.aspx")
                .header("sec-ch-ua", "\"Brave\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("sec-fetch-dest", "document")
                .header("sec-fetch-mode", "navigate")
                .header("sec-fetch-site", "same-origin")
                .header("sec-fetch-user", "?1")
                .header("sec-gpc", "1")
                .header("upgrade-insecure-requests", "1")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
                .formParam("__EVENTTARGET", "")
                .formParam("__EVENTARGUMENT", "")
                .formParam("__LASTFOCUS", "")
                .formParam("__VIEWSTATE", vs)
                .formParam("__VIEWSTATEGENERATOR", "3B37067B")
                .formParam("__VIEWSTATEENCRYPTED", "")
                .formParam("__EVENTVALIDATION", ev)
                .formParam("ctl00$alert_msg", "")
                .formParam("ctl00$PageContent$drporg", "199")
                .formParam("ctl00$PageContent$txt_assementno", "")
                .formParam("ctl00$PageContent$drpward", "5158")
                .formParam("ctl00$PageContent$txt_AssesseName", "")
                .formParam("ctl00$PageContent$drppropetgrup", "--Select--")
                .formParam("ctl00$PageContent$txtOldAssNo", "")
                .formParam("ctl00$PageContent$drpstreet", "--Select--")
                .formParam("ctl00$PageContent$txt_DoorNo", "")
                .formParam("ctl00$PageContent$btnSearch", "Search")
                .when()
                .post("/PT_PropertySearch.aspx");

        // Printing the response status code and body
        resp=resp+response.getBody().asString();
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
		return resp;
    }
}