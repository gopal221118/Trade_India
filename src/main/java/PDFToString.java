import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFToString {

    public static void main(String[] args) {
        try {
            // Correct the file path and ensure the file exists
            File file = new File("C:/receiptDownload/input/Output/february_receipt.pdf");
            if (!file.exists()) {
                System.out.println("File not found: " + file.getAbsolutePath());
                return;
            }
            
            // Load the PDF file
            PDDocument document = PDDocument.load(file);
            
            // Extract text from the PDF
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);
            
            // Close the PDF document after extraction
            document.close();

            // Print the extracted text for debugging
            System.out.println("Extracted PDF Text:\n" + pdfText);

            // Variables to store extracted information
            String applicationNumber = null;
            String contactNumber = null;
            String tradeName = null;
            String fromDate = null;
            
            // Extracting Application Number
            if (pdfText.contains("Application No:")) {
                applicationNumber = pdfText.split("Application No:")[1].split("\\n")[0].trim();
            }

            // Extracting Contact Number
            if (pdfText.contains("Contact No:")) {
                contactNumber = pdfText.split("Contact No:")[1].split("\\n")[0].trim();
            }

            // Extracting Trade Name
            if (pdfText.contains("Trade Name and Address :")) {
                tradeName = pdfText.split("Trade Name and Address :")[1].split("\\n")[0].trim();
            }

            // Extracting License Renewal "From Date"
            if (pdfText.contains("Trade License renewal from")) {
                fromDate = pdfText.split("Trade License renewal from")[1].split(" to")[0].trim();
            }
            
            // Create a new Excel workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Trade License Data");
            
            // Create a header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Application Number");
            headerRow.createCell(1).setCellValue("Contact Number");
            headerRow.createCell(2).setCellValue("Trade Name");
            headerRow.createCell(3).setCellValue("License Renewal From Date");
            
            // Create a row to store extracted data
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(applicationNumber != null ? applicationNumber : "N/A");
            dataRow.createCell(1).setCellValue(contactNumber != null ? contactNumber : "N/A");
            dataRow.createCell(2).setCellValue(tradeName != null ? tradeName : "N/A");
            dataRow.createCell(3).setCellValue(fromDate != null ? fromDate : "N/A");
            
            // Write the data to an Excel file with a valid path
            try (FileOutputStream fileOut = new FileOutputStream("C:/receiptDownload/output/Trade_License_Information.xlsx")) {
                workbook.write(fileOut);
            }
            
            // Close the workbook
            workbook.close();
            
            System.out.println("Data has been saved to Trade_License_Information.xlsx");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
