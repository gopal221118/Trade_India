package com.iadv.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DecryptAES {
    public static String getDecryptedText(String encryptedText) {
    	String dtt="";
        try {
        	System.out.println(encryptedText);
            // The base64 encoded key (same as the one used in the JavaScript code)
            String base64Key = "bXVzdGJlMTZieXRlc2tleQ==";
            
            // Decode the base64 key
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            // Initialize the cipher for AES/ECB/PKCS5Padding (similar to ECB with PKCS7 in CryptoJS)
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Decode the encrypted text (Base64)
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

            // Decrypt the data
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Convert decrypted bytes to a UTF-8 string
            String decryptedText = new String(decryptedBytes, "UTF-8");

            // Output the decrypted value
            System.out.println("Decrypted Text: " + decryptedText);
            dtt=dtt+decryptedText;

        } catch (Exception e) {
            e.printStackTrace();
        }
		return dtt;
    }
}
