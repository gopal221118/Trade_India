package com.iadv.main;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.opencsv.CSVWriter;

public class BLR_RPT_EXTRACT {

	public static void main(String[] args) {
		try {
			String csvFile = args[0];
	        CSVWriter writer = null;
	        
	        try {
	            writer = new CSVWriter(new FileWriter(csvFile));
	              writer.writeNext(new String[]{("Acknowledgement No"),("Contact Name"),("Contact No"),("Application No"),("Total Fee (Rs)"),("Penlty (Rs)"),("Paid Amount (Rs)"),("Excess Amount(Rs)"),("Mode of Payment"),("Date"),("Trade Name and Address"),("Trade License renewal from")});
	        } catch (IOException e) {
	            e.printStackTrace();
	            return; 
	        }
	        
	        String directoryPath = args[1];
	        ArrayList<String> filePaths = getAllFilePaths(directoryPath);

	        for (String path : filePaths) {
	            System.out.println(path);
	            File file = new File(path);
		        try (PDDocument document = PDDocument.load(file)) {
		            PDFTextStripper pdfStripper = new PDFTextStripper();
		            String text = pdfStripper.getText(document);
		            String ntext = (((((((((((((((((text.replace("Acknowledgement No:","~Acknowledgement No:")).replace("Contact Name:","~Contact Name:")).replace("New Trade License No:","~New Trade License No:")).replace("Contact No:","~Contact No:")).replace("Trade License renewal from", "~Trade License renewal from")).replace("Paid Amount (Rs) :", "~Paid Amount (Rs) :")).replace("Application No:", "~Application No:")).replace("", ""))).replace("Trade Name and Address :", "~Trade Name and Address :")).replace("Mode of Payment:", "~Mode of Payment:"))).replace("Date:", "~Date:")).replace("Report Generation", "")).replace("Total Fee (Rs)  :", "~Total Fee (Rs)  :")).replace("Total Payable Fee (Rs) :", "")).replace("Penlty (Rs)", "~Penlty (Rs)")).replace("Excess Amount(Rs) :", "~Excess Amount(Rs) :");
		            String vals[]=ntext.split("~");
			        HashMap<String,String> valMap= new HashMap<String,String>();
			        for(String val:vals)
			        {
			        	if(val.contains(":"))
			        	{
			        		if(val.contains("Contact No:"))
			        		{
			        			String pval[]=val.split(":");
			        			valMap.put(pval[0].trim(), pval[1].substring(0, 10));
			        		}
			        		else
			        		{
			        			String xval[]=val.split(":");
			        			valMap.put(xval[0].trim(), xval[1].trim());
			        		}
			        	System.out.println(val);
			        	}
			        	else if(val.contains("-20"))
			        	{
			            String tval[]= val.split(",");
			        	valMap.put("Trade License renewal from", (tval[0].replace("Trade License renewal from", "")).trim());
			        	}
			        }
			        System.out.println(valMap);
			        String tempMap[]= {valMap.get("Acknowledgement No"),valMap.get("Contact Name"),valMap.get("Contact No"),valMap.get("Application No"),valMap.get("Total Fee (Rs)"),valMap.get("Penlty (Rs)"),valMap.get("Paid Amount (Rs)"),valMap.get("Excess Amount(Rs)"),valMap.get("Mode of Payment"),valMap.get("Date"),valMap.get("Trade Name and Address"),valMap.get("Trade License renewal from")};
			        writer.writeNext(tempMap);
			        System.out.println(tempMap);
		            	
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		       }
	        writer.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static ArrayList<String> getAllFilePaths(String directoryPath) {
	        ArrayList<String> filePaths = new ArrayList<>();
	        File folder = new File(directoryPath);

	        if (folder.exists() && folder.isDirectory()) {
	            retrieveFilePaths(folder, filePaths);
	        } else {
	            System.out.println("The provided path is not a valid directory.");
	        }

	        return filePaths;
	    }

	    private static void retrieveFilePaths(File folder, ArrayList<String> filePaths) {
	        File[] files = folder.listFiles();

	        if (files != null) {
	            for (File file : files) {
	                if (file.isFile()) {
	                    filePaths.add(file.getAbsolutePath());
	                } else if (file.isDirectory()) {
	                    retrieveFilePaths(file, filePaths);
	                }
	            }
	        }
	    }
		}
