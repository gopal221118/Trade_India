package com.iadv.main;

import java.io.File;


import java.io.FileWriter;
import java.util.*;

import com.iadv.api.TN_PropertyTax_API;
import com.iadv.data.ReadfromTxt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TN1_property_tax_with_contact_main_step1 {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No file path provided.");
            return;
        }

        String mnc = ReadfromTxt.readFileAsString(args[1]);
        String[] mncall = mnc.split(";");
        HashMap<String, String> mncmap = new HashMap<>();

        for (String s : mncall) {
            if (!s.contains(":")) {
                break;
            } else {
                String[] mncx = s.split(":");
                mncmap.put(mncx[0].trim(), mncx[1].trim());
            }
        }

      
        HashMap<String, ArrayList<String>> munci_no_with_ward_no = new HashMap<>();

        for (String key : mncmap.keySet()) {
            try {
                File file = new File(args[0] + File.separator + mncmap.get(key) + ".html");
                FileWriter fw = new FileWriter(file);
                String postdata = ReadfromTxt.readFileAsString(args[0] + File.separator + mncmap.get(key) + ".txt");

                String[] postvals = postdata.split("&");
                String vsx = "";
                String evx = "";

                for (String postval : postvals) {
                    if (postval.startsWith("__VIEWSTATE=")) {
                        vsx = postval.split("=")[1].trim();
                    } else if (postval.startsWith("__EVENTVALIDATION=")) {
                        evx = postval.split("=")[1].trim();
                    } else {
                        System.out.println(postval);
                    }
                }

                String resp = TN_PropertyTax_API.getAPIResponse(postdata, fw);
                Document doc = Jsoup.parse(resp);
                Elements options = doc.select("#PageContent_drpward > option");

                ArrayList<String> wardValues = new ArrayList<>();
                for (int i1 = 1; i1 < options.size(); i1++) {
                    Element option = options.get(i1);
                    String value = option.attr("value");
                    if (!value.isEmpty()) {
                        wardValues.add(value);
                    }
                }

                munci_no_with_ward_no.put(key, wardValues);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String code : munci_no_with_ward_no.keySet()) {
            System.out.println(code + ":" + munci_no_with_ward_no.get(code));
        }
    }
}
