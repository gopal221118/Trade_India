package com.iadv.tn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.iadv.api.TN_Trade_API;
import com.iadv.data.XLSXtoHashMap_Row_Col;
import com.iadv.extractor.TN_Trade_HTML_Extractor;
import com.opencsv.CSVWriter;

public class TN_Trade_Main {

	public static void main(String[] args) {
		try
		{
			 String csvFile = args[1];
		        CSVWriter writer = null;

		        try {
		            writer = new CSVWriter(new FileWriter(csvFile));
		              writer.writeNext(new String[]{
		                    "Sr. No.", "TIN", "GSTIN/PID", "Firm Name", "Address", 
		                    "Assessment Circle", "Tax Type", "Return Form Type", 
		                    "RC Effective Date", "Status", "Cancellation Date", "Type Of Dealer"
		                });
		        } catch (IOException e) {
		            e.printStackTrace();
		            return; 
		        }

			String pageresponse;
			HashMap<Integer, ArrayList<String>> namesMap=XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
			for(int i=1;i<namesMap.size();i++)
			{
				int count=1;
				pageresponse= TN_Trade_API.getAPIResponse(namesMap.get(i).get(0), count);
				while(!(pageresponse.contains("No Records Found")))
				{
					HashMap<Integer, HashMap<String, String>> csvMap=TN_Trade_HTML_Extractor.extractHTMLResponse(pageresponse,writer);	
					System.out.println(csvMap);
					count=count+1;
					pageresponse= TN_Trade_API.getAPIResponse(namesMap.get(i).get(0), count);
				}
			}
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
