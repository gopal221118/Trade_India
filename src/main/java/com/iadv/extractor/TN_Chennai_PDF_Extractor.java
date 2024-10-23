package com.iadv.extractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TN_Chennai_PDF_Extractor extends PDFTextStripper {
	static ArrayList<String> btvals=new ArrayList<String>();
	private static String fulltext;

    public TN_Chennai_PDF_Extractor() throws IOException {
        super();
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
    	String bt="";
        // Iterate over the TextPositions to check the font attributes
        for (TextPosition text : textPositions) {
            // Check if the font is bold using the font name or font weight
            if (isBold(text)) {
                // Print the bold text
            	bt=bt+text.getUnicode();
               // System.out.print(text.getUnicode());
            }
        }
        fulltext=fulltext+bt+" ";
        String btt=bt.replace("No.", "No#");
        if(!(btt.length()==0) && btt.length()<75 && !(btt.charAt(0)== ' ') && !(btt.contains(".")))
        {
          	btvals.add(btt);
     }      
    }

    // Method to determine if the text is bold based on the font name or font weight
    private boolean isBold(TextPosition textPosition) {
    	
        String fontName = textPosition.getFont().getName().toLowerCase();
        // Check if the font name contains 'bold' or has weight 700 (common for bold fonts)
        return fontName.contains("bold") || textPosition.getFont().getFontDescriptor().getFontWeight() >= 700;
    }

    public static void main(String[] args) {
    	HashMap<String,String> tradeMap=new HashMap<String,String>();
        String filePath = "C:/Users/Superuser/Desktop/LicenseCopy-3.pdf";
        String address="";
        String nlc="";
        try {
            // Load the PDF document
            PDDocument document = PDDocument.load(new File(filePath));

            // Create an instance of the custom BoldTextStripper
            TN_Chennai_PDF_Extractor stripper = new TN_Chennai_PDF_Extractor();
            stripper.setStartPage(0);  // Optional: Set page limits if needed
            stripper.setEndPage(document.getNumberOfPages());

            // Process and print only bold text
            stripper.getText(document);

            // Close the document
            document.close();
            System.out.println(btvals);
            for(int i=5;i<btvals.size();i++)
            {
            	if((btvals.get(i).length())==14)
            	{
            		nlc=nlc+btvals.get(i);
                	System.out.println("New Licence Code:"+btvals.get(i));
                	tradeMap.put("New Licence Code",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==19)
            	{
                	System.out.println("Receipt No:"+btvals.get(i));
                	tradeMap.put("Receipt No",btvals.get(i));
       	}
            	else if((btvals.get(i).length())==4)
            	{
                	System.out.println("Division:"+btvals.get(i));
                	tradeMap.put("Division",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==3)
            	{
                	System.out.println("Zone:"+btvals.get(i));
                	tradeMap.put("Zone",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==10)
            	{
                	System.out.println("Date of Registration:"+btvals.get(i));
                	tradeMap.put("Date of Registration",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==21)
            	{
                	System.out.println("Old Licence Code:"+btvals.get(i));
                	tradeMap.put("Old Licence Code",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==13)
            	{
                	System.out.println("Expiry Date:"+btvals.get(i));
                	tradeMap.put("Expiry Date",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==15)
            	{
                	System.out.println("PTNAN :"+btvals.get(i));
                	tradeMap.put("PTNAN",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==16)
            	{
                	System.out.println("PROPERTY ID:"+btvals.get(i));
                	tradeMap.put("PROPERTY ID",btvals.get(i));
            	}
            	else if((btvals.get(i).length())==20)
            	{
                	System.out.println("Receipt No(Prop) :"+btvals.get(i));
                	tradeMap.put("Receipt No(Prop)",btvals.get(i));
            	}
            	else
            	{
            		
            		if(btvals.get(i).contains(",") || btvals.get(i).contains("-"))
            		{
            			address=address+btvals.get(i);
            		}
            	   	}

            }

        } catch (IOException e) {
            System.err.println("Error occurred while processing the PDF: " + e.getMessage());
        }
		System.out.println("Address:"+address);
		tradeMap.put("Address 1",address);
        if(nlc.length()==14)
       	{
       		String cname[]=(fulltext.substring(fulltext.indexOf(nlc)+14,fulltext.indexOf(nlc)+65)).split(" ");
           	System.out.println("Name:"+(cname[0]+cname[1]).trim());
           	tradeMap.put("Name",(cname[0]+cname[1]).trim());
       	}
            String addressx[]=address.split(",");
            String tnameall="";
        	String tname= fulltext.substring(fulltext.indexOf(addressx[addressx.length-1])+(addressx[addressx.length-1]).length(),fulltext.indexOf(addressx[addressx.length-1])+(addressx[addressx.length-1]).length()+180);
        	
        	String tnamex[]=tname.split("-");
        			String tnamey[]=tnamex[0].split(" ");
        			for(int j=0;j<tnamey.length;j++)
        			{
        				if(tnamey[j].contains(".00") )
        				{
        					String tempv=tnamey[j];
        					tnameall=tname.replace(tempv, "~");
        				}
        			}
        			String tnameallx=tnameall.substring(0,tnameall.indexOf("-")+7);
        			String tnamearray[]=tnameallx.split("~");
        			String tradename=(tnamearray[0].trim()).substring(0,(tnamearray[0].trim()).lastIndexOf(" "));
        			String fname=(tnamearray[0].trim()).replace(tradename, "");
        			System.out.println("Trade Name:"+tradename);
        			tradeMap.put("Trade Name",tradename);
        			System.out.println("Parent Name:"+fname);
        			tradeMap.put("Parent Name",tradename);
        			String tta2=(tnamearray[1].trim()).substring(0,(tnamearray[1].trim()).indexOf(","));
        			String tty=tta2.substring(0,tta2.lastIndexOf(" "));
        			String ra2= (tnamearray[1].trim()).replace(tta2, "");
        			System.out.println("Trade Type:"+tty);
        			tradeMap.put("Trade Type",tty);
        			String fa2=tta2.replace(tty, "")+ra2;
        			System.out.println("Address2:"+fa2);
        			tradeMap.put("Address 2",fa2);
        			System.out.println(tradeMap);
        			


        
    }
}
