package com.iadv.api;

import org.jsoup.Jsoup;

import com.opencsv.CSVWriter;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Kerala_SNE_API {

	public static void getNWrite(String regsearchValue, String url, String csrfToken, CSVWriter writer) {
		try {
			RestAssured.baseURI = url;

			// Create a RequestSpecification for the request
			RequestSpecification request = RestAssured.given()
					.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
					.header("Accept-Language", "en-US,en;q=0.9").header("Cache-Control", "max-age=0")
					.header("Connection", "keep-alive").header("Cookie", "PHPSESSID=d75cfrfh42r4h3f19aueiroe72")
					.header("Origin", "https://lcas.lc.kerala.gov.in")
					.header("Referer", "https://lcas.lc.kerala.gov.in/office/registration/onlinerenewal_online_pay.php")
					.header("Sec-Fetch-Dest", "document").header("Sec-Fetch-Mode", "navigate")
					.header("Sec-Fetch-Site", "same-origin").header("Sec-Fetch-User", "?1")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
					.header("sec-ch-ua",
							"\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
					.header("sec-ch-ua-mobile", "?0").header("sec-ch-ua-platform", "\"Windows\"")
					.formParam("hidcsrf_token", csrfToken).formParam("cbotype", "1")
					.formParam("regsearch", regsearchValue).formParam("comnactid", "").formParam("comnregid", "")
					.formParam("btnDisp", "Display");

			// Send the POST request
			Response response = request.post();

			// Print the response status and body
			// System.out.println("Response Code: " + response.getStatusCode());

			org.jsoup.nodes.Document doc = Jsoup.parse(response.getBody().asString());
			String DOR = doc.select("#shpsdatereg").val();
			String Shop_Name = doc.select("#txtshopname").val();
			String Employer_Name = doc.select("#shpempname").val();
			String Address = doc.select("#shopempaddress").val();
			String ED = doc.select("#cbotoyear").val();
			String RD = doc.select("#cbofromyea").val();
			String phone = doc.select("#phone").val();

			String temp[] = { DOR, Shop_Name, Employer_Name, Address, ED, RD, phone };
			for(int i =0; i<temp.length; i++) {
				if(temp[i].length()>0)
				{
					System.out.println("Length hai");
					break;
				}else
				{
					System.out.println("No");
				}
			}
			writer.writeNext(temp);

			System.out.println("Response Body: " + DOR);
			System.out.println("Response Body: " + Shop_Name);
			System.out.println("Response Body: " + Employer_Name);
			System.out.println("Response Body: " + Address);
			System.out.println("Response Body: " + ED);
			System.out.println("Response Body: " + RD);
			System.out.println("Response Body: " + phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
