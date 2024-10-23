package com.iadv.ts;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.iadv.data.FileFetcher;
import com.opencsv.CSVWriter;

public class Hyderabad_PT_Main {

	public static void main(String[] args) {
		try {
			String csvFile = args[0];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] { "PTIN_No", "Name_Address", "Masked_Mobile_Number" });
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			List<String> filepaths = FileFetcher.getAllFilePaths(args[1]);
			for(String filepath : filepaths)
			{
				System.out.println("****************************************************");
				System.out.println(filepath);
				Hyd_PT_No_extractor.extractData(filepath,writer);
				System.out.println("****************************************************");
		}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
