package com.iadv.ka;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MoveFiles {
    public static void main(String[] args) {
        // Source folder (from where files will be moved)
        String sourceFolderPath = "C:\\Users\\Praveen\\Downloads";
        
        // Destination folder (where files will be moved to)
        String destinationFolderPath = "D:\\BLR_Main";

     // Create File objects for source and destination folders
        File sourceFolder = new File(sourceFolderPath);
        File destinationFolder = new File(destinationFolderPath);

        // Check if source and destination folders exist
        if (sourceFolder.isDirectory() && destinationFolder.isDirectory()) {
            // Get all files in the source folder
            File[] files = sourceFolder.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Check if the current file has a .html extension
                    if (file.isFile() && file.getName().endsWith(".html")) {
                        // Create destination file object
                        File destFile = new File(destinationFolder, file.getName());

                        try {
                            // Move the file to the destination folder
                            Files.move(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Moved file: " + file.getName());
                        } catch (IOException e) {
                            System.out.println("Failed to move file: " + file.getName());
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("The source folder is empty or invalid.");
            }
        } else {
            System.out.println("Either the source or destination folder path is invalid.");
        }
    }
}
