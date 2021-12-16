import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public final class SecretKeyGeneration {
    private static String movePC;
    private final String key;
    private final String HMACMove;


    public SecretKeyGeneration(String movePC) {
        SecretKeyGeneration.movePC = movePC;
        this.key = keyToHexString(generateKey());
        this.HMACMove = hmacWithJava("HmacSHA3-256", movePC, key);
    }

    public final void showHMACmove() {
        System.out.println("HMAC: " + HMACMove);
    }

    public final void showSecretKey() {
        System.out.println("HMAC key: " + key);
    }

    public final void checkHMAC() {
        System.out.println(hmacWithJava("HmacSHA3-256", movePC, this.key));
    }

    public static SecretKey generateKey() {
        KeyGenerator keyGenerator = null;
        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        assert keyGenerator != null;
        keyGenerator.init(keyBitSize, secureRandom);
        return keyGenerator.generateKey();
    }

    public String keyToHexString(SecretKey secretKey) {
        byte[] rawData = secretKey.getEncoded();
        return hexString(rawData);
    }

    public String hmacWithJava(String algorithm, String data, String key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        byte[] bytes = data.getBytes();
        Mac mac = null;
        try {
            mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return hexString(mac.doFinal(bytes));
    }

    private String hexString(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        return String.format(
                "%0" + (bytes.length << 1) + "X", bigInteger);
    }
}
