package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Hyderabad_Mutation_AssesmentList {
	public static String getResponse(String vs, String ev, String circleno, String sname, int count) {
		String resp="";
		try
		{
		RestAssured.baseURI = "https://www.ghmc.gov.in";
		Response response = given().config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
				.header("accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
				.header("accept-language", "en-US,en;q=0.9").header("cache-control", "max-age=0")
				.header("content-type", "application/x-www-form-urlencoded")
				.header("cookie",
						"ASP.NET_SessionId=m4zplz4xoqwowtnysbtbwlhl; ghmc-gov-in_xc=\"cde8dd0982d6fab2\"; TS01dc4fc6=01adc37616d10869a306d2cec497f3f33e124da11a557693da2cc3c130b4631248cc24cf7f605a70fdb9bdf8bd8c2930dd0e6dcbec; ac0d03=K8aPJAxVj1HnM1zV1wWx1fMyLvSoS/C8+IXgvgq17mgntXF8VFgTkNqMhP2PwKNO+dFiNSzdnYQpCqkrGwQUgAUnEYJ4GJEHFYC0LVZacebG+2lOJi4nYJwxoZdrrSlLaPFfDPbBChMhGm5ear74Y23cB3P5ub8DCkeLBvnrqtzt80HV; ac0d03=GC9lo2frDeQ2VRmFH5egVJEXpAkut7vGib5tBw4JTdCHkakYCWlWLORgkqvJ1X7a8YYpOitFIRbCzBuQNplL6Mq09xwBBnUs25grWnjsSLjc3rdBYmtffjITGFdI3vqVuk/VqWleHzAQWmFHYiU+LXFktZ/krjz4ykp8p4KCRAPoSVxF")
				.header("origin", "null")
				.header("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"")
				.header("sec-ch-ua-mobile", "?0").header("sec-ch-ua-platform", "\"Windows\"")
				.header("sec-fetch-dest", "document").header("sec-fetch-mode", "navigate")
				.header("sec-fetch-site", "same-origin").header("sec-fetch-user", "?1")
				.header("upgrade-insecure-requests", "1")
				.header("user-agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
				.formParam("__EVENTTARGET", "ctl00$ContentPlaceHolder1$gvDetails")
				.formParam("__EVENTARGUMENT", "Page$" + count).formParam("__VIEWSTATE", vs)
				.formParam("__VIEWSTATEGENERATOR", "CDC19510").formParam("__EVENTVALIDATION", ev)
				.formParam("ctl00$ContentPlaceHolder1$ddlCircle", circleno)
				.formParam("ctl00$ContentPlaceHolder1$txtOwnerName", sname)
				.formParam("ctl00$ContentPlaceHolder1$txtdoorNo", "")
				.formParam("ctl00$ContentPlaceHolder1$txtAssessmentNum", "").when().post("/Mutation_Certificate.aspx");
		System.out.println("Response Status Code: " + response.getStatusCode());
		resp=resp+response.getBody().asString();
		System.out.println("Response Body: " + response.getBody().asString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
