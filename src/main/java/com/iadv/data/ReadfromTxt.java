package com.iadv.data;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadfromTxt {
	
	 public static String readFileAsString(String filePath) {
	        try {
	            // Read the file content and return it as a string
	            return new String(Files.readAllBytes(Paths.get(filePath)));
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null; 
	        }
	    }
	 
	 public static List<String> readFileAsList(String filePath) {
	        List<String> lines = new ArrayList<String>();
	        try {
	            // Read all lines from the file and store them in the ArrayList
	            lines = Files.readAllLines(Paths.get(filePath));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return lines; 
	    }

}