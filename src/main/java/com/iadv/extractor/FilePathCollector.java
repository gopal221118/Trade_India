package com.iadv.extractor;

import java.io.File;
import java.util.ArrayList;

public class FilePathCollector {
    private static ArrayList<String> filePaths = new ArrayList<>();

    public static void main(String[] args) {
        String folderPath = "D:/Jharkhand_Ranchi";
        File folder = new File(folderPath);
        collectFilePaths(folder);
        for (String path : filePaths) {
            System.out.println(path);
        }
        System.out.println(filePaths.get(2));
    }

    private static void collectFilePaths(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filePaths.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    collectFilePaths(file);
                }
            }
        }
    }
    public static ArrayList<String> seshaGan(){
    	return filePaths;
    }
}

