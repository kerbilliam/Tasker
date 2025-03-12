package Cipher;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;


public class Ciphers {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding"; //algorithm/mode/padding
    private static final String KEY = "1234567891234567";

    public static String getKey(){
        return KEY; //code to generate a random key based on size. Maybe? If key is regenerated with every instance of the app  then will have no access to previous keys to decrypt existing data. Make an array of keys and then cycle through them when decrypting? That would make the app very slow.
    }
    
    public static String encrypt(String plainText, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM); //converts string key into byte format and then uses that to create a secret key object.
        Cipher cipher = Cipher.getInstance(TRANSFORMATION); //how the cipher will encrypt/decrypt
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] byteForm = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)); //encrypts and converts to bytes

        return Base64.getEncoder().encodeToString(byteForm); //converts the encrypted bytes into a string and returns it.
    }


    public static String decrypt(String cipherText, String key) throws Exception {
        try{
            Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));

            return new String(decryptedBytes, StandardCharsets.UTF_8);
            
        } catch (Exception e){
            System.out.println("Invalid key");
            return "none";
        }
            

        
    }
    public static void main(String[] args) throws Exception {
        String key = getKey(); //minimum key length is 16. Key length determines the rounds of encryption, so longer key means more secure encryption.
        String word = "important";
        //System.out.println(encrypt(word, key)); prints gibberish - GOOD
        String encrypted = encrypt(word, key);

        System.out.println(encrypt(word, key));

        System.out.println(decrypt(encrypted, key)); 

        String decrypted = decrypt(encrypted, key);
    }
}