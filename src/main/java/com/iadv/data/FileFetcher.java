package com.iadv.data;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileFetcher {

    // Method to fetch all file paths from a folder and return them in an ArrayList
    public static List<String> getAllFilePaths(String folderPath) {
        List<String> filePaths = new ArrayList<>();

        try {
            // Walk through the file tree and add all file paths to the list
            Files.walk(Paths.get(folderPath))
                 .filter(Files::isRegularFile) // Only include regular files (exclude directories)
                 .forEach(path -> filePaths.add(path.toString()));
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions such as invalid directory paths
        }

        return filePaths;
    }
}