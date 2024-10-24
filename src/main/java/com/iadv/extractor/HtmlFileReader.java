package com.iadv.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlFileReader {

    public static String getFileContents(String filePath) {
    	String fullcontent="";
         try {
            String fileContent = readHtmlFileAsString(filePath);
            fullcontent=fullcontent+fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return fullcontent;
    }

    // Method to read the HTML file and return its content as a String
    public static String readHtmlFileAsString(String filePath) throws IOException {
        // Read all bytes from the file and convert to a string
    	String dd="";
    	try
    	{
        dd= dd+new String(Files.readAllBytes(Paths.get(filePath)));
    	}
    	catch (Exception e) {
    		e.printStackTrace();
		}
		return dd;
    	
    }
}
