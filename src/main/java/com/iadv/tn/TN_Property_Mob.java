package com.iadv.tn;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iadv.api.TN_PropertyTax_API;
import com.iadv.api.TN_PropertyTax_API2;
import com.iadv.data.FileFetcher;
import com.iadv.data.ReadfromTxt;

public class TN_Property_Mob {

	public static void main(String[] args) {
		try {
			HashMap<String,String> mcodeMap = new HashMap<String,String>();
			List<String> mcodennames=ReadfromTxt.readFileAsList(args[1]);
			for(String mcodenname : mcodennames)
			{
				String mcodes[] = mcodenname.split(":");
				mcodeMap.put(mcodes[0].trim(),mcodes[1].trim());
			}
			System.out.println(mcodeMap);
			List<String> mcfpaths = FileFetcher.getAllFilePaths(args[0]);
			for (String mcfpath : mcfpaths) {
				System.out.println(mcfpath);
				if (!(mcfpath.contains("Select"))) {
					ArrayList<String> tempVals = new ArrayList<String>();
					String filecontents = ReadfromTxt.readFileAsString(mcfpath);
					String fileparts[] = filecontents.split("&");
					for (String filepart : fileparts) {
						if (filepart.contains("__EVENTTARGET=") || filepart.contains("__VIEWSTATE=")
								|| filepart.contains("__EVENTVALIDATION=")
								|| filepart.contains("ctl00%24PageContent%24drporg")) {
							String filetgs[] = filepart.split("=");
							tempVals.add(filetgs[1].trim());
						}
					}
					tempVals.add(mcodeMap.get(tempVals.get(tempVals.size()-1)));
					System.out.println(tempVals);
					for(String tempVal : tempVals)
					{
						System.out.println(tempVal);
					}
					String wardresp=TN_PropertyTax_API.getAPIResponse(filecontents);
					File file=new File("C:\\TN_contact_project_files_step2\\inputfile\\test.html");
					FileWriter fw=new FileWriter(file);
					fw.write(wardresp);
					fw.close();
					ArrayList<String> wardlist = TN_PropertyTax_API.extractWardList(wardresp);
					for(String wardno : wardlist)
					{
					String htmlresponse =TN_PropertyTax_API2.getAPIResponse(wardno,tempVals);
					System.out.println(htmlresponse);
					}
					}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
