package br.com.dev.lgpd.services;

import br.com.dev.lgpd.services.exceptions.CryptographyServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class CryptographyService {

    public static String encrypt(String input, SecretKey secretKey) {

        // Generate Iv
        IvParameterSpec initializationVector = generateInitializationVector();

        Cipher cipher;
        try {
            // Get Algorithm Instance
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Init cipher.
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, initializationVector);

            // Encrypt input text
            byte[] cipherText = cipher.doFinal(input.getBytes());

            // Generate Iv Base64
            String ivAsString = Base64.getEncoder().encodeToString(initializationVector.getIV());

            // Generate cipher text base64
            String cipherTextAsStrig = Base64.getEncoder().encodeToString(cipherText);

            return ivAsString.concat(":").concat(cipherTextAsStrig);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String cipherText, String secretKey) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            String[] cipherParts = cipherText.split(":");

            IvParameterSpec initializationVector = new IvParameterSpec(Base64.getDecoder().decode(cipherParts[0]));
            byte[] secretKeyByte = Base64.getDecoder().decode(secretKey.getBytes());

            // Generate SecretKeySpec
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyByte, 0, secretKeyByte.length,  "AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, initializationVector);

            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherParts[1]));

            return new String(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | InvalidKeyException | BadPaddingException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static SecretKey generateSecretKey() throws CryptographyServiceException {
        int secretKeySize = 256;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(secretKeySize);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException exception) {
            log.error("Could not generate secretKey", exception);
            throw new CryptographyServiceException("Could not generate secretKey", exception);
        }
    }

    private static IvParameterSpec generateInitializationVector() {
        byte[] initializationVector = new byte[16];

        new SecureRandom().nextBytes(initializationVector);
        return new IvParameterSpec(initializationVector);
    }

    public static String convertSecretKeyToBase64(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
