package com.iadv.tn;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TN_Assesment_Comparator {
    public static void main(String[] args) {
        String extractedFilePath = "C:\\Trade\\TN\\TN_Assesment_Final\\TN_Default_Assesment.xlsx";
        String fullFilePath = "C:\\Trade\\TN\\TN_Assesment_Final\\TN_AssesmentList.xlsx";
        String outputFilePath = "C:\\Trade\\TN\\TN_Assesment_Final\\Unmatched_Assessments.csv";

        try {
            // Step 1: Read extracted assessment numbers into a Set
            Set<String> extractedAssessmentNos = readAssessmentNumbers(extractedFilePath);

            // Step 2: Compare with the full list and identify unmatched assessments
            List<String> unmatchedAssessments = findUnmatchedAssessments(fullFilePath, extractedAssessmentNos);

            // Step 3: Print unmatched assessments and count to console
            printUnmatchedAssessments(unmatchedAssessments);

            // Step 4: Write the unmatched assessments to a CSV file
            writeUnmatchedToCsv(unmatchedAssessments, outputFilePath);

            System.out.println("Unmatched assessments written to: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> readAssessmentNumbers(String filePath) throws IOException {
        Set<String> assessmentNumbers = new HashSet<>();

        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Assuming "Assessment No" is in the first column
                if (cell != null) {
                    String value = cell.toString().trim();
                    if (!value.isEmpty()) {
                        assessmentNumbers.add(value);
                    }
                }
            }
        }

        return assessmentNumbers;
    }

    private static List<String> findUnmatchedAssessments(String filePath, Set<String> extractedNos) throws IOException {
        List<String> unmatched = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Assuming "Assessment No" is in the first column
                if (cell != null) {
                    String value = cell.toString().trim();
                    if (!value.isEmpty() && !extractedNos.contains(value)) {
                        unmatched.add(value);
                    }
                }
            }
        }

        return unmatched;
    }

    private static void writeUnmatchedToCsv(List<String> unmatched, String outputPath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {
            writer.write("Assessment No\n"); // Header
            for (String assessment : unmatched) {
                writer.write(assessment + "\n");
            }
        }
    }

    private static void printUnmatchedAssessments(List<String> unmatched) {
        System.out.println("Unmatched Assessment Numbers:");
        for (String assessment : unmatched) {
            System.out.println(assessment);
        }
        System.out.println("Total Unmatched Assessments: " + unmatched.size());
    }
}
