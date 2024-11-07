package com.iadv.ts;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import com.iadv.data.ReadfromTxt;
import com.iadv.data.XLSXtoHashMap_Row_Header;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;


public class Hyderabad_Step2 {

	// Method to perform POST request and return the response as a string
	public static String getResponseFromCurl(String vs, String ev, String ptin) {
		String resp = "";
		try {
			Response response = RestAssured.given()
					.config(RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation())) 
					.header("accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
					.header("accept-language", "en-US,en;q=0.9").header("cache-control", "max-age=0")
					.header("content-type", "application/x-www-form-urlencoded")
					.header("origin", "https://eodb.ghmc.gov.in").header("priority", "u=0, i")
					.header("referer", "https://eodb.ghmc.gov.in/MobileUpdate.aspx")
					.header("sec-ch-ua",
							"\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
					.header("sec-ch-ua-mobile", "?0").header("sec-ch-ua-platform", "\"Windows\"")
					.header("sec-fetch-dest", "document").header("sec-fetch-mode", "navigate")
					.header("sec-fetch-site", "same-origin").header("sec-fetch-user", "?1")
					.header("upgrade-insecure-requests", "1")
					.header("user-agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
					.header("Cookie", "ASP.NET_SessionId=v1ysm4lxqfinezrq2t55arpi")
					.formParam("__VIEWSTATE",vs)
					.formParam("__VIEWSTATEGENERATOR", "4ECF7CB8")
					.formParam("__EVENTVALIDATION",ev)
					.formParam("ctl00$ContentPlaceHolder1$ddlCircle", "0")
					.formParam("ctl00$ContentPlaceHolder1$txtptinno", ptin)
					.formParam("ctl00$ContentPlaceHolder1$btnSubmit", "Search").when()
					.post("https://eodb.ghmc.gov.in/MobileUpdate.aspx") // Sending POST request
					.then().extract().response();
			resp = resp + (response.getBody().asString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public static void main(String[] args) {
		try {
			String vs = ReadfromTxt.readFileAsString(args[0]);
			String ev = ReadfromTxt.readFileAsString(args[1]);
			HashMap<Integer, HashMap<String, String>>  ptinmap = XLSXtoHashMap_Row_Header.getConvertedMap(args[2]);
			for(int i=1;i<ptinmap.size();i++)
			{
			String resp = getResponseFromCurl(vs, ev, ((ptinmap.get(i).get("PTIN_No")).replace("PT-", "")).trim());
			File file = new File(args[3]+File.separator+ptinmap.get(i).get("PTIN_No")+".html");
			FileWriter fw=new FileWriter(file);
			fw.write(resp);
			System.out.println(resp);
			fw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
