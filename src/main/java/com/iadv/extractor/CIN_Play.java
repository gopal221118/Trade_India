package com.iadv.extractor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.iadv.data.FileFetcher;
import com.iadv.data.XLSXtoHashMap_Sheets_Col;

public class CIN_Play {

	public static void main(String[] args) {
		try
		{
			File file=new File(args[1]);
			FileWriter fw=new FileWriter(file);
			List<String> allfiles = FileFetcher.getAllFilePaths(args[0]);
			for(String fp : allfiles)
			{
				try
				{
			int sheetcount = XLSXtoHashMap_Sheets_Col.getNumberOfSheets(fp);
			System.out.println(sheetcount);
			for(int i=0;i<sheetcount;i++)
			{
				try
				{
				HashMap<Integer, ArrayList<String>> tempMap= XLSXtoHashMap_Sheets_Col.getConvertedMap(fp, i);
				for(int j=0;j<tempMap.size();j++)
				{
					try
					{
					ArrayList<String> temparray = tempMap.get(j);
					System.out.println(temparray);
					for(int k=0;k<temparray.size();k++)
					{
						try
						{
						System.out.println((temparray.get(k).trim()));
						if((temparray.get(k).trim()).startsWith("U") && ((temparray.get(k).trim()).length())==21)
						{
							System.out.println((temparray.get(k).trim()));
							fw.write((temparray.get(k).trim())+"\r\n");
						}
						else if((temparray.get(k).trim()).startsWith("L") && ((temparray.get(k).trim()).length())==21)
						{
							System.out.println((temparray.get(k).trim()));
							fw.write((temparray.get(k).trim())+"\r\n");
						}
						else if((temparray.get(k).trim()).startsWith("A") && (temparray.get(k).trim()).contains("-") && ((temparray.get(k).trim()).length())==8)
						{
							System.out.println((temparray.get(k).trim()));
							fw.write((temparray.get(k).trim())+"\r\n");
						}
						else if((temparray.get(k).trim()).startsWith("F") && ((temparray.get(k).trim()).length())==6)
						{
							System.out.println((temparray.get(k).trim()));
							fw.write((temparray.get(k).trim())+"\r\n");
						}
					}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			}
			fw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
