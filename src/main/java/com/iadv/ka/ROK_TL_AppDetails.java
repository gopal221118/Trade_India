package com.iadv.ka;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebElement;

import com.iadv.data.ReadfromTxt;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class ROK_TL_AppDetails {

	public static void main(String[] args) {

		List<String> lists = ReadfromTxt.readFileAsList(args[0]);
		for (String ulb : lists) {
			try
			{
			String ulbs[] = ulb.split(":");
			String limit = "";
			Response response = getDashboardResponse(ulbs[0].trim(), 1);
			System.out.println("Response status code: " + response.getStatusCode());
			File file=new File(args[1]+File.separator+ulbs[0].trim()+"_"+1+".html");
			FileWriter fw = new FileWriter(file);
			fw.write(response.getBody().asString());
			fw.close();
			Document document = Jsoup.parse(response.getBody().asString());
			Element lastLink = document.selectFirst(
					"body > table > tbody > tr:nth-child(2) > td:nth-child(2) > span.pagelinks > a:nth-child(10)");
			if (lastLink != null) {
				String hrefValue = (lastLink.attr("href")).substring(0,(lastLink.attr("href")).lastIndexOf("&"));
				limit = (hrefValue.substring(hrefValue.lastIndexOf("=")+1,hrefValue.length()).trim());
				System.out.println("Href value of the 'Last' link: " + hrefValue);
			} else {
				System.out.println("Element not found with the specified CSS selector.");
			}
			System.out.println(limit);
			for(int i=2;i<=Integer.parseInt(limit);i++)
			{
				try
				{
				Response responsex = getDashboardResponse(ulbs[0].trim(), i);	
				File filex=new File(args[1]+File.separator+ulbs[0].trim()+"_"+i+".html");
				FileWriter fwx = new FileWriter(filex);
				fwx.write(responsex.getBody().asString());
				fwx.close();
				System.out.println("Path:"+args[1]+File.separator+ulbs[0].trim()+"_"+i+".html"+"-----"+"Response status code: " + responsex.getStatusCode());

				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			catch (Exception e) {
				e.printStackTrace();
			}
			}
	}

	public static Response getDashboardResponse(String ulb, int pgno) {
		return RestAssured.given().config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
				.header("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
				.header("Accept-Language", "en-US,en;q=0.9").header("Cache-Control", "max-age=0")
				.header("Connection", "keep-alive")
				.header("Cookie",
						"JSESSIONID=8464B60975D2F45179C57939371C7884.worker42; _ga=GA1.3.1746916687.1731419816; _gid=GA1.3.198259675.1731419816; _gat=1; _ga_28WJTFNQDL=GS1.3.1731577037.3.1.1731577934.0.0.0")
				.header("Upgrade-Insecure-Requests", "1")
				.header("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
				.get("http://www.mrc.gov.in/TradeLicense/displayDashBoardAdmin1?ulbIds=" + ulb + "&d-16586-p=" + pgno
						+ "&type=4");
	}
}
