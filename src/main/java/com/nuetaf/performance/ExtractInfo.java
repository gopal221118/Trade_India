package com.nuetaf.performance;

import com.opencsv.CSVWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExtractInfo {
   public static HashMap<String, String> getPDFValues(String inputText) {
      ArrayList<String> linesWithColon = new ArrayList();
      HashMap<String, String> licenseinfo = new HashMap();
      new HashMap();
      ArrayList<String> reqfields = new ArrayList();
      new ArrayList();

      try {
         String[] extractvalues = new String[]{"License Number", "Name", "Office_No_1", "Mobile_No_1", "Office_No_2", "Mobile_No_2", "Address", "State", "Pin Code", "Kind of Business", "Category of License", "Place", "Issued On", "Valid Upto", "Current Status of License", "Email-ID"};
         String[] lines = inputText.split("\\n");
         String[] valx = lines;
         int z = lines.length;

         int y;
         for(y = 0; y < z; ++y) {
            String line = valx[y];
            if (line.contains(":")) {
               linesWithColon.add(line.replace(": /", "/"));
            }
         }

         int mc = 1;
         Iterator var16 = linesWithColon.iterator();

         while(var16.hasNext()) {
            String line = (String)var16.next();
            if (line.contains("Contact")) {
               valx = line.replaceAll("[^0-9\\s]", "").trim().split("  ");
               if (valx.length == 2) {
                  reqfields.add("Office_No_" + mc + "~" + valx[0].trim());
                  reqfields.add("Mobile_No_" + mc + "~" + valx[1].trim());
               } else {
                  reqfields.add("Mobile_No_" + mc + "~" + valx[0].trim());
               }

               ++mc;
            } else {
               valx = line.split(":");
               String mapval = "";
               if (valx[1].trim().contains("(")) {
                  mapval = (mapval + valx[1].trim().substring(0, valx[1].trim().indexOf("("))).trim();
               } else {
                  mapval = mapval + valx[1].trim();
               }

               reqfields.add(valx[0].trim() + "~" + mapval);
            }
         }

         System.out.println("****************************************");

         for(y = 0; y < extractvalues.length; ++y) {
            for(z = 0; z < reqfields.size(); ++z) {
               if (((String)reqfields.get(z)).contains(extractvalues[y].trim())) {
                  if (extractvalues[y].trim().startsWith("Pin")) {
                     valx = ((String)reqfields.get(z)).split("~");
                     licenseinfo.put(extractvalues[y].trim(), valx[1].trim().replaceAll("[^0-9\\s]", "").trim());
                  } else if (extractvalues[y].trim().startsWith("State")) {
                     valx = ((String)reqfields.get(z)).split("~");
                     licenseinfo.put(extractvalues[y].trim(), valx[1].trim().replaceAll("District", "").trim());
                  } else {
                     valx = ((String)reqfields.get(z)).split("~");
                     licenseinfo.put(extractvalues[y].trim(), valx[1].trim());
                  }
               }
            }
         }

         System.out.println(licenseinfo);
         System.out.println("****************************************");
      } catch (Exception var13) {
         var13.printStackTrace();
      }

      return licenseinfo;
   }

   public static void writeRow(CSVWriter csvWriter, Map<String, String> pdfMap, String[] header) {
      try {
         String[] values;
         if (((String)pdfMap.get("Category of License")).contains("State")) {
            values = new String[]{(String)pdfMap.get("License Number"), "Phone Number correct", "Not Available", (String)pdfMap.get("Current Status of License"), (String)pdfMap.get("Category of License"), "Not Available", (String)pdfMap.get("State"), (String)pdfMap.get("Address"), (String)pdfMap.get("Pin Code"), "N/A", (String)pdfMap.get("Mobile_No_1"), (String)pdfMap.get("Email-ID"), (String)pdfMap.get("Name"), (String)pdfMap.get("License Number"), (String)pdfMap.get("Issued On"), (String)pdfMap.get("Valid Upto"), "False", (String)pdfMap.get("Email-ID"), (String)pdfMap.get("Mobile_No_2")};
            csvWriter.writeNext(values);
         } else {
            values = new String[]{(String)pdfMap.get("License Number"), "Phone Number correct", (String)pdfMap.get("Office_No_1"), (String)pdfMap.get("Current Status of License"), (String)pdfMap.get("Category of License"), "Not Available", (String)pdfMap.get("State"), (String)pdfMap.get("Address"), (String)pdfMap.get("Pin Code"), "N/A", (String)pdfMap.get("Mobile_No_1"), (String)pdfMap.get("Email-ID"), (String)pdfMap.get("Name"), (String)pdfMap.get("License Number"), (String)pdfMap.get("Issued On"), (String)pdfMap.get("Valid Upto"), (String)pdfMap.get("Office_No_2"), (String)pdfMap.get("Email-ID"), (String)pdfMap.get("Mobile_No_2")};
            csvWriter.writeNext(values);
         }
      } catch (Exception var4) {
         csvWriter.writeNext(header);
      }

   }
}
