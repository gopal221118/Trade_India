package com.iadv.extractor;

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

public class ExtractBLRPDF {

	public static ArrayList<ArrayList<String>> data = new ArrayList<>();
	// Create an Excel workbook
	public static Workbook workbook = new XSSFWorkbook();
	// Create a sheet in the workbook
	public static Sheet sheet = workbook.createSheet("PDF Data");


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

			if (pdfvals.length >= 15) {

				ArrayList<String> list = new ArrayList<>();

				list.add(pdfvals[1]);
				list.add(pdfvals[2]);
				list.add(pdfvals[3]);
				list.add(pdfvals[4]);
				list.add(pdfvals[5]);
				list.add(pdfvals[6]);
				list.add(pdfvals[7]);
				list.add(pdfvals[13]);
				list.add(pdfvals[15]);

				data.add(list);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) throws Exception {
		String excelFilePath = args[1];
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

		File[] files = new File(args[0]).listFiles();

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
				System.out.println("Directory: " + filename.getAbsolutePath());

				displayFiles(filename.listFiles());
			}

			else {
				System.out.println(filename.getAbsolutePath());
				remover(filename.getAbsolutePath());
			}
		}
	}

}