package com.transit.ticketing.cipher;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class Cryptic {
    public static String sign(String requestBody) throws Exception {
        PrivateKey privKey = readPrivateKey(new File("private.pem"));
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privKey);
        sig.update(requestBody.getBytes(StandardCharsets.UTF_8));
        byte[] s = sig.sign();
        return Base64.getEncoder().encodeToString(s);
    }
    public static boolean verify(String requestBody,String signature) throws Exception {
        PublicKey pubKey = readPublicKey(new File("public.pem"));
        Signature sig2 = Signature.getInstance("SHA256withRSA");
        sig2.initVerify(pubKey);
        sig2.update(requestBody.getBytes(StandardCharsets.UTF_8));
        return sig2.verify(signature.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws Exception {
        String enc = "b3JkZXJfaWQ6MTAzMDt0cmlwX2lkOjIwMDA7c2NoZWR1bGVfaWQ6OTAwO2RvajoyMDIxLTEyLTE1O29yaV9zdG9wOjEwMTtkZXN0X3N0b3A6MTAyO25vOjI7Y3JlYXRlZDpUdWUgRGVjIDI4IDEwOjM3OjA2IElTVCAyMDIxO2JvYXRfaWQ6NDAwO3NpZ25hdHVyZTpJQkYveTJBNzhJc0hnZndaU2lsOEQxUHpvKzJDd2o1L1BCT0FLeW5VcG1SaWtDdHhzRlZ3WERzU3J3OFNPby9OUTNmdUpBbWtubTJpS1BIQ3lPUkVZa0VZTC91Z0pHTUpPSi82OXhwV3RIVHVXblhTNWgzeXpUTEtWUy9yOThLY3hnR3JlTk1MR2tGcFBXc3R6V3EraXpIbjBUbVlmcWxSSWxHMnc1N0tCZHNQbUc4OExnVnowUUthaTJBYTROUm96ck5aK1RnY2JRMXRTTU94cWVWRDl0cHMrdUlTVjJmRytsVlZadXRraGR3ejJuWXlhZldERSthbmJVNEVMUnJMMWxHVXZ1QXAraEZEWGlZT0ttNUR1RFYrNC8zNmI4czJKUEx6L1RIc3c1MXlwRkdnMExKYjFLdHkyazJ0b3IzQ0pMYUR0TkQ2K2g4bTNrSHp3cUhlV3c9PQ==";
        String dec = new String(Base64.getDecoder().decode(enc));
        System.out.println(dec);
        String req = "order_id:1030;trip_id:2000;schedule_id:900;doj:2021-12-15;ori_stop:101;dest_stop:102;no:2;created:Tue Dec 28 10:37:06 IST 2021;boat_id:400";
        String sign = "IBF/y2A78IsHgfwZSil8D1Pzo+2Cwj5/PBOAKynUpmRikCtxsFVwXDsSrw8SOo/NQ3fuJAmknm2iKPHCyOREYkEYL/ugJGMJOJ/69xpWtHTuWnXS5h3yzTLKVS/r98KcxgGreNMLGkFpPWstzWq+izHn0TmYfqlRIlG2w57KBdsPmG88LgVz0QKai2Aa4NRozrNZ+TgcbQ1tSMOxqeVD9tps+uISV2fG+lVVZutkhdwz2nYyafWDE+anbU4ELRrL1lGUvuAp+hFDXiYOKm5DuDV+4/36b8s2JPLz/THsw51ypFGg0LJb1Kty2k2tor3CJLaDtND6+h8m3kHzwqHeWw==";
        PublicKey pubKey = readPublicKey(new File("public.pem"));
        Signature sig2 = Signature.getInstance("SHA256withRSA");
        sig2.initVerify(pubKey);
        sig2.update(req.getBytes(StandardCharsets.UTF_8));
        boolean result = sig2.verify(Base64.getDecoder().decode(sign));
        System.out.println(result);
    }

    public static RSAPrivateKey readPrivateKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("\n", "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static RSAPublicKey readPublicKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("\n", "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
    }
}
