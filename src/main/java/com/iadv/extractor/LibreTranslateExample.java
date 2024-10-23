package com.iadv.extractor;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LibreTranslateExample {

    public static void main(String[] args) {
        // Original Hindi text to translate
        String hindiText = "कायार्लय - नगर पालक निगम, इंदौर "
                + "वर्ष : 2023-24 "
                + "नगर पालिका निगम अधिनियम 1956 की धारा 366 एवं 427 के अंतर्गत निनमर्त उपविधियों के अंतर्गत जारी "
                + "व्यवसाय लाइसेंस (अनुपित्त)";
        
        // Translate the Hindi text using MyMemory API
        String translatedText = translateMyMemory(hindiText, "hi", "en");

        // Print the translated text
        System.out.println("Translated Text:\n" + translatedText);
    }

    /**
     * Translates text using MyMemory API
     * 
     * @param textToTranslate The text to be translated
     * @param sourceLang      Source language code (e.g., "hi" for Hindi)
     * @param targetLang      Target language code (e.g., "en" for English)
     * @return Translated text
     */
    public static String translateMyMemory(String textToTranslate, String sourceLang, String targetLang) {
        String translatedText = "";
        
        try {
            // Encode the text for the URL
            String encodedText = URLEncoder.encode(textToTranslate, StandardCharsets.UTF_8.toString());
            String apiUrl = String.format("https://api.mymemory.translated.net/get?q=%s&langpair=%s|%s",
                                          encodedText, sourceLang, targetLang);

            // Create HTTP client and request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the raw API response for debugging
            System.out.println("API Response: " + response.body());

            // Parse the JSON response
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject responseData = jsonResponse.getAsJsonObject("responseData");
            translatedText = responseData.get("translatedText").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            translatedText = "Error occurred during translation.";
        }

        return translatedText;
    }
}
