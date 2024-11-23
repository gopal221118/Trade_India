package com.iadv.ts;

import java.io.File;
import java.io.FileWriter;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class TLSearchDetailFetcher {
    public static void getNWriteResponseVals(String distcode, String alp, String muncode,String fpath) {
    	try
    	{
    		File file=new File(fpath);
    		FileWriter fw=new FileWriter(file);
            String paramValue = ""+distcode+",,"+alp+",,"+muncode+",,"; // Modify this as needed
            Response response = RestAssured
            .given()
            .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation())) // Relaxed SSL validation
            .header("accept", "text/html, */*; q=0.01")
            .header("accept-language", "en-US,en;q=0.9,hi;q=0.8,te;q=0.7")
            .header("content-type", "application/json; charset=UTF-8")
            .header("cookie", "ASP.NET_SessionId=rm1ks5rvpgfu4jxsrtw0taw3; ts-cgg-gov-1xc=\"bd1ff5e79e613e99\"; __RequestVerificationToken_L0NETUFfQVJCUw2=xRZima6XqXYmErKRHkU3hxHy4e2q2vFyxvbL2Kjb8LjsXM1Xpmi7wDpPt9Shtqvqo6dqhAXId_I_mblIZbb6prkqjj0cHbWsJsd8AoFPzDY1; CitizenCookie=CITIZEN=1; TS01dc4fc6=01b9414e33c9ad158837801ba6bf76b8e58a498d4569b77a484d2772d8e744d7822d1b1e365520a0745bcea4566b998a7339c42e2d; e0d503=7DZgOkReTsr/HgOHORC7QL+OIOgPsfTgLTxKOgWOxoAWz4OVOYTj2yJ0jrKuB7Yx2Mcg/gEoYLA42uJCFbBHgPg0ifsBaTrIHNS8JIY8qitqT10W3p8KDIb20AQAeL9vzKb5RtXOtubOPQ0CpNqo3V17EMDaw61F6VEyq1ich1Av2RcS; e0d503=waw4XD6Rd+hKNzbvc6ygV8ltqU5mofVBqrQeu4PV7TEQeBPZFCaAZU1plv3pRCFIwvu/dBLhUGgbbhOpo1zA8uTiMsjOt7drOE5mb0HUg66jdERttIfmrPhmkiHVjOxH3oa4m3xgQrkeKl3YXJWNL7qiH75q2/up9ofpLxt8GxJuwIrM")
            .header("origin", "https://cdma.cgg.gov.in")
            .header("priority", "u=1, i")
            .header("referer", "https://cdma.cgg.gov.in/CDMA_ARBS/CDMA_PG/TLMENU")
            .header("sec-ch-ua", "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
            .header("sec-ch-ua-mobile", "?0")
            .header("sec-ch-ua-platform", "\"Windows\"")
            .header("sec-fetch-dest", "empty")
            .header("sec-fetch-mode", "cors")
            .header("sec-fetch-site", "same-origin")
            .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
            .header("x-requested-with", "XMLHttpRequest")
            .body("{\"Param\": \"" + paramValue + "\"}")
            .when()
            .post("https://cdma.cgg.gov.in/CDMA_ARBS/CDMA_PG/BindTLSearchDetail_PV") // POST request
            .then()
            .extract()
            .response();
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        fw.write(response.getBody().asString());
        fw.close();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
    }
}

