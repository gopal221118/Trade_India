package com.iadv.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.iadv.data.ReadfromTxt;

public class BLR_Trade_API {

    public static String sendPostRequest(String znw, String folderpath) throws Exception {
        StringBuilder allResponses = new StringBuilder();
        String znws[] = znw.split(":");

        for (char i = 'A'; i <= 'Z'; i++) {
            // Define file path and writer
            File file = new File(folderpath + File.separator + znws[1].trim() + "_" + znws[0].trim() + "_" + i + "_KA.html");
            FileWriter fw = new FileWriter(file);
            
            // Setup connection
            String url = "https://trade.bbmpgov.in/Forms/frmRenewalTradeRegistration.aspx";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set request method to POST
            con.setRequestMethod("POST");

            // Set headers
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("accept-language", "en-GB,en-US;q=0.9,en;q=0.8,hi;q=0.7");
            con.setRequestProperty("cache-control", "no-cache");
            con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setRequestProperty("cookie", "_gid=GA1.2.1790788213.1729258753; _ga_KVBYG29ZXK=GS1.1.1729258752.6.0.1729258752.0.0.0; _ga=GA1.1.957376084.1728477914; ASP.NET_SessionId=el4qzfcw51iwskcrzmuo5mih");
            con.setRequestProperty("origin", "https://trade.bbmpgov.in");
            con.setRequestProperty("referer", "https://trade.bbmpgov.in/Forms/frmRenewalTradeRegistration.aspx");
            con.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"129\", \"Not=A?Brand\";v=\"8\", \"Chromium\";v=\"129\"");
            con.setRequestProperty("sec-ch-ua-mobile", "?0");
            con.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            con.setRequestProperty("sec-fetch-dest", "empty");
            con.setRequestProperty("sec-fetch-mode", "cors");
            con.setRequestProperty("sec-fetch-site", "same-origin");
            con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
            con.setRequestProperty("x-microsoftajax", "Delta=true");
            con.setRequestProperty("x-requested-with", "XMLHttpRequest");

            
            // Request body (form data) based on the curl data
            String contents=ReadfromTxt.readFileAsString("C:\\CIN\\testx.txt");
            String urlParameters = "ctl00%24ToolkitScriptManager1=ctl00%24ContentPlaceHolder1%24upTradeLicense%7Cctl00%24ContentPlaceHolder1%24btnDetailSearch&ToolkitScriptManager1_HiddenField=&ctl00%24ContentPlaceHolder1%24ddlZone="+znws[1].trim()+"&ctl00%24ContentPlaceHolder1%24ddlWard="+znws[0].trim()+"&ctl00%24ContentPlaceHolder1%24txtName="+i+contents;
            		con.setDoOutput(true);
            System.out.println("Sending request for: " + i);

            // Send POST request
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }

            // Get the response code
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the input stream
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    allResponses.append(response.toString());
                }
            } else {
                System.err.println("Request failed with response code: " + responseCode);
            }

            // Write the response to file
            fw.write(allResponses.toString());
            fw.close();
        }

        // Return the combined response as a String
        return allResponses.toString();
    }
}

