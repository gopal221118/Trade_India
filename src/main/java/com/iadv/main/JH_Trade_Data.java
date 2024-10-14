package com.iadv.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.iadv.data.FileListfromFolder;
import com.iadv.data.XLSXtoHashMap_Row_Header;
import com.iadv.extractor.HtmlFileReader;
import com.iadv.extractor.JH_HTML_Extractor;
import com.opencsv.CSVWriter;

public class JH_Trade_Data {
	public static void main(String[] args) {

		try {
			try {
    			String csvFile = args[2];
    	        CSVWriter writer = null;
    	        File file=new File(args[3]);
    	        FileWriter fw=new FileWriter(file);
    	        try {
    	            writer = new CSVWriter(new FileWriter(csvFile));
    	            writer.writeNext(new String[]{
    	                    "Application_No", "Licence_No", "Owner_Name", "Firm_Name", "Mobile_No", 
    	                    "Link1", "Link2", "Establishment Date", "For defined Fee", "Mr/Mrs",
    	                    "Having receipt no", "Valid Upto", "For More Details Please Visit",
    	                    "Provisional License No", "Firm / organization name", "Ward No.",
    	                    "Business Address", "Note", "Apply Date", "Application No", "in the"
    	                });
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	            return; 
    	        }
			HashMap<Integer, HashMap<String, String>> jhSheetvals = XLSXtoHashMap_Row_Header.getConvertedMap(args[0]);
			ArrayList<String> htmlpaths = FileListfromFolder.getfileList(args[1]);
			System.out.println(htmlpaths);
			System.out.println(jhSheetvals);
			for (int i = 1; i < jhSheetvals.size(); i++) {
				HashMap<String, String> jhValMap = jhSheetvals.get(i);
				String appno[] = (jhValMap.get("Application_No")).split(":");
				String realfilepath = "";
				for (int j = 0; j < htmlpaths.size(); j++) {
					if (htmlpaths.get(j).contains((appno[1]).trim())) {
						realfilepath = realfilepath + htmlpaths.get(j);
					}
					else
					{
						fw.write((appno[1].trim())+"\r\n");
					}
				}
			String html=HtmlFileReader.getFileContents(realfilepath);
			HashMap<String,String> allvals = JH_HTML_Extractor.extractHTML(html);
			System.out.println(allvals);
			String completeVals[]= {jhValMap.get("Ward_No"),
					jhValMap.get("Application_No"),
					jhValMap.get("Licence_No"),
					jhValMap.get("Owner_Name"),
					jhValMap.get("Firm_Name"),
					jhValMap.get("Mobile_No"),
					jhValMap.get("Link1"),
					jhValMap.get("Link2"),
					allvals.get("Establishment Date"),
		            allvals.get("For defined Fee"),
		            allvals.get("Mr/Mrs"),
		            allvals.get("Having receipt no"),
		            allvals.get("Valid Upto"),
		            allvals.get("For More Details Please Visit"),
		            allvals.get("Provisional License No"),
		            allvals.get("Firm / organization name"),
		            allvals.get("Ward No."),
		            allvals.get("Business Address"),
		            allvals.get("Note"),
		            allvals.get("Apply Date"),
		            allvals.get("Application No"),
		            allvals.get("in the")};
			writer.writeNext(completeVals);

			}
			writer.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
}

	
