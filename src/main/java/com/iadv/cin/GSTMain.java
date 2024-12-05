package com.iadv.cin;


import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.iadv.data.ReadfromTxt;

public class GSTMain {
	
	public static void main(String args[])
	{
		try
		{
			List<String> gstall = ReadfromTxt.readFileAsList(args[0]);
			System.out.println(gstall.size());
			File file=new File(args[1]);
			FileWriter fw=new FileWriter(file);
			for(String gstcol : gstall)
			{
			
					if((gstcol.trim()).length()==15 && !((gstcol.trim()).contains(" ")))
					{
						fw.write((gstcol.trim())+"\r\n");
						System.out.println(gstcol.trim());
					}
				
			}
			fw.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}

}
