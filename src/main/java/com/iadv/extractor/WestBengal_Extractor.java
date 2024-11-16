package com.iadv.extractor;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WestBengal_Extractor {
    public static void main(String[] args) {
        // Base URI
        RestAssured.baseURI = "https://edistrict.wb.gov.in";
String oname="SOVA DIGITAL PHOTO COLOUR LAB";
        // Perform the GET request and get the response
        Response response = RestAssured.given()
        		.config(RestAssured.config.sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()))
            .header("Accept", "application/json, text/plain, */*")
            .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ4NDdGWkcxa2I4TlhzWERtdm16WGoxTHJPZ2RVNUNSdnhkeDhYVjk4bTU0In0.eyJleHAiOjE3Mjg5ODc4ODcsImlhdCI6MTcyODk4NjY4NywiYXV0aF90aW1lIjoxNzI4OTgzNjMzLCJqdGkiOiJlOWQyZWJjMy00NTVlLTRiZGEtOTY3ZS0zOWYyZDY0OGUwOTIiLCJpc3MiOiJodHRwczovL2VkaXN0cmljdC53Yi5nb3YuaW4vc3NvL3JlYWxtcy9FZGlzdHJpY3QiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiODcwOTdlYzktOTMxYi00MDA1LTg4YmYtYTA0MTAwMzNmNGUyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZWRpc3RyaWN0LXBvcnRhbCIsIm5vbmNlIjoiUmsxNlMxOU5Ua3hrVldWTVozcDFWM2hoWkZKb2FEZFJaSEZ4YVVsLWJFVk9XUzFwYjBoUVJEVk9UVmwtIiwic2Vzc2lvbl9zdGF0ZSI6ImMzYmMyMzg0LTJjZGYtNGJiOS04NmJhLTVhNWZlNWNiY2FkMiIsImFjciI6IjAiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtZWRpc3RyaWN0Il19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIG9wZW5pZCIsInNpZCI6ImMzYmMyMzg0LTJjZGYtNGJiOS04NmJhLTVhNWZlNWNiY2FkMiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlByaXRhbSBLdW1hciIsInByZWZlcnJlZF91c2VybmFtZSI6InByaXRhbTMzMjEyMzQ1Njc4OSIsImdpdmVuX25hbWUiOiJQcml0YW0iLCJmYW1pbHlfbmFtZSI6Ikt1bWFyIiwiZW1haWwiOiJwcml0YW0xc2luZ2g3ODc4QGdtYWlsLmNvbSJ9.ambfIRBhWMKgYfGtH52WJ7qcvAcJ55fnhs4K-4HzWN860ePJGUShseCbXZJv0eggaLDGUTeeoiY6q3tmj7zR7ciPnTtsNhAYKzTngcPSPxYkVZhN9kSWL5yWmfhCU2QPGvSgnsCSA7bOhchOeCc2i0KtGc9RuaLemvB3bNI0Y8E973dnHJ7oAbvpHjzkasDpZz0Bz7qQFMOtO5zR9m_f0LHkl27yKtB4R4_RHd85zp1SaQYS-the9xj72am4bK0Vz8p8HVFeEmUBGLEDFMxzxjektbPdWE33fh9aE3aXWazFnHnmtvDNR0A28QxFn8hGpYk01uRa25j3nwKv6Wt5cw")
            .get("/digigov/common/getUdmaBusinessDtlsforAutoReneal?reqNo=0917P1217322393162&OrgName="+oname.replace(" ", "%20"));

        // Convert the response body to a String
        String jsonResponse = response.getBody().asString();

        // Parse JSON using org.json
        JSONObject jsonObject = new JSONObject(jsonResponse);

        // Create a list to store the 'licValidTill' values
        List<String> licValidTillList = new ArrayList<>();

        // Extract the 'licValidTill' value and add it to the list
        if (jsonObject.has("licValidTill")) {
            String licValidTill = jsonObject.getString("licValidTill");
            licValidTillList.add(licValidTill);
        }

        // Print the list
        System.out.println("LicValidTill List: " + licValidTillList);
    }
}
