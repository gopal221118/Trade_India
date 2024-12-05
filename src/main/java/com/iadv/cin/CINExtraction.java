package com.iadv.cin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iadv.data.FileFetcher;
import com.iadv.data.HTMLSourcefromFile;
import com.opencsv.CSVWriter;

public class CINExtraction {
	
	public static void main(String args[])
	{
		try {
			String csvFile = args[1];
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter(csvFile));
				writer.writeNext(new String[] {"CIN", "URL", "ROC", "Valid"});
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			try
			{
				List<String> filelists = FileFetcher.getAllFilePaths(args[0]);
				for(String filelist : filelists)
				{
					String html = HTMLSourcefromFile.getSouce(filelist);
					extractNWrite(html,writer);
				}
				writer.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
    public static void extractNWrite(String html,CSVWriter writer) {
      try {
            // Parse the HTML
            Document doc = Jsoup.parse(html);
            
            // Get the row count
            Elements rows = doc.select("#table > tbody > tr");
            int rowCount = rows.size();
            System.out.println("Row count: " + rowCount);

            // Loop through the rows and extract values
            for (int i = 1; i <= rowCount; i++) {
            	ArrayList<String> values = new ArrayList<String>();
                Elements cells = doc.select("#table > tbody > tr:nth-child(" + i + ") > td");
                for (Element cell : cells) {
                    // Check if the cell contains an anchor tag
                    Elements link = cell.select("a");
                    if (!link.isEmpty()) {
                        String href = link.attr("href");
                        values.add(href);
                        System.out.println("Link: " + href);
                    } else {
                        System.out.println("Value: " + cell.text());
                        values.add(cell.text());
                    }
                    if(values.size()==4)
                    {
                    String[] temparray = values.toArray(new String[0]);
                    writer.writeNext(temparray);
                    }
                }
                System.out.println("-------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
