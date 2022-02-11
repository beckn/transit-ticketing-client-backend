package com.transit.ticketing.cipher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jcajce.provider.digest.Blake2b;

public class Cryptic2 {
    public static void main(String[] args) throws CryptoException, InterruptedException {
        // Test case defined in https://www.rfc-editor.org/rfc/rfc8037
        var msg = "eyJhbGciOiJFZERTQSJ9.RXhhbXBsZSBvZiBFZDI1NTE5IHNpZ25pbmc".getBytes(StandardCharsets.UTF_8);
        var expectedSig = "hgyY0il_MGCjP0JzlnLWG1PPOt7-09PGcvMg3AIbQR6dWbhijcNR4ki4iylGjg5BhVsPt9g7sVvpAr_MuM0KAg";

        var privateKeyBytes = Base64.getUrlDecoder().decode("nWGxne_9WmC6hEr0kuwsxERJxWl7MmkZcDusAxyuf2A");
        var publicKeyBytes = Base64.getUrlDecoder().decode("11qYAYKxCrfVS_7TyWQHOg7hcvPapiMlrwIaaPcHURo");

        /*var privateKey = new Ed25519PrivateKeyParameters(privateKeyBytes, 0);
        var publicKey = new Ed25519PublicKeyParameters(publicKeyBytes, 0);

        // Generate new signature
        Signer signer = new Ed25519Signer();
        signer.init(true, privateKey);
        signer.update(msg, 0, msg.length);
        byte[] signature = signer.generateSignature();
        var actualSignature = Base64.getUrlEncoder().encodeToString(signature).replace("=", "");

        // Verify
        Signer verifier = new Ed25519Signer();
        verifier.init(false, publicKey);
        verifier.update(msg, 0, msg.length);
        boolean verified = verifier.verifySignature(signature);

        System.out.println(verified);*/
        String privatekey = "8j0Menby6+O4kUkE2yDJw4u0pqWmAdlftsROXlbKHVGHlb0U6bkLc857vBBKHCL5TDsLxsw7mqJFI0cyC7/a8A==";
        String publickey = "h5W9FOm5C3POe7wQShwi+Uw7C8bMO5qiRSNHMgu/2vA=";
        Cryptic2 cryptic2 = new Cryptic2();
        String sign = cryptic2.sign(
                privatekey,
                "hello"
        );
        System.out.println(sign);
        boolean result = cryptic2.verify(publickey, "hello", sign);
        System.out.println(result);

    }

    public String sign(
            String b64PrivateKey,
            String requestBody
    ) throws CryptoException {
        Signer signer = getEd25519SignerForSigning(b64PrivateKey);
        String formattedRequest = formatBodyForSigning(requestBody);
        signer.update(formattedRequest.getBytes(StandardCharsets.UTF_8), 0, formattedRequest.length());
        return Base64.getEncoder().encodeToString(signer.generateSignature());
    }

    public boolean verify(
            String b64PublicKey,
            String requestBody,
            String signature
    ){
        Signer signer = getEd25519SignerForVerification(b64PublicKey);
        String formattedRequest = formatBodyForSigning(requestBody);
        signer.update(formattedRequest.getBytes(StandardCharsets.UTF_8), 0, formattedRequest.length());
        return signer.verifySignature(Base64.getDecoder().decode(signature));
    }

    private Signer getEd25519SignerForVerification(String b64PublicKey){
        byte[] publicKey = Base64.getDecoder().decode(b64PublicKey);
        var cipherParams = new Ed25519PublicKeyParameters(publicKey, 0);
        Signer sv = new Ed25519Signer();
        sv.init(false, cipherParams);
        return sv;
    }

    private Signer getEd25519SignerForSigning(String b64PrivateKey){
        byte[] privateKey = Base64.getDecoder().decode(b64PrivateKey);
        var cipherParams = new Ed25519PrivateKeyParameters(privateKey, 0);
        Signer sv = new Ed25519Signer();
        sv.init(true, cipherParams);
        return sv;
    }

    private String formatBodyForSigning(
            String requestBody
    ){return  "digest: BLAKE-512="+blakeHash(requestBody);};


    private String blakeHash(String requestBody){
        MessageDigest digest = new Blake2b.Blake2b512();
        digest.reset();
        digest.update(requestBody.getBytes(StandardCharsets.UTF_8));
        byte[] hash = digest.digest();
        //val hex: String = Hex.toHexString(hash)
        return Base64.getEncoder().encodeToString(hash);
    }
}
