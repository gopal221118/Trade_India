package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

public class WestBengal_Api {
	public static String getExpiryDetails(String certno, String orname,String token) {
		String resp = "";
		try {
			RestAssured.baseURI = "https://edistrict.wb.gov.in";

			// Perform the GET request
			Response response = RestAssured.given()
					.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
					.header("Accept", "application/json, text/plain, */*").header("Accept-Language", "en-US,en;q=0.9")
					.header("Authorization",
							"Bearer "+token)
					.header("Content-Security-Policy",
							"default-src 'self'; connect-src *; font-src *; frame-src *; img-src * data:; media-src *; object-src *; script-src * 'unsafe-inline' 'unsafe-eval'; style-src * 'unsafe-inline'; upgrade-insecure-requests;")
					.header("Cookie",
							"JSESSIONID=7CD24CBB73573CE48CC13DE881EB87F2; route=1728907948.769.39.543642|f18d920647dc7c698f680b69005a4cae; SERVER=prod-k8s-worker-node1.wb.gov.in; SRVCOOKIE=keycloak-prod-node1; DG_UNIQUE_ID=ecbb71a9-5ed3-45ba-aaa5-d965f0985ae1; JSESSIONID=7CD24CBB73573CE48CC13DE881EB87F2; JSESSIONID=C6BAFCF1893D6B7B6E41A0C73DF57D09; JSESSIONID=BB66B711DB42720E14FA7064212CCE57")
					.header("Referer", "https://edistrict.wb.gov.in/portal/edist/auto-renewal-trade-licence-udma")
					.header("Referrer-Policy", "same-origin").header("Sec-Fetch-Dest", "empty")
					.header("Sec-Fetch-Mode", "cors").header("Sec-Fetch-Site", "same-origin")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36")
					.header("X-CSRF-TOKEN", "73611969-db11-4844-b93a-d479946caf8d")
					.header("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"")
					.header("sec-ch-ua-mobile", "?0").header("sec-ch-ua-platform", "\"Windows\"")
					.queryParam("reqNo", certno).queryParam("OrgName", orname)
					.get("/digigov/common/getUdmaBusinessDtlsforAutoReneal");
			resp = resp + response.getBody().asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
