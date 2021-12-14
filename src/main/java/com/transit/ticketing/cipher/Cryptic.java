package com.transit.ticketing.cipher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class Cryptic {
    public static String sign(String requestBody) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
        File privateKeyFile = new File("private.key");
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Signature sig = Signature.getInstance("SHA256withRSA");

        // extract the private key

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);
        sig.initSign(privKey);

        sig.update(requestBody.getBytes(StandardCharsets.UTF_8));
        byte[] s = sig.sign();
        String encodedString = Base64.getEncoder().encodeToString(s);
        return encodedString;
    }
    public static boolean verify(String requestBody,String signature) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
        File publicKeyFile = new File("public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        Signature sig = Signature.getInstance("SHA256withRSA");
        PublicKey pubKey = keyFactory.generatePublic(publicKeySpec);
        sig.initVerify(pubKey);
        sig.update(requestBody.getBytes(StandardCharsets.UTF_8));
        return sig.verify(Base64.getDecoder().decode(signature));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        //System.out.println(Cryptic.sign("Hello"));
        //String sign = "VkXnwY2BZnTxOwZR4/RA39jv003Q81K05o9+9+5LmzBl2khm1fHsCdBKVMAImQfw9HO2VCO8YT56lMygOQbOtq9tMd+xuemwkt126lVyLnSYyfQWe1yrAmBMuncsUFC69vLDBgoIF/H9qSohZr02m0NAXNhcVxt50wpK24WCZhMdJRQIK1oLLEAaHROeJ4rN3x9a964QEuV9v7wXp8lA3oMN4pRV+DjyTuXmDOBMczz6ZksxW0Um4GzW7fRMCJBGwQIAc0vIKeFeQURvsQF2DJm7dUNUI5AKKf02JQh5iUrwzkQq2S08Jj8Ijk7ZJY7eai+M8krc2jLJHgapJVFAUw==";
        //System.out.println(Cryptic.verify("Hello",sign));

        /*String req = "b3JkZXJfaWQ6MTAyNzt0cmlwX2lkOjIwMDA7c2NoZWR1bGVfaWQ6OTAwO2RvajoyMDIxLTEyLTE0O29yaV9zdG9wOjEwMTtkZXN0X3N0b3A6MTAyO25vOjI7Y3JlYXRlZDpUdWUgRGVjIDE0IDExOjIwOjQ0IElTVCAyMDIxO2JvYXRfaWQ6NDAwO3NpZ25hdHVyZTpWRWtJR1VTeUZnR3hMZkpVVVF6MDRSTFEzYkNKWDVyZXRtbG0rQ1NsOWMxSDZiOFFIVU1sbm5TZlVDV2RNMGlyS3hVczlFUjlHUk1hMkdIL0VtVmp5MUR2d1dQK1RnMUQwU3BYaXJmT1dmZ1BtYm1hMS9OeXRWaVRRb2JLN0FvcFFJbG9QU1p6V0lONEZ1OHFTTXFhU2l4b2NCMWZBSXRpOGhKV3hrYTUzeFB4NjVNRWdhdk5OZUxxdE1oSVJ3blJrVmVOVWdHdWNVdmJnZ3FwS1BFWEs1NVBaR2Q3SlhkeXpLalhQclVPdUNKMHdmdDl3aStRcHpiVFdqa1U1QzR1Zm5vMEN0dWVwNXdhYktqK2FSRTd2eUw5TFFrQmJ4ampLa0VqQkFHbUNoRGZNczdLbDVyNEs0c21XMTl3d2JlQzRwT1pTNnFaS3hhQ3R1aGJUTldldlE9PQ==";
        byte[] dec = Base64.getDecoder().decode(req);*/

        String re = "order_id:1028;trip_id:2000;schedule_id:900;doj:2021-12-14;ori_stop:101;dest_stop:102;no:2;created:Tue Dec 14 11:20:44 IST 2021;boat_id:400";
        String sign = "VEkIGUSyFgGxLfJUUQz04RLQ3bCJX5retmlm+CSl9c1H6b8QHUMlnnSfUCWdM0irKxUs9ER9GRMa2GH/EmVjy1DvwWP+Tg1D0SpXirfOWfgPmbma1/NytViTQobK7AopQIloPSZzWIN4Fu8qSMqaSixocB1fAIti8hJWxka53xPx65MEgavNNeLqtMhIRwnRkVeNUgGucUvbggqpKPEXK55PZGd7JXdyzKjXPrUOuCJ0wft9wi+QpzbTWjkU5C4ufno0Ctuep5wabKj+aRE7vyL9LQkBbxjjKkEjBAGmChDfMs7Kl5r4K4smW19wwbeC4pOZS6qZKxaCtuhbTNWevQ==";

        System.out.println(Cryptic.verify(re,sign));
    }
}
