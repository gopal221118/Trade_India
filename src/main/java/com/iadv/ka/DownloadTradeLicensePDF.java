package com.iadv.ka;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.iadv.data.XLSXtoHashMap_Row_Header;

public class DownloadTradeLicensePDF {

    public static void downloadKAPDF(String baseUrl,String applicationNumber,String folderpath) {
     
        try {
            Path outputPath = Paths.get(folderpath, "TL_" + ((applicationNumber.replace("/", "_"))).replace(" ", "_") + ".pdf");
            // Sending the GET request using Rest-Assured
            Response response = RestAssured.given()
    				.config(RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Connection", "keep-alive")
                .header("Cookie", "JSESSIONID=9A7E7E379B5509387CF4EF780E6C59C7.worker42; _ga=GA1.3.1746916687.1731419816; _gid=GA1.3.1442448814.1731755966; _gat=1; _ga_28WJTFNQDL=GS1.3.1731768039.5.1.1731768131.0.0.0")
                .header("Referer", "http://www.mrc.gov.in/TradeLicense/loadviewPdf")
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .queryParam("applNum", applicationNumber)
                .get(baseUrl);

            // Check if response is successful
            if (response.getStatusCode() == 200) {
                System.out.println("Response received successfully. Saving PDF...");

                // Save the PDF content to the specified file
                try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
                    fos.write(response.asByteArray());
                }

                System.out.println("PDF downloaded successfully at: " + outputPath.toAbsolutePath());
            } else {
                System.out.println("Failed to download PDF. HTTP Status Code: " + response.getStatusCode());
            }
        } catch (IOException e) {
            System.out.println("Error while saving the PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String args[])
    {
    	try
    	{
    		 HashMap<Integer, HashMap<String, String>> appnolist =	XLSXtoHashMap_Row_Header.getConvertedMap(args[0]);
    		 for(int i=1;i<appnolist.size();i++)
    		 {
    			 System.out.println(appnolist.get(i).get("APPLICATION NUMBER"));
    			 downloadKAPDF(args[1],appnolist.get(i).get("APPLICATION NUMBER"),args[2]);
    		 }
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
    }
}

