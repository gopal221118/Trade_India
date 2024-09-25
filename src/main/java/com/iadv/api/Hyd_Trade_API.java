package com.iadv.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Hyd_Trade_API {

    public String sendRequest(String inputChar) throws Exception {
        // The URL of the API endpoint
        String url = "https://onlinepayments.ghmc.gov.in/TLTax/Get_TL_Search_Det";

        // Open a connection to the URL
        @SuppressWarnings("deprecation")
		URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set the request method to POST
        con.setRequestMethod("POST");

        // Set headers as specified in the cURL command
        con.setRequestProperty("accept", "text/html, */*; q=0.01");
        con.setRequestProperty("accept-language", "en-US,en;q=0.9,hi;q=0.8,te;q=0.7");
        con.setRequestProperty("content-type", "application/json; charset=UTF-8");
        con.setRequestProperty("cookie", "ghmc-gov-in_xc=\"a6bfccf7afcb7d04\"; ASP.NET_SessionId=bnbtv2cduqqoqjv514ivw3t0; __RequestVerificationToken=d8PeihMKAA_RmWVWSt6iqv6GMqPohb88GcXGZ4pqYpA5ZW1dhpJVYQv8MxP5hovmB0JgzMvcTVN2ntDavjDiREqHPF_jFC4mPG-aaSZRJek1; TS01dc4fc6=019fd2dd653af5c2ecf18bdbf65827a905dae45e3f80897a7d6cbf5f30e1d48a265797b38b486339efc4f1e59083d70e9f48ffc2a0; ac0d03=rCZ4v3Ct2b3/ctmgI7xqhA58yeOEYJ//1hP4p2ToLZWUZQPV939ygzI/qS/qeuh9/SaFsQe7c2vlQV2cWtIJWm2GRr1C8M81/6sYGziY9WKT8vDf5IGBnczZNDaKsHJ8az4cc7i41F28TQp69Kj8WXXsi2FYpqtRGfqT+t863odReM+o; ac0d03=D1WFzdrkVU3Q8wAJgLepYttjfehLPV0D373pQBCYl7T3TUggaG02GaF4/eCqzj434V1w+eRm1C6BgzhCBYCYw1nuFEnwrGJezG6gmXLpIgbP3dc7XeracAdw36oqaspkwEdGKhOT2VfGUjh2wgNL7DHdofmZozbO3kqOHDFCcMyf2F3m");
        con.setRequestProperty("origin", "https://onlinepayments.ghmc.gov.in");
        con.setRequestProperty("priority", "u=1, i");
        con.setRequestProperty("referer", "https://onlinepayments.ghmc.gov.in/");
        con.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Google Chrome\";v=\"128\"");
        con.setRequestProperty("sec-ch-ua-mobile", "?0");
        con.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        con.setRequestProperty("sec-fetch-dest", "empty");
        con.setRequestProperty("sec-fetch-mode", "cors");
        con.setRequestProperty("sec-fetch-site", "same-origin");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
        con.setRequestProperty("x-requested-with", "XMLHttpRequest");

        // Enable input/output streams
        con.setDoOutput(true);

        // Prepare the JSON data payload with updated values
        String jsonData = "{Circle: \"1069\",OwnerDoorNo: \"\",OwnerName: \"" + inputChar + "\", TINNO: \"\",tradeName: \"\",tradedoorno: \"\"}";

        // Write the JSON data to the output stream
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Check if the response code is not 200 (OK)
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Server returned HTTP response code: " + responseCode + " for URL: " + url);
        }

        // Get the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Return the response as a string
        return response.toString();
    }
}
