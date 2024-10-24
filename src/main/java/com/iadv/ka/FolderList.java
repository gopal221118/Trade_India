package com.iadv.ka;

import java.io.File;

public class FolderList {
    public static void main(String[] args) {
        // Provide the folder path here
        String folderPath = "F:\\BLRX";

        // Create a File object for the given folder path
        File folder = new File(folderPath);

        // Check if the path is a directory
        if (folder.isDirectory()) {
            // Get the list of all files and directories in the folder
            File[] fileList = folder.listFiles();

            if (fileList != null) {
                System.out.println("Folder paths in the given directory:");

                // Loop through the list to print only directories (full path)
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        System.out.println(file.getAbsolutePath());
                    }
                }
            } else {
                System.out.println("The folder is empty or the path is invalid.");
            }
        } else {
            System.out.println("The provided path is not a directory.");
        }
    }
}
