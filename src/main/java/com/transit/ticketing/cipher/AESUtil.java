package com.transit.ticketing.cipher;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class AESUtil {
    private final static String algorithm = "AES/CBC/PKCS5Padding";
    public static String encrypt(String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static IvParameterSpec generateIv(String iv) {
        return new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        /*String input = "baeldung";
        SecretKey key = AESUtil.generateKey(128);
        IvParameterSpec ivParameterSpec = AESUtil.generateIv("test");
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = AESUtil.encrypt(input, key, ivParameterSpec);
        System.out.println(cipherText);
        String plainText = AESUtil.decrypt(cipherText, key, ivParameterSpec);
        System.out.println(plainText);*/

        SecretKeySpec skeySpec = new SecretKeySpec("rx0TXv6fkyI3QXOLJedSYg==".getBytes("UTF-8"), "AES");
        //String encryptedSign = AESUtil.encrypt("baeldung",skeySpec,AESUtil.generateIv("encryptionIntVec"));
        String encryptedSign="TzENb1oNnu4fccqeI98gWHGltVUCW/oliAmMtNuvP7F7Gh3rgO9ZB54veT1yzKTfRYX5bfqyjjl4cgP5FWIg3nQj2eVIkl1DgIWOa9eR3wnV1LmcNsuTp8i9k674d8NAa5jho77I35ifzIT2jvSlSgp4zT8zrKSeLwKWe+gmhG4=";
        String plainText = AESUtil.decrypt(encryptedSign, skeySpec, AESUtil.generateIv("encryptionIntVec"));
        System.out.println(plainText);
    }
}
