package com.iadv.ts;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iadv.data.ReadfromTxt;

public class Rot_Dist_Reader {
	public static void main(String[] args ) {

		List<String> lines = ReadfromTxt.readFileAsList(args[0]);

		Map<String, String> districtMap = new HashMap<>();
		for (String line : lines) {

			String[] parts = line.split(":");
			if (parts.length == 2) {
				String key = parts[0].trim(); 
				String value = parts[1].trim();               
				Map<String, String> finalData = Rot_Extractor_Munci.municipalextractor(key,args[1]+File.separator+value.trim()+".txt");
				System.out.println(finalData);
			} else {
				System.out.println("Line is not in expected format: " + line);
			}
		}
		for (Map.Entry<String, String> entry : districtMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}
}
