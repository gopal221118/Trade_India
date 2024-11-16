package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static io.restassured.RestAssured.given;

public class BBMP_Trade {

    public static void main(String[] args) {
        
        // Step 1: GET request to fetch the dynamic tokens (__VIEWSTATE, __EVENTVALIDATION)
        RestAssured.baseURI = "https://trade.bbmpgov.in/Forms/frmRenewalTradeRegistration.aspx";
        
        Response getResponse = given()
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
                .header("accept", "*/*")
                .when()
                .get();
        
        // Parse the response body to extract __VIEWSTATE and __EVENTVALIDATION using Jsoup
        String responseBody = getResponse.getBody().asString();
        Document doc = Jsoup.parse(responseBody);
        
        // Extract the values for __VIEWSTATE, __EVENTVALIDATION
        String viewState = doc.select("input[name=__VIEWSTATE]").val();
        String eventValidation = doc.select("input[name=__EVENTVALIDATION]").val();
        String viewStateGenerator = doc.select("input[name=__VIEWSTATEGENERATOR]").val();

        // Step 2: POST request with dynamic values from the GET request
        Response postResponse = given()
				.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .header("accept", "*/*")
                .header("accept-language", "en-US,en;q=0.9")
                .header("cache-control", "no-cache")
                .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("cookie", getResponse.getCookies().toString()) // Reuse the session cookies from the GET request
                .header("origin", "https://trade.bbmpgov.in")
                .header("referer", "https://trade.bbmpgov.in/Forms/frmRenewalTradeRegistration.aspx")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
                .formParam("ctl00$ToolkitScriptManager1", "ctl00$ContentPlaceHolder1$upTradeLicense|ctl00$ContentPlaceHolder1$btnDetailSearch")
                .formParam("ToolkitScriptManager1_HiddenField", "")
                .formParam("ctl00$ContentPlaceHolder1$ddlZone", "8")
                .formParam("ctl00$ContentPlaceHolder1$ddlWard", "174")
                .formParam("ctl00$ContentPlaceHolder1$txtName", "a")
                .formParam("ctl00$ContentPlaceHolder1$hfTotalTradeLicenseFee", "0")
                .formParam("ctl00$ContentPlaceHolder1$hfTradeSubID", "0")
                .formParam("ctl00$ContentPlaceHolder1$hfAnimalhubandry", "0")
                .formParam("ctl00$ContentPlaceHolder1$hfPenaltypercentage", "0")
                .formParam("__EVENTTARGET", "")
                .formParam("__EVENTARGUMENT", "")
                .formParam("__LASTFOCUS", "")
                .formParam("__VIEWSTATE", viewState)  // Pass dynamic __VIEWSTATE
                .formParam("__VIEWSTATEGENERATOR", viewStateGenerator)  // Pass dynamic __VIEWSTATEGENERATOR
                .formParam("__EVENTVALIDATION", eventValidation)  // Pass dynamic __EVENTVALIDATION
                .formParam("__ASYNCPOST", "true")
                .formParam("ctl00$ContentPlaceHolder1$btnDetailSearch", "Search")
                .when()
                .post();
        
        // Print the response status code and body
        System.out.println("Response Code: " + postResponse.getStatusCode());
        System.out.println("Response Body: " + postResponse.getBody().asString());
    }
}
