package com.iadv.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.iadv.api.WestBengal_Api;
import com.iadv.data.XLSXtoHashMap_Row_Header;
import com.opencsv.CSVWriter;

import io.restassured.path.json.JsonPath;

public class WestBengal_main {
	public static void main(String[] args) {
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "Name", "Firm_Name", "Certificate_Number", "Issue_Date", "Expiry_Date",
						"Mobile_No", "Email_ID", "Adhar_Card_No", "licValidTill", "ain", "address", "pincode",
						"holdingNo", "Update_MobileNo" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			HashMap<Integer, HashMap<String, String>> wbtradedetails = XLSXtoHashMap_Row_Header
					.getConvertedMap(args[0]);
			int count = 0;
			HashMap<String, Integer> monthMap = new HashMap<String, Integer>();

			// Adding month names and corresponding numbers to the HashMap
			monthMap.put("Jan", 1);
			monthMap.put("Feb", 2);
			monthMap.put("Mar", 3);
			monthMap.put("Apr", 4);
			monthMap.put("May", 5);
			monthMap.put("Jun", 6);
			monthMap.put("Jul", 7);
			monthMap.put("Aug", 8);
			monthMap.put("Sep", 9);
			monthMap.put("Oct", 10);
			monthMap.put("Nov", 11);
			monthMap.put("Dec", 12);
			for (int i = 1; i < wbtradedetails.size(); i++) {
				try {
					System.out.println(i + "---------------->" + wbtradedetails.get(i).get("Firm_Name") + "-----------"
							+ wbtradedetails.get(i).get("Certificate_Number"));
					String response = WestBengal_Api.getExpiryDetails(wbtradedetails.get(i).get("Certificate_Number"),
                      	wbtradedetails.get(i).get("Firm_Name"), args[2]);
					System.out.println(response);
					try
					{
						if(response.length()!=0)
						{
							try
							{
							JsonPath jsonPath = new JsonPath(response);
							String ain = jsonPath.getString("data.output.businessData.ain");
							String licValidTill = jsonPath.getString("data.output.businessData.dtlsOfBusiness.licValidTill");
							String address1 = jsonPath.getString("data.output.businessData.addrssofBusinessTrade.address1");
							String address2 = jsonPath.getString("data.output.businessData.addrssofBusinessTrade.address2");
							String pincode = jsonPath.getString("data.output.businessData.addrssofBusinessTrade.pincode");
							String holdingNo = jsonPath.getString("data.output.businessData.addrssofBusinessTrade.holdingNo");
							String updateMobileNo = "P91"+jsonPath.getString("data.output.businessData.applicantDtls.mobileNo");
                			System.out.println("AIN: " + ain);
							System.out.println("License Valid Till: " + licValidTill);
							System.out.println("Address 1: " + address1);
							System.out.println("Address 2: " + address2);
							System.out.println("Holding No: " + holdingNo);
							System.out.println("Mobile No: " + updateMobileNo);
							String expdts[] = wbtradedetails.get(i).get("Expiry_Date").split("-");
							String expecteddate = (expdts[2] + "-" + monthMap.get(expdts[1]) + "-" + expdts[0]);
							String lvd="";
							if((licValidTill.trim()).length()==9)
							{
								String lvds[]=(licValidTill.trim()).split("-");
							lvd=lvds[0]+"-"+lvds[1]+"-0"+lvds[2];
							}
							else
							{
								lvd=lvd+(licValidTill.trim());
							}
							System.out.println((lvd) + "----------------" + (expecteddate.trim()));
							if (lvd.contains(expecteddate.trim())) {
								count = count + 1;
								String name = wbtradedetails.get(i).get("Name");
								String firmName = wbtradedetails.get(i).get("Firm_Name");
								String certificateNumber = wbtradedetails.get(i).get("Certificate_Number");
								String issueDate = wbtradedetails.get(i).get("Issue_Date");
								String expiryDate = wbtradedetails.get(i).get("Expiry_Date");
								String mobileNo = wbtradedetails.get(i).get("Mobile_No");
								String emailId = wbtradedetails.get(i).get("Email_ID");
								String adharCardNo = wbtradedetails.get(i).get("Adhar_Card_No");
								String address = address1 + address2;
								String allvals[] = { name, firmName, certificateNumber, issueDate, expiryDate, mobileNo,
										emailId, adharCardNo, licValidTill, ain, address, pincode, holdingNo, updateMobileNo };
								writer.writeNext(allvals);
								System.out.println(wbtradedetails.get(i));
							}	
							}
							catch (Exception e) {
								String allvals[] = { "Null", "Null", "Null", "Null", "Null", "Null",
										"Null", "Null", "Null", "Null", "Null", "Null", "Null", "Null" };
								writer.writeNext(allvals);
							}
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
			}
			writer.close();
		}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}
		
