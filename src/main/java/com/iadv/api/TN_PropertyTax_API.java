package com.iadv.api;

import io.restassured.RestAssured;


import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


import java.io.FileWriter;


public class TN_PropertyTax_API {

    public static String getAPIResponse(String postdata, FileWriter fw) {
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
            fw.write(resp);
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }
}
