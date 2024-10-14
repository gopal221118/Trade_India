package com.iadv.tn;

import java.io.File;
import java.io.FileWriter;
import com.iadv.api.TN_PropertyTax_API;
import com.iadv.data.ReadfromTxt;

public class TN_PropertyTax_Fetch {

	public static void main(String args[])
	{
		try
		{
			File file=new File(args[2]);
			FileWriter fw=new FileWriter(file);
			String vs = ReadfromTxt.readFileAsString(args[0]);
			String ev=  ReadfromTxt.readFileAsString(args[1]);
			String resp=TN_PropertyTax_API.getAPIResponse(vs, ev);
			fw.write(resp);
			System.out.println(resp);
			fw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
