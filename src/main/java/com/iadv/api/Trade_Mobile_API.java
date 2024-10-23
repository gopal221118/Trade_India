package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class Trade_Mobile_API {

    public static void main(String[] args) {
        // Define the base URI
        RestAssured.baseURI = "https://onlinepayments.ghmc.gov.in";

        // Make the GET request and capture the response
        Response response = RestAssured
            .given()
			.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
            .header("accept", "*/*")
            .header("accept-language", "en-US,en;q=0.9")
            .header("cache-control", "no-cache")
            .header("cookie", "ghmc-gov-in_xc=\"a6bc23450dda4797\"; ASP.NET_SessionId=q2xemuisunohaj3iotyysy45; __RequestVerificationToken=k6GsgDyFTTtXOdtUUqfVLbs5cbjNfzTIJYXEgHufnDDCLjEpJbLIa6kxBlERGF6nAOOABBTM_biqHZlWNipUjUi7xHt4RONqQeSSC8T4VZU1; TS01dc4fc6=01f8178b9e1b9b2df370bc4b693cb6ac06b2b5a383c668152076584c9b27fd78ba443de1ccea82176a389ae3bfa207e5d9dd492b6f; ghmc-gov-in_xc=\"a6bc23450dda4797\"; ac0d03=od1DVKcXU23Pv7wNrCJpdiIILZODVOW2rBmklnjREPlTF5oUMP1I9YNHiJs1c3pkeeFJFN2KMf79ZerS3k2/LBLrqLyOQEr078cAKZKMqyN7ZzTdofsYmMz2aExeA5Sqd9eOQSR/uB6TwMkBj7c6fdH2w/5j6vOljHRf9geZnO90vEKR")
            .header("pragma", "no-cache")
            .header("priority", "u=1, i")
            .header("referer", "https://onlinepayments.ghmc.gov.in/")
            .header("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"")
            .header("sec-ch-ua-mobile", "?0")
            .header("sec-ch-ua-platform", "\"Windows\"")
            .header("sec-fetch-dest", "empty")
            .header("sec-fetch-mode", "cors")
            .header("sec-fetch-site", "same-origin")
            .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
            .header("x-requested-with", "XMLHttpRequest")
            .queryParam("Ptin", "1015501988")
            .when()
            .get("/PTax/GetMobileNo")  // Send GET request
            .then()
            .extract()
            .response();

        // Print the response status code
        System.out.println("Response Code: " + response.getStatusCode());

        // Print the response body
        System.out.println("Response Body: " + response.asString());
    }
}
