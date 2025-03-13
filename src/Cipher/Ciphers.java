package Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Ciphers {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding"; 
    private static final String KEY = "1234567891234567";

    public static String getKey(){
        return KEY; 
    }
    
    public static String encrypt(String plainText, String key) throws Exception  {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM); 
            Cipher cipher = Cipher.getInstance(TRANSFORMATION); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    
            byte[] byteForm = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)); 
            return Base64.getEncoder().encodeToString(byteForm);
        } catch (Exception e) {
            System.err.println("Error occurred during encryption: " + e);
            return "";
        }
 
    }


    public static String decrypt(String cipherText, String key) throws Exception {
        try{
            if (cipherText == null || cipherText.length() == 0) {
                return "";
            }

            Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));

            return new String(decryptedBytes, StandardCharsets.UTF_8);
            
        } catch (IllegalArgumentException | IllegalStateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e){
            System.err.println("Error occurred during decryption: " + e);
            return "";
        }
            

        
    }
}