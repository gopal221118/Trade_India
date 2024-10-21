package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.iadv.data.ReadfromTxt;

import java.security.cert.X509Certificate;
public class Api_response_step2_property_tn {

    public static String api_Response_From_Assesmentno(String no,String txtforapi) {
    	String file = ReadfromTxt.readFileAsString(txtforapi);
    	String Response="";
        try {
            // Disable SSL certificate verification
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Set the base URI
            RestAssured.baseURI = "https://tnurbanepay.tn.gov.in";

            // Prepare the request
            RequestSpecification request = RestAssured.given()
                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                    .header("accept-language", "en-US,en;q=0.8")
                    .header("cache-control", "max-age=0")
                    .header("cookie", "ASP.NET_SessionId=zlkhdicdm1o2d4l2ez0qz01d; ASP.NET_SessionId=zlkhdicdm1o2d4l2ez0qz01d")
                    .header("origin", "https://tnurbanepay.tn.gov.in")
                    .header("priority", "u=0, i")
                    .header("referer", "https://tnurbanepay.tn.gov.in/PT_AssessmentViewsLink.aspx")
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
                    .formParam("ctl00$alert_msg", "")
                    .formParam("ctl00$PageContent$txt_ConNo", no)
                    .formParam("ctl00$PageContent$txt_OldConnectionNo", "")
                    .formParam("ctl00$PageContent$btnSearch", "Search")
                    .formParam("ctl00$PageContent$hid_assid", "2525388")
                    .formParam("__EVENTTARGET", "")
                    .formParam("__EVENTARGUMENT", "")
                    .formParam("__LASTFOCUS", "")
                    .formParam("__VIEWSTATE", file) // Set this as needed
                    .formParam("__VIEWSTATEGENERATOR", "4D2BD4F4")
                    .formParam("__EVENTVALIDATION", "/wEdAAkR+CXhNsVzbgnKWbmZ7lhuiRxVGtWoS22cPPpMjKJ83FppwEJNDzQXpXUR68fNHu46KNIorwODtlVnDEeDRWqKDWQOnfROU3xaT9jX0kGxbbLZeDQMbk5wgpm+/L1Dhx06ji4lRBK9xCoTvoveLmIIQgaAJNMdWmuX0Iz5F5gxmuocGNW+6vQ0+bzCQldn42sklsd5oOR5+DkWKZcKU73XKL3//Q==");

            // Send the POST request
            Response response = request.post("/PT_AssessmentViewsLink.aspx");

            // Print the response
            System.out.println("Response Code: " + response.getStatusCode());
            Response = Response+response.getBody().asString();

        } catch (Exception e) {
            e.printStackTrace();
        } return Response;
    }
}
