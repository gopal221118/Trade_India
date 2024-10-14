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

public class Trade_main_JH {

	public static void main(String[] args) {
		ArrayList<String> responseList = new ArrayList<String>();
		HashMap<Integer, ArrayList<String>> jhliclist = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "Lic_No", "Expiry_Date" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			for (int i = 1; i < jhliclist.size(); i++) {
				System.out.println("*****************************************************");
				String response = TradeLicenseFetcher.tradeApi(((jhliclist.get(i).get(0)).trim()));
				responseList.add(response);
				String textData = Trade_jh_Exctractor.extractDataFromHtml(response);
				String tempvals[] = { (jhliclist.get(i).get(0)).trim(), textData };
				System.out.println(i + "------>" + tempvals[0] + "----" + tempvals[1]);
				writer.writeNext(tempvals);
				System.out.println("*****************************************************");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
