package com.iadv.extractor;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JH_HTML_Extractor {
	
	public static HashMap<String,String> extractHTML(String html)
	{
		HashMap<String,String> jhMap=new HashMap<String,String>();
		try
		{
			Document doc = Jsoup.parse(html);
			Elements spanElements = doc.select("table tbody tr td");
			ArrayList<String> textValues = new ArrayList<>();
			for (Element span : spanElements) {
				System.out.println(span.text());
				if (span.text().contains(":")) {
					textValues.add(span.text());
				}
			}
			System.out.println("Extracted Text Values: " + textValues);
			for(int i=0;i<textValues.size();i++)
			{
				String tempval[]=textValues.get(i).split(":");
				try
				{
					if(tempval.length==2)
					{
				    jhMap.put(tempval[0].trim(), tempval[1].trim());
					}
					else if(tempval.length==1)
					{
						jhMap.put(tempval[0].trim(), "Empty");	
					}
					else
					{
						jhMap.put(i+"No_Val", "Empty");		
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
		return jhMap;
	}

}
