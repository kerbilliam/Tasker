import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Ciphers {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static byte[] encrypt(String plainText, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] cipherText, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
    public static void main(String[] args) throws Exception {
        String key = "1234567891234567"; //minimum key length is 16. Key length determines the rounds of encryption, so longer key means more secure encryption.
        String word = "important secret message";
        //System.out.println(encrypt(word, key)); prints gibberish - GOOD
        byte[] encrypted = encrypt(word, key);

        System.out.println(encrypt(word, key));

        System.out.println(decrypt(encrypted, key)); 

        String decrypted = decrypt(encrypted, key);
    }
}