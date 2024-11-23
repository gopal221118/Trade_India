package com.iadv.api;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.iadv.data.ReadfromTxt;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class ROK_Trade_Step1 {

	public static void main(String[] args) {
		try {
			List<String> lists = ReadfromTxt.readFileAsList(args[0]);
			File file = new File("C:\\CIN\\KA\\Input\\AppSamples.txt");
			FileWriter fw = new FileWriter(file);
			for (String ulb : lists) {
				String ulbs[] = ulb.split(":");
				for (int i = 5000; i < 9999; i++) {
					String gscno = "";
					if((ulbs[0].trim()).length()==1)
					{
					gscno = "L200" + ulbs[0].trim() + "000200" + i;
					}
					else if((ulbs[0].trim()).length()==2)
					{
					gscno = "L20" + ulbs[0].trim() + "000200" + i;
					} 
					else if((ulbs[0].trim()).length()==3)
					{
					gscno = "L2" + ulbs[0].trim() + "000200" + i;
					}
					System.out.println(gscno);
					Response response = getStatusResponse(gscno);
					String tempresp = response.getBody().asString();
					if (tempresp.contains("Invalid Application No. / GSC No.")) {
					} else {
						System.out.println("Status Code: " + response.getStatusCode());
						String appno = getValueFromHtml(tempresp);
                        fw.write(ulbs[0].trim()+":"+ulbs[1].trim()+gscno+":"+appno+"\r\n");
                        break;
					}
				}
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Response getStatusResponse(String gscno) {

		return RestAssured.given()
				.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
				.header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
				.header("Accept-Language", "en-US,en;q=0.9").header("Cache-Control", "no-cache")
				.header("Connection", "keep-alive").header("Content-Type", "application/x-www-form-urlencoded")
				.header("Cookie",
						"JSESSIONID=87A063A2F5EC1E30DBB8227B755E0419.worker42; _ga=GA1.3.1896878655.1731487215; _gid=GA1.3.746590767.1731487215; _ga_28WJTFNQDL=GS1.3.1731492671.2.0.1731492671.0.0.0; JSESSIONID=C8DA504BBAFC47E6A861B2B76E2CE859.worker42")
				.header("Origin", "http://www.mrc.gov.in").header("Pragma", "no-cache")
				.header("Referer", "http://www.mrc.gov.in/TradeLicense/checkStatus")
				.header("Upgrade-Insecure-Requests", "1")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
				.formParam("searchoption", "gscNo").formParam("applNum", "").formParam("gscNo", gscno)
				.post("http://www.mrc.gov.in/TradeLicense/checkStatus");
	}

	public static String getValueFromHtml(String html) {
		Document doc = Jsoup.parse(html);
		Element element = doc.selectFirst("#currStatus > table > tbody > tr:nth-child(2) > td:nth-child(2)");
		return element != null ? element.text() : "Element not found";
	}

}
