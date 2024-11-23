package com.iadv.data;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLSourcefromFile {

    public static String getSouce(String fpath) {
    String fsource="";
        try {
            File htmlFile = new File(fpath);
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            String pageSource = doc.outerHtml();
            fsource=fsource+pageSource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fsource;
    }
}
