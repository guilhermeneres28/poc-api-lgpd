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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class CryptographyService {

    public static String encrypt(String input, SecretKey key) {

        IvParameterSpec initializationVector = generateInitializationVector();

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, initializationVector);

            byte[] cipherText = cipher.doFinal(input.getBytes());

            return Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        IvParameterSpec initializationVector = generateInitializationVector();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, key, initializationVector);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));

        return new String(plainText);
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


    public static String convertSecretKey(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
