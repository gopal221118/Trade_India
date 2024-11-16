package com.iadv.ts;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ROT_Step3_appno {
    public static void main(String[] args) {
        String excelFilePath = args[0];
        String saveFolderPath = args[1];  
        try (FileInputStream file = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
           
            for (Row row : sheet) {
                String tinNumber = row.getCell(0).getStringCellValue(); 
                Response response = RestAssured
                        .given()
                        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                        .header("accept-language", "en-US,en;q=0.9,hi;q=0.8,te;q=0.7")
                        .header("cache-control", "max-age=0")
                        .header("cookie", "ASP.NET_SessionId=tthsdcrzpajcbu20hhihozoc; __RequestVerificationToken_L0NETUFfQVJCUw2=59r-cFjYWsBE3sdLfwZes715jBNWaylOmXq3Zpxs73ja3LHpxfT4NtdGyBslGGcuBqSEqKWJTgVgu_zsy51hvTc_I5cJbsuKHvng_UmYW7E1; CitizenCookie=CITIZEN=1; ts-cgg-gov-1xc=\"675891e1a47d0b6c\"; e0d503=Oyh23KeYyXFSYdR7xKQ4LXXnIdiKLKnEm9vZaPBIdwi2am3hQ1BChESVKSCj/mzd6gM1dOzKm/jR5oORC+I/c2e/LwRZVm/RBbrUUVAg5yy+do+8EuEkg/S+CHaOvIpvuCiRXYMoce/zUUxmo5GeEaBDGEfcA2aipekX+deDdIkcreV8; TS01dc4fc6=014a837b260c32ab02bea8c6645ac23d7fa531aec38a8252e90be048b79dbffdbcbe0b25be9592e4bb90418984e8d5e96e46cd1370; CitizenCookie=CITIZEN=1; TS01dc4fc6=01adc37616db0656ea97a84752e17d05ed34b43fdbb178cbb351eb264ea5b6c43e88ca14427a4680d6ff47cbec719572b3b436b49c")
                        .header("origin", "https://cdma.cgg.gov.in")
                        .header("priority", "u=0, i")
                        .header("referer", "https://cdma.cgg.gov.in/CDMA_ARBS/Dashboard/updatecitizenmobileno")
                        .header("sec-ch-ua", "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
                        .header("sec-ch-ua-mobile", "?0")
                        .header("sec-ch-ua-platform", "\"Windows\"")
                        .header("sec-fetch-dest", "document")
                        .header("sec-fetch-mode", "navigate")
                        .header("sec-fetch-site", "same-origin")
                        .header("sec-fetch-user", "?1")
                        .header("upgrade-insecure-requests", "1")
                        .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
                        .multiPart("module", "TL")
                        .multiPart("I_ASMTNO", "")
                        .multiPart("I_TINNO", tinNumber)
                        .multiPart("I_CANNO", "")
                        .post("https://cdma.cgg.gov.in/CDMA_ARBS/Dashboard/updatecitizenmobileno");
                if (response.getStatusCode() == 200) {
                    String htmlContent = response.getBody().asString();
                    saveHtmlToFile(tinNumber, htmlContent, saveFolderPath);
                    System.out.println("Response for TIN " + tinNumber + " saved successfully.");
                } else {
                    System.out.println("Failed to fetch data for TIN " + tinNumber + ". HTTP Status Code: " + response.getStatusCode());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void saveHtmlToFile(String tinNumber, String htmlContent, String saveFolderPath) {
        String sanitizedTinNumber = tinNumber.replace("/", "_"); 
        String fileName = saveFolderPath + "/Response_" + sanitizedTinNumber + ".html"; 

        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();  // Create parent directories if necessary

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(htmlContent.getBytes());
                System.out.println("File saved: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
