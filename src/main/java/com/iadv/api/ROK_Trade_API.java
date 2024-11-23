package com.iadv.api;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.iadv.data.ReadfromTxt;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class ROK_Trade_API {

	public static void main(String args[]) {
		try {
			List<String> lists = ReadfromTxt.readFileAsList("C:\\CIN\\KA\\Input\\DistCodes.txt");
			File file = new File("C:\\CIN\\KA\\Input\\ULBCodes.txt");
			FileWriter fw = new FileWriter(file);
			for (String list : lists) {
				String distNCode[] = list.split(":");
				System.out.println(distNCode[0].trim() + "--------------------" + distNCode[1].trim());
				ArrayList<String> ulbcodes = getULBCodes(distNCode[0].trim());
				for (String ulbcode : ulbcodes) {
					fw.write(ulbcode+"\r\n");
				}
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getULBCodes(String dcode) {
		ArrayList<String> ulblist = new ArrayList<String>();
		try {
			Response response = RestAssured.given()
					.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
					.header("Accept", "*/*").header("Accept-Language", "en-US,en;q=0.9")
					.header("Connection", "keep-alive").header("Content-Type", "application/x-www-form-urlencoded")
					.header("Cookie",
							"JSESSIONID=B9780D00E04C5F516D1B7F24A4852407.worker42; _ga=GA1.3.1746916687.1731419816; _gid=GA1.3.198259675.1731419816; _ga_28WJTFNQDL=GS1.3.1731419815.1.0.1731419815.0.0.0; JSESSIONID=C8DA504BBAFC47E6A861B2B76E2CE859.worker42")
					.header("Referer", "http://www.mrc.gov.in/TradeLicense/loadCreateApplicationForCitizen")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
					.header("X-Requested-With", "XMLHttpRequest")
					.get("http://www.mrc.gov.in/TradeLicense/getUlbList?districtId=" + dcode);
			System.out.println("Status Code: " + response.getStatusCode());
			JSONObject jsonObject = new JSONObject(response.getBody().asString());
			JSONObject resultObject = jsonObject.getJSONObject("result");
			for (String key : resultObject.keySet()) {
				String entry = key + ": " + resultObject.getString(key);
				ulblist.add(entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ulblist;
	}
}
