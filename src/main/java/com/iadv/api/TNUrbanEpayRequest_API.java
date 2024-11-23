package com.iadv.api;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import com.iadv.data.ReadfromTxt;
import com.iadv.data.XLSXtoHashMap_Row_Header;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class TNUrbanEpayRequest_API {

	public static void main(String[] args) {

		String vs = ReadfromTxt.readFileAsString(args[0]);
		String ev = ReadfromTxt.readFileAsString(args[1]);

		HashMap<Integer, HashMap<String, String>> tntrademap = XLSXtoHashMap_Row_Header.getConvertedMap(args[2]);
        String range[] = args[4].split("-");
		for (int i = Integer.parseInt(range[0].trim()); i < Integer.parseInt(range[1].trim()); i++) {
			try {
				File file = new File(
						(args[3] + File.separator + (tntrademap.get(i).get("Assessment No")).replace("/", "_"))
								+ ".html");
				FileWriter fw = new FileWriter(file);
				Response response = getTNUrbanEpayResponse(vs, ev, tntrademap.get(i).get("Assessment No"));
				System.out.println(i+":"+(tntrademap.get(i).get("Assessment No"))+ "----" + "Response Code: " + response.getStatusCode());
				fw.write(response.getBody().asString());
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Response getTNUrbanEpayResponse(String vs, String ev, String assno) {
		return RestAssured.given()
				.config(RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
				.header("accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
				.header("accept-language", "en-US,en;q=0.9").header("cache-control", "no-cache")
				.header("cookie",
						"ASP.NET_SessionId=iiadf4lpp011gnbiji4yqbw3; ASP.NET_SessionId=iiadf4lpp011gnbiji4yqbw3")
				.header("origin", "https://tnurbanepay.tn.gov.in").header("pragma", "no-cache")
				.header("priority", "u=0, i")
				.header("referer", "https://tnurbanepay.tn.gov.in/PT_AssessmentViewsLink.aspx")
				.header("sec-ch-ua", "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
				.header("sec-ch-ua-mobile", "?0").header("sec-ch-ua-platform", "\"Windows\"")
				.header("sec-fetch-dest", "document").header("sec-fetch-mode", "navigate")
				.header("sec-fetch-site", "same-origin").header("sec-fetch-user", "?1")
				.header("upgrade-insecure-requests", "1")
				.header("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
				.formParam("__EVENTTARGET", "").formParam("__EVENTARGUMENT", "").formParam("__LASTFOCUS", "")
				.formParam("__VIEWSTATE", vs).formParam("__VIEWSTATEGENERATOR", "4D2BD4F4")
				.formParam("__EVENTVALIDATION", ev).formParam("ctl00$alert_msg", "")
				.formParam("ctl00$PageContent$txt_ConNo", assno.trim())
				.formParam("ctl00$PageContent$txt_OldConnectionNo", "")
				.formParam("ctl00$PageContent$btnSearch", "Search")
				.post("https://tnurbanepay.tn.gov.in/PT_AssessmentViewsLink.aspx");
	}
}
