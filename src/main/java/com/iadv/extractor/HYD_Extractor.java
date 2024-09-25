package com.iadv.extractor;



import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HYD_Extractor {

    public static HashMap<String, String[]> extractData(String response) {
        HashMap<String, String[]> dataMap = new HashMap<>();

        // Regular expression to extract rows from the HTML table
        String rowPattern = "<tr role=\"row\" class=\"odd\">.*?</tr>";
        Pattern pattern = Pattern.compile(rowPattern, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        // Extract table rows
        while (matcher.find()) {
            String row = matcher.group();

            // Regular expression to extract columns (td elements)
            String colPattern = "<td>(.*?)</td>";
            Pattern colPatternCompiled = Pattern.compile(colPattern, Pattern.DOTALL);
            Matcher colMatcher = colPatternCompiled.matcher(row);

            String[] columns = new String[8];
            int colIndex = 0;
            String slNo = "";

            while (colMatcher.find() && colIndex < 9) {
                if (colIndex == 0) {
                    slNo = colMatcher.group(1).trim(); // Sl No as the key
                } else {
                    columns[colIndex - 1] = colMatcher.group(1).trim(); // Remaining columns as values
                }
                colIndex++;
            }

            // Store the extracted columns in the HashMap
            if (!slNo.isEmpty()) {
                dataMap.put(slNo, columns);
            }
        }

        return dataMap;
    }
}

