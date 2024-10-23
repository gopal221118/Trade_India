package com.iadv.extractor;

import java.io.File;
import java.util.ArrayList;

public class FilePathCollector1 {

    // ArrayList to store all file paths
    private static ArrayList<String> filePaths = new ArrayList<>();

    public static void main(String[] args) {
        String folderPath = "D:/Jharkhand_Ranchi"; // Folder path containing the HTML files
        File folder = new File(folderPath);
        collectFilePaths(folder);

        // Optionally print the file paths
        for (String path : filePaths) {
            System.out.println(path);
        }
        System.out.println(filePaths);
    }

    // Method to collect all file paths from a folder and its subfolders
    public  static void collectFilePaths(File folder) {
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

    // Method to return the list of file paths
    public static ArrayList<String> getFilePaths() {
        return filePaths;
    }
}
