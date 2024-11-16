package com.iadv.ts;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.iadv.api.Hyderabad_Mutation_AssesmentList;
import com.iadv.data.ReadfromTxt;
import com.iadv.extractor.HTML_Extractor_Hyd;
import com.opencsv.CSVWriter;

public class Hyderabad_Property_Tax {

	public static void main(String[] args) {

		try {
			try {
				String csvFile = args[3];
				CSVWriter writer = null;

				try {
					writer = new CSVWriter(new FileWriter(csvFile));
					writer.writeNext(new String[] { "Assessment No.", "Owner", "Door NO", "MObile NO" });
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				String vs = ReadfromTxt.readFileAsString(args[0]);
				String ev = ReadfromTxt.readFileAsString(args[1]);
				List<String> ccs = ReadfromTxt.readFileAsList(args[2]);

				for (String cc : ccs) {
					for (char i = 'a'; i <= 'z'; i++) {
						for(int j=2;j<10000;j++)
						{
							System.out.println(cc+"------"+i+"------"+j);
						String html = Hyderabad_Mutation_AssesmentList.getResponse(vs, ev, cc, String.valueOf(i),
								j);
						if ((html.contains("Error 500 - Internal Server Error"))) {
							break;
						} else {
							HTML_Extractor_Hyd.extractnWrite(html, writer);
						}
						}
					}
				}
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
