package com.iadv.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVWriter;

public class Ranchi_Mapping_Data {

	public static void main(String[] args) {
		try {
			String csvFile = args[2];
			CSVWriter writer = null;
			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "Application_No", "Licence_No", "Owner_Name", "Firm_Name", "Mobile_No",
						"Link1", "Link2", "Establishment_Date", "For_defined_Fee", "Mr_Mrs", "Having_receip_no",
						"Valid_Upto", "Provisional_License_No", "Firm_organization_name", "Ward_No", "Business_Address",
						"Note", "Apply_Date", "Application_No1", "in_the", "New_Expiry_Date" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			HashMap<Integer, ArrayList<String>> ranchiall = XLSXtoHashMap_Row_Col.getConvertedMap(args[0]);
			HashMap<Integer, ArrayList<String>> ranchiwithexpiry = XLSXtoHashMap_Row_Col.getConvertedMap(args[1]);
			for (int i = 1; i < ranchiall.size(); i++) {
				System.out.println(i+ "**************************************************************");
				ArrayList<String> templist = new ArrayList<String>();
				String licno = (ranchiall.get(i).get(1));
				for (int j = 1; j < ranchiwithexpiry.size(); j++) {
					if (licno.contains(ranchiwithexpiry.get(j).get(0))) {
						templist.addAll(ranchiall.get(i));
						templist.add((ranchiwithexpiry.get(j).get(1)));
					}
				}
				if (templist.size() == 21) {
					String[] temparray = templist.toArray(new String[0]);
					writer.writeNext(temparray);
				} 
				else if(templist.size() ==0)
				{
					templist.addAll(ranchiall.get(i));
					templist.add("NA");
					String[] temparray = templist.toArray(new String[0]);
					writer.writeNext(temparray);
				}
				
				System.out.println("**************************************************************");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
