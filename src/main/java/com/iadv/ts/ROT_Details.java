package com.iadv.ts;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static io.restassured.RestAssured.given;

public class ROT_Details {

    public static void main(String[] args) throws IOException {
        // Load TIN numbers from Excel file
        FileInputStream file = new FileInputStream(new File(args[0]));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = row.getCell(0);
            String tinNumber = cell.getStringCellValue();
            System.out.println("*********************");
            // Make the request and save response
            sendRequestAndSaveResponse(tinNumber,args[1]);
        }
        
        workbook.close();
        file.close();
    }

    public static void sendRequestAndSaveResponse(String tinNumber,String fpath) {
        // Set base URI
        RestAssured.baseURI = "https://cdma.cgg.gov.in/CDMA_ARBS/CDMA_PG";

        // Send POST request
        Response response = given()
            .header("accept", "text/html, */*; q=0.01")
            .header("accept-language", "en-US,en;q=0.9,hi;q=0.8,te;q=0.7")
            .header("content-type", "application/json; charset=UTF-8")
            .header("cookie", "ASP.NET_SessionId=wmrrdf0izje3l3ioorn0nrv2; CitizenCookie=CITIZEN=1; ts-cgg-gov-1xc=\"88ecd4ee01487803\"; __RequestVerificationToken_L0NETUFfQVJCUw2=9W9OqK3tm0bsAHPEQdP0R73oPx9tDPPKqkDUL7zbhvScCyErXXGwD6bUa1QUBBMA48zv1z0fTPGscR7JS0_sFMVbEmoAIMkgx9xe4kcNyxg1; e0d503=fOAe2mMC1T1j3nsO6j/3RERzcsm6YFvbrPbfoF+j21uhbhuUj2Am8/fLQ++902nyhqsfGHmW4eQcIfCACKdqqA/re6Q1XxUKXGWlJdSPiZrHniDNSU50gglEWWPOGSyQVjwAYIz0ZX6NlvNTk9wJjDYEC/KyVUgX5Zgc8hTCXphXvDRh; TS01dc4fc6=0122916886f32d1638b03bd516d51791fd9ec4bdff3a900c730053f20829b8a2777ad1e85d77aafb8daecb9f7ec411e8e20d4355c1; CitizenCookie=CITIZEN=1; TS01dc4fc6=014b0c213e5aa23952b9441607c5bb3e0a6564f2860d4495d0286a0c10f709f67cba6a387097783f4af98644f56ef1826e8c604556; e0d503=j7R2Fwnbr9uSyYPPiI+eoTumvnpuYF3bgWVcrlSNpHM8/E0uU+ACftYWQVCtE1+JvFDDMxa07T0GMUToTbE1mwi7kfitNC2SU/DWnKjgXlo5HeNgrJVarhf5Z8cuBeuEDUvC5tbow4TtWMJsRjwSAprUE+lTaIHIFAd/Cs98PWEqZ9yc")
            .header("origin", "https://cdma.cgg.gov.in")
            .header("priority", "u=1, i")
            .header("referer", "https://cdma.cgg.gov.in/CDMA_ARBS/CDMA_PG/GetTLDetails")
            .header("sec-ch-ua", "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
            .header("sec-ch-ua-mobile", "?0")
            .header("sec-ch-ua-platform", "\"Windows\"")
            .header("sec-fetch-dest", "empty")
            .header("sec-fetch-mode", "cors")
            .header("sec-fetch-site", "same-origin")
            .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
            .header("x-requested-with", "XMLHttpRequest")
            .body("{\"TINNO\": \"" + tinNumber + "\"}")
            .post("/GetTLDetails_PV");

        // Check if response is successful
        if (response.statusCode() == 200) {
            try {
                // Save response to HTML file
                File file = new File(fpath + tinNumber + ".html");
                file.getParentFile().mkdirs(); // Ensure directory exists
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(response.asByteArray());
                }
                System.out.println("Response saved for TIN number " + tinNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to get response for TIN number " + tinNumber);
        }
    }
}
