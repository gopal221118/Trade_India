package com.iadv.data;

import java.io.File;
import java.util.ArrayList;

public class FileListfromFolder {

    private static ArrayList<String> filePaths = new ArrayList<>();

    public static ArrayList<String> getfileList(String folderPath) {
        File folder = new File(folderPath);
        collectFilePaths(folder);
        System.out.println(filePaths);
		return filePaths;
    }

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
  
}
