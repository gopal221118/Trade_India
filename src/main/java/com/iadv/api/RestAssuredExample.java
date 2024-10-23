package com.iadv.api;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class RestAssuredExample {
    public static void main(String[] args) {
        // Disable SSL validation
        disableSSLValidation();

        // Base URL for the request
        RestAssured.baseURI = "https://tnurbanepay.tn.gov.in";

        // Prepare the request
        RequestSpecification request = RestAssured.given();

        // Add headers
        request.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8");
        request.header("accept-language", "en-US,en;q=0.5");
        request.header("cache-control", "max-age=0");
        request.header("content-type", "application/x-www-form-urlencoded");
        request.header("cookie", "ASP.NET_SessionId=mntbbfuxo3bzwna5vpxnaefm");
        request.header("origin", "https://tnurbanepay.tn.gov.in");
        request.header("referer", "https://tnurbanepay.tn.gov.in/PT_PropertySearch.aspx");
        request.header("sec-ch-ua", "\"Brave\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"");
        request.header("sec-ch-ua-mobile", "?0");
        request.header("sec-ch-ua-platform", "\"Windows\"");
        request.header("sec-fetch-dest", "document");
        request.header("sec-fetch-mode", "navigate");
        request.header("sec-fetch-site", "same-origin");
        request.header("sec-fetch-user", "?1");
        request.header("sec-gpc", "1");
        request.header("upgrade-insecure-requests", "1");
        request.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");

        // Define string variables for form data
        String eventTarget = "";
        String eventArgument = "";
        String lastFocus = "";
        String viewState = ""; // Set this to the actual value
        String viewStateGenerator = "3B37067B"; // Set this if needed
        String viewStateEncrypted = ""; // Set this to the actual value
        String eventValidation = ""; // Set this to the actual value
        String alertMessage = "";
        String orgValue = "199"; // Set this if needed
        String assessmentNumber = "";
        String wardValue = "5162"; // Set this if needed
        String assesseName = "";
        String propertyGroup = "--Select--"; // Set this if needed
        String oldAssessmentNumber = "";
        String streetValue = "--Select--"; // Set this if needed
        String doorNumber = "";
        String btnSearchValue = "Search";

        // Add form data (urlencoded parameters)
        request.formParam("__EVENTTARGET", eventTarget);
        request.formParam("__EVENTARGUMENT", eventArgument);
        request.formParam("__LASTFOCUS", lastFocus);
        request.formParam("__VIEWSTATE", viewState);
        request.formParam("__VIEWSTATEGENERATOR", viewStateGenerator);
        request.formParam("__VIEWSTATEENCRYPTED", viewStateEncrypted);
        request.formParam("__EVENTVALIDATION", eventValidation);
        request.formParam("ctl00$alert_msg", alertMessage);
        request.formParam("ctl00$PageContent$drporg", orgValue);
        request.formParam("ctl00$PageContent$txt_assementno", assessmentNumber);
        request.formParam("ctl00$PageContent$drpward", wardValue);
        request.formParam("ctl00$PageContent$txt_AssesseName", assesseName);
        request.formParam("ctl00$PageContent$drppropetgrup", propertyGroup);
        request.formParam("ctl00$PageContent$txtOldAssNo", oldAssessmentNumber);
        request.formParam("ctl00$PageContent$drpstreet", streetValue);
        request.formParam("ctl00$PageContent$txt_DoorNo", doorNumber);
        request.formParam("ctl00$PageContent$btnSearch", btnSearchValue);

        // Send the POST request and capture the response
        Response response = request.post("/PT_PropertySearch.aspx");

        // Print the response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }

    // Disable SSL validation method
    private static void disableSSLValidation() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };

            sslContext.init(null, trustManagers, new java.security.SecureRandom());
            SSLContext.setDefault(sslContext);

            RestAssured.useRelaxedHTTPSValidation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
