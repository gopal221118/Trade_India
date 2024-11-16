package com.iadv.api;

import io.restassured.RestAssured;


import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


import java.io.FileWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TN_PropertyTax_API {

    public static String getAPIResponse(String postdata) {
        String resp = "";
        try {
            RestAssured.baseURI = "https://tnurbanepay.tn.gov.in";

            Response response = given()
                .config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .header("content-type", "application/x-www-form-urlencoded")
                .body(postdata.substring(postdata.indexOf("[") + 1, postdata.lastIndexOf("]"))).when()
                .post("/PT_PropertySearch.aspx");

            resp = response.getBody().asString();
            System.out.println("Response Status Code: " + response.getStatusCode());
       

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }
    
    public static ArrayList<String> extractWardList(String resp)
    {
    	System.out.println(resp);
    	ArrayList<String> tempwardlist = new ArrayList<String>();
    	try
    	{
    		 Document doc = Jsoup.parse(resp);
             Elements options = doc.select("#PageContent_drpward > option");

             for (int i1 = 1; i1 < options.size(); i1++) {
                 Element option = options.get(i1);
                 String value = option.attr("value");
                 if (!value.isEmpty()) {
                	 tempwardlist.add(value);
                 }
             }
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
		return tempwardlist;
    }
}
