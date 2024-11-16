package com.iadv.mp;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pdf_to_Eccel_Convertor  {

    public static ArrayList<ArrayList<String>> data = new ArrayList<>();
    // Create an Excel workbook
    public static Workbook workbook = new XSSFWorkbook();
    // Create a sheet in the workbook
    public static Sheet sheet = workbook.createSheet("PDF Data");

    public static String excelFilePath = "First_30K_output.xlsx";

    public static void remover(String path) {

        String pdfFilePath = path;


        try {
            // Load the PDF document
            PDDocument pdfDocument = PDDocument.load(new File(pdfFilePath));

            // Extract text from PDF
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(pdfDocument);

            // Close the PDF document
            pdfDocument.close();

            String pdfvals[] = pdfText.split(":");


            if (pdfvals.length == 15) {

                ArrayList<String> list = new ArrayList<>();

                list.add(pdfvals[0]);
                list.add(pdfvals[2]);
                list.add(pdfvals[4]);
                list.add(pdfvals[6]);
                list.add(pdfvals[8]);
                list.add(pdfvals[9]);
                list.add(pdfvals[10]);
                list.add(pdfvals[13]);

//                for (int i = 0; i < pdfvals.length; i++) {
//                    System.out.println(i + "----------->" + pdfvals[i]);
//
//                }

                data.add(list);




            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) throws Exception {

        ArrayList<String> list = new ArrayList<>();
        list.add("Registration Number");
        list.add("City");
        list.add("Name");
        list.add("Fine Fee");
        list.add("Phone Number");
        list.add("DD/MM/YYYY");
        list.add("DD/MM/YYYY");
        list.add("Registration Name");
        list.add("Registration Address");
        data.add(list);
        
        String url = args[0];

        File[] files = new File(url).listFiles();

        // Calling method 1 to
        // display files*/
        displayFiles(files);

        int rawi = 0;
        for (ArrayList<String> l : data) {
            int cell = 0;
            Row row = sheet.createRow(rawi++);
            for (String cdata : l) {

                row.createCell(cell++).setCellValue(cdata);
            }
        }

        // Write the Excel file to disk
        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        System.out.println("PDF has been successfully converted to Excel!");

    }

    public static void displayFiles(File[] files) {
        // Traversing through the files array
        for (File filename : files) {
            // If a sub directory is found,
            // print the name of the sub directory
            if (filename.isDirectory()) {
                System.out.println("Directory: "
                        + filename.getAbsolutePath());

                // and call the displayFiles function
                // recursively to list files present
                // in sub directory
                displayFiles(filename.listFiles());
            }

            // Printing the file name present in given path
            else {
                // Getting the file name
                System.out.println(filename.getAbsolutePath());
                remover(filename.getAbsolutePath());
                //Convertor(filename.getAbsolutePath());
            }
            // remover("/Users/nishantmishra/Pdf_to_excel/src/MP/eNagarPalika_Trade_Renewal_20240305024408.pdf");
        }
    }





}

