package com.iadv.api;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TN_Trade_API {
	public static String getAPIResponse(String cname,int pageno) {
		String responsex = "";
		try {
			RestAssured.baseURI = "https://ctd.tn.gov.in";
			Response response = given()
					.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,/;q=0.8")
					.header("Accept-Language", "en-US,en;q=0.9").header("Cache-Control", "max-age=0")
					.header("Connection", "keep-alive").header("Content-Type", "application/x-www-form-urlencoded")
					.header("Cookie",
							"JSESSIONID=l8UDFTE0erQVBRZO0kqfmzCF; COOKIE_SUPPORT=true; GUEST_LANGUAGE_ID=en_US; srvWeb80=Webserver249; LFR_SESSION_STATE_10161=1725527949886; JSESSIONID=UZoqmoq0zW0gMPBUE8kyQz0G")
					.header("Origin", "https://ctd.tn.gov.in")
					.header("Referer", "https://ctd.tn.gov.in/Portal//cnclDlrList.htm?actionCode=getDealerDtls")
					.header("Sec-Fetch-Dest", "iframe").header("Sec-Fetch-Mode", "navigate")
					.header("Sec-Fetch-Site", "same-origin").header("Sec-Fetch-User", "?1").header("Sec-GPC", "1")
					.header("Upgrade-Insecure-Requests", "1")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
					.header("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Brave\";v=\"128\"")
					.header("sec-ch-ua-mobile", "?0").header("sec-ch-ua-platform", "\"Windows\"")
					.formParam("validationFormName", "cnclDlrListForm").formParam("txfmLangId", "0")
					.formParam("source", "Pagination").formParam("commodity", "").formParam("currentPage", "1")
					.formParam("PageSize", "5").formParam("flag", "CnclList").formParam("operation", "dealer")
					.formParam("isNavigateYesNo", "N").formParam("fistPageFlag", "").formParam("dealerList", "")
					.formParam("viewPage", "lifeRay").formParam("currentDisplayPage", pageno)
					.formParam("totalRecord", "305").formParam("recordPerPage", "5")
					.formParam("functionCallPagination", "searchDealer").formParam("startIndex", "6")
					.formParam("searchBy", "F").formParam("cnclDlrListVO.rcno", "")
					.formParam("cnclDlrListVO.masterPan", "").formParam("cnclDlrListVO.firmname", cname)
					.formParam("cnclDlrListVO.gstin", "").formParam("cnclDlrListVO.strZone", "")
					.formParam("cnclDlrListVO.taxtype", "").formParam("cnclDlrListVO.dealerStatus", "REGD")
					.formParam("captcahPostText", "").when().post("/Portal/cnclDlrList.htm?actionCode=getDealerDtls")
					.then().extract().response();
			responsex = response + response.getBody().asString();
			System.out.println("Response Body: " + response.getBody().asString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responsex;
	}
		}