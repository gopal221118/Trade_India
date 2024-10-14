package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Basic_FSSAI_Download {
    public static void downloadLicense(String downloadpath, String licno, String refid,String token,String authid) {
        // Base URL for the request
        String url = "https://foscos.fssai.gov.in/gateway/downloadpdf2/registration/"+refid;

        // Send the request using RestAssured
        Response response = RestAssured
                .given()
				.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Authorization", "Bearer "+token)
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .header("Cookie", "_gid=GA1.3.1155686640.1727123058; key_13624026000145=bWXsrjg+rxeTikoS1TsEUjBYtxrU/hCTS59TuMOftxo=; _ga_P6LE4V93JW=GS1.1.1727123803.1.1.1727123810.0.0.0; _ga=GA1.3.779093791.1714223281; _ga_X6L29149T6=GS1.3.1727123064.6.1.1727123816.58.0.0; _gat=1; _ga_E101FG0RXN=GS1.3.1727123073.7.1.1727124850.0.0.0")
                .header("Referer", "https://foscos.fssai.gov.in/public/fbo/dashboard")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-origin")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
                .header("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Google Chrome\";v=\"128\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("x-auth-user-id", authid)
                .get(url);

        // Check if the request was successful
        if (response.getStatusCode() == 200) {
            try {
                // Get the response body as an InputStream
                InputStream inputStream = response.asInputStream();

                // Save the response as a PDF file
                FileOutputStream fileOutputStream = new FileOutputStream(downloadpath+File.separator+licno+".pdf");

                // Buffer to read the input stream data
                byte[] buffer = new byte[4096];
                int bytesRead;

                // Read from the input stream and write to the file
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                // Close the streams
                fileOutputStream.close();
                inputStream.close();

                System.out.println("PDF downloaded successfully.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to download PDF. Status code: " + response.getStatusCode());
        }
    }
}

