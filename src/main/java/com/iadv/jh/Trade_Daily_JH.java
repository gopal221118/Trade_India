package com.iadv.jh;

import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.iadv.jh.TradeLicenseFetcher;
import com.iadv.jh.Trade_Reader_Jh;
import com.iadv.jh.Trade_jh_Exctractor;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Trade_Daily_JH {

	public static void main(String[] args) {
		ArrayList<String> responseList = new ArrayList<String>();
		HashMap<Integer, ArrayList<String>> jhliclist = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "Application_No","Licence_No","Owner_Name","Firm_Name","Mobile_No","Link1","Link2","Establishment_Date","For_defined_Fee","Mr_Mrs","Having_receip_no","Valid_Upto","Provisional_License_No","Firm_organization_name","Ward_No","Business_Address","Note","Apply_Date","Application_No1","in_the","New_Expiry_Date","Real_Expiry_Date"});
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			for (int i = 1; i < jhliclist.size(); i++) {
				System.out.println("*****************************************************");
				ArrayList<String> templist = new ArrayList<String>();
				templist.addAll(jhliclist.get(i));
				String licno = (((jhliclist.get(i).get(1)).trim()).replace("Val:", "")).trim();
				if (licno.length() > 1) {
					String response = TradeLicenseFetcher.tradeApi(licno);
					responseList.add(response);
					String textData = Trade_jh_Exctractor.extractDataFromHtml(response);
					if(textData.equalsIgnoreCase(args[2]))
					{
					templist.add(textData);
					System.out.println(templist);
					String[] temparray = templist.toArray(new String[0]);
					writer.writeNext(temparray);
					}
					System.out.println("*****************************************************");
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
