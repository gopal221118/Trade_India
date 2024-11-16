package com.iadv.ts;


import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Rot_Extractor_Munci {
	public static Map<String, String> municipalextractor(String distcode,String distpath) {
		Map<String, String> ulbDataMap = new HashMap<String, String>();
		File file=new File(distpath);
		try {
			FileWriter fw= new FileWriter(file);
		
		try {

			 Response response = RestAssured
			            .given()
			            .config(RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation())) // Relaxed SSL validation
			            .header("accept", "application/json, text/javascript, */*; q=0.01")
			            .header("content-type", "application/json; charset=UTF-8")
			            .header("cookie", "ASP.NET_SessionId=rm1ks5rvpgfu4jxsrtw0taw3; ts-cgg-gov-1xc=\"bd1ff5e79e613e99\"; __RequestVerificationToken_L0NETUFfQVJCUw2=xRZima6XqXYmErKRHkU3hxHy4e2q2vFyxvbL2Kjb8LjsXM1Xpmi7wDpPt9Shtqvqo6dqhAXId_I_mblIZbb6prkqjj0cHbWsJsd8AoFPzDY1; CitizenCookie=CITIZEN=1; TS01dc4fc6=01b9414e33c9ad158837801ba6bf76b8e58a498d4569b77a484d2772d8e744d7822d1b1e365520a0745bcea4566b998a7339c42e2d; e0d503=gtIYBEIC3r2nf50kSKbYDNo8D1qTwLP+wxiTZMWE3M0lgDARBY64vntneUXLpfSmsmV5EzfILFX/ajbASe3RbYrVx//pI6BFj4nDYJgUsZ6FjklyDqmYVEkIY8aQK6VH+tPpGg+w3DBvpDjfKcQ4lp6vzX+ePip6RJq7vMb7WjL7eLFq; e0d503=VRIBo9FhVo16yDYpXR0K5i9BG/0v59UfaBN65Un2cMXXV5C2U1dIX4Biy6FZ6QYNE2KTPW9y9F1oiVoCpXVECzDHNAW7REsy4mjR0NIVJIwK1KrUgtaFB/Nv4yL75jLwb5SGT8ivvNn/vwfFf8IVL/jlgPHaZPdiZ0uQLeIriyu5J55H")
			            .header("origin", "https://cdma.cgg.gov.in")
			            .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")
			            .header("x-requested-with", "XMLHttpRequest")
			            .body("{\"DistrictID\":\"" + distcode + "\" }") // Proper JSON format with escaped quotes
        	            .when()
			            .post("https://cdma.cgg.gov.in/CDMA_ARBS/CDMA_PG/GetULBData") // Use POST request
			            .then()
			            .extract()
			            .response();

		

		if (response.getStatusCode() == 200) {
			System.out.println("Request was successful!");

			String responseBody = response.getBody().asString();
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				JsonNode rootNode = objectMapper.readTree(responseBody);
				Iterator<JsonNode> elements = rootNode.elements();

				while (elements.hasNext()) {
					JsonNode ulb = elements.next();
					String ulbId = ulb.path("I_ULBOBJID").asText();
					String ulbName = ulb.path("VC_ULBNAME").asText();
					ulbDataMap.put(ulbId, ulbName);
					fw.write(ulbId + " : " + ulbName+"\r\n");
				}

				ulbDataMap.forEach((key, value) -> System.out.println(key + " : " + value));
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Request failed with status code: " + response.getStatusCode());
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ulbDataMap;
	}
}
