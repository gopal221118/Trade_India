package com.iadv.ts;
import java.io.File;
import java.util.List;
import com.iadv.data.ReadfromTxt;

public class ROT_Step2_Main {

	public static void main(String[] args) {
		try
		{
		List<String> distlist = ReadfromTxt.readFileAsList(args[0]);
		for(String distmap : distlist)
		{
			String distncode[]=distmap.split(":");
			String distcode=distncode[0];
			List<String> munlist= ReadfromTxt.readFileAsList(args[1]+File.separator+(distncode[1]).trim()+".txt");
			System.out.println("****************************");
			for(String munvals : munlist)
			{
				String munvalsall[]=munvals.split(":");
				for(char i='a';i<='z';i++)
				{
				TLSearchDetailFetcher.getNWriteResponseVals(distcode, String.valueOf(i), munvalsall[0].trim(), args[2]+File.separator+distcode+"_"+munvalsall[1].trim()+"_"+munvalsall[0].trim()+"_"+i+".html");
				System.out.println(distcode+"---"+munvalsall[0].trim()+"----"+i);
				}
			}
		}
		System.out.println("****************************");
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
