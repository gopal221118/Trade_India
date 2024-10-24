package com.iadv.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.iadv.data.XLSXtoHashMap_Row_Header;

public class Trade_JH_Provisional_Data {

	public static void main(String[] args) {
		try {
			HashMap<Integer, HashMap<String, String>> ranchiMap = XLSXtoHashMap_Row_Header.getConvertedMap(args[0]);
			System.out.println(ranchiMap.size());
			String range[]=args[2].split("-");
			for (int i = Integer.parseInt(range[0].trim()); i < Integer.parseInt(range[1].trim()); i++) {
				System.out.println("********************************************");
				System.out.println(i + "------>" + ranchiMap.get(i));
				System.out.println(ranchiMap.get(i).get("Link2"));
				try {
					Document document = Jsoup.connect((ranchiMap.get(i).get("Link2")).trim()).get();
					String htmlContent = document.outerHtml();
					String appno[] = ranchiMap.get(i).get("Application_No").split(":");
					FileWriter fileWriter = new FileWriter(
							args[1] + File.separator + "_" + appno[1].trim() + "_" + i + ".html");
					fileWriter.write(htmlContent);
					fileWriter.close();
					System.out.println("Page source has been written for " + i + " file");
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("********************************************");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
