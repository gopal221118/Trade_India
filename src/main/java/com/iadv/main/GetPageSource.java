package com.iadv.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.iadv.api.Basic_FSSAI_Download;
import com.iadv.api.FoSCOSRefAPI;
import com.iadv.api.State_FSSAI_Download;
import com.iadv.crypto.DecryptAES;
import com.iadv.data.XLSXtoHashMap_Row_Col;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetPageSource {

	public static void main(String[] args) {
        try {
        	
            // The target URL
        	 HashMap<Integer, ArrayList<String>> licList= XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
        	 for(int i=1;i<licList.size();i++)
        	 {
        	 getSources(args[1],((licList.get(i).get(0)).replace("Lic. No.", "")).trim(),args[2],args[3]);
        	 }
           
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	public static void getSources(String downloadpath, String licno, String token, String authid)
	{
		try
		{
			//String url="/tracker/dist/lnchk.php?ln="+licno;
			String jsonString="gkVvK7x6LPSMW8361kN+tFw5DRyC329CVYG/LGHUbsb9KtvRoGB6UcWK70z8iIeF24UByop39XIBzt2gcy4G5SCiOZgZzGUBQa6kt7UOA+Uev+t+of4gSuxJCLyY95bJ/CCtKw8prGPsKslCmkY5iimX+dQenKKtMc+rqIJWFSbCyDvYDUQOmK5Rn9Q1rCGKkaZxSk9MVi+K4ng35XIBW5v0cWlqV6LWUsE0ssJidshv/rg3AsekrpD6Zz86Xaw0WqUXPMA+/7WpRRRhJ6vQL0O4N7uLoAnNYibnT6Y2RC80bmD8Q4BFzmC7XyuHLvr9MDDzA6APcnhmjZHh7Msy4HNN/YDcPrU+Yn0NVlDTfsVmwwcAFdPRuMD0BExiS2Q/Y0gPv2Qx5HUTY3skk8gU+JVx+tmHtyTljfb86kYucC2REmWgqsbl0moDi++HzBu/1eEcDLbUEIL06zYs823E2HDZINDNzI4TOZK1hYZWvOGV9W74EnFaqjJF7VKpZuqwjVKFbIpuTRmuiMohIpAdAxrFJ6Y8MowKyzpnlvqwahXLeTqmbK+yBsFr9N1QuFKmsftC9//wg2S33QwJR0n2lSsOsKM+NgeWtHL8crqC5XBxR6JPx6MNUo5ANUC46Sf/Q1+c08SsFByNzfqNwrWcysIKcNhs1K9SENi/h7denerM/aHJ+LvUdHjFBx+VBdA4M2gnMmVy645F6EzHG8GlJRM7/sDN1NllXFOHBuaqJ5CPPXpWHW930M07a4GlzLjRAEJ1gfKWs4cM2imzMHCDilrm0Tq0HxLQ0K2QNR8McGH3Pni1/M137Hc85nb5c5m/";
			
	            String refid = (getReffID(jsonString));	
	            if(licno.startsWith("1"))
	            {
	            	State_FSSAI_Download.downloadLicense(downloadpath, licno, refid, token, authid)	;
	            }
	            else if(licno.startsWith("2"))
	            {
	            	Basic_FSSAI_Download.downloadLicense(downloadpath, licno, refid, token, authid)	;
	            }
	            else
	            {
	            System.out.println("Incorrect License");
	            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getReffID(String jsonString)
	{
		System.out.println(jsonString);
		String refidx="";
		// Parse the JSON string
        try {
            JSONObject jsonObject = new JSONObject(jsonString.trim());

            // Get the paginationListRecords array
            JSONArray paginationListRecords = jsonObject.getJSONArray("paginationListRecords");

            // Extract the refid from the first element of the array
            if (paginationListRecords.length() > 0) {
                JSONObject record = paginationListRecords.getJSONObject(0);
                int refid = record.getInt("refid");
                System.out.println("Ref ID: " + refid);
                refidx=refidx+refid;
            } else {
                System.out.println("No records found.");
            }
        } catch (org.json.JSONException e) {
            System.err.println("Invalid JSON: " + e.getMessage());
        }
		return refidx;
    }
}