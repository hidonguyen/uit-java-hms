package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.AppConfig;

import java.util.Base64;

public class CryptoHelper {

    private static final String SECRET_KEY = AppConfig.load().aesHashKey;

    public static String encrypt(String plainText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), Constants.ALGORITHM);
            Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {

        }
        return "";
    }

    public static String decrypt(String encryptedText) {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), Constants.ALGORITHM);

        try {
            Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {

        }
        return "";
    }

}
