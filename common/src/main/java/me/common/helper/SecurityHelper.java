package me.common.helper;

import me.common.libs.Hashids;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


public class SecurityHelper {
    final static Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);
    private static final String DEFAULT_SALT = "";
    private static final Hashids hashIdIST = new Hashids(DEFAULT_SALT);

    public static byte[] md5Bytes(String text) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        msgDigest.update(text.getBytes());

        byte[] bytes = msgDigest.digest();

        return bytes;
    }

    public static String md5(String text) {
        byte[] bytes = md5Bytes(text);

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    public static byte[] sha1Byte(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support SHA-1 algorithm.");
        }
        messageDigest.update(text.getBytes());

        byte[] bytes = messageDigest.digest();

        return bytes;
    }

    public static String sha1(String text) {
        byte[] bytes = sha1Byte(text);

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    public static String idEncode(long number) {
        long[] numbers;
        if (number >= 0) {
            numbers = new long[]{0, number};
        } else {
            numbers = new long[]{1, -number};
        }

        return hashIdIST.encode(numbers);
    }

    public static String idEncodeWithCustomSALT(long number, String salt) {
        long[] numbers;
        if (number >= 0) {
            numbers = new long[]{0, number};
        } else {
            numbers = new long[]{1, -number};
        }
        Hashids hashIdIST = new Hashids(salt);
        return hashIdIST.encode(numbers);
    }


    public static long idDecode(String encodeText) {
        try {
            if (encodeText != null) {
                long[] numbers = hashIdIST.decode(encodeText.toLowerCase());

                if (numbers[0] == 0) {
                    return numbers[1];
                } else {
                    return -numbers[1];
                }
            }
        } catch (Exception ex) {
        }

        return 0L;
    }

    public static int lowerHashCode(String text) {
        if (text == null) {
            return 0;
        }

        int h = 0;
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                ch = (char) (ch + 32);
            }

            h = 31 * h + ch;
        }

        return h;
    }


    public static String uid() {
        return UUID.randomUUID().toString();
    }

    public static Long uuid() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }


    public static String maskPhone(String phone) {
        final StringBuilder builder = new StringBuilder();
        final int length = phone.length();
        for (int i = 0; i < length; i++) {
            if (i <= 2 || i >= length - 3) {
                builder.append(phone.charAt(i));
            } else {
                builder.append("x");
            }
        }
        return builder.toString();
    }

    public static long hashString(String string) {
        // based on string hash principle
        long hashValue = 1125899906842597L; // long prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            hashValue = 31 * hashValue + string.charAt(i);
        }
        return hashValue;
    }

    public static long hashPositiveNumericString(String string) {
        // based on string hash principle
        long hashValue = 1125899906842597L; // long prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            hashValue = Math.abs(31 * hashValue + string.charAt(i));
        }
        return hashValue;
    }

    public static String encryptRSA(String plainText, String publicKey) throws Exception {
        byte[] publicBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptRSA(String cipherText, String privateKey) {
        try {
            byte[] privateBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);

            byte[] bytes = Base64.getDecoder().decode(cipherText);
            Cipher decriptCipher = Cipher.getInstance("RSA");
            decriptCipher.init(Cipher.DECRYPT_MODE, priKey);

            return new String(decriptCipher.doFinal(bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("error decrypting", e);
        }
        return null;
    }

    public static String generateHMAC256(String secretKey, String phone){

        SecretKeySpec hmacSHA2561 = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac hmacSHA256 = null;
        try {
            hmacSHA256 = Mac.getInstance("HmacSHA256");
            hmacSHA256.init(hmacSHA2561);
            byte[] hashString = hmacSHA256.doFinal(phone.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashString) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    public static String hashSHA256(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }

    public static String sha1(String text, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((secretKey).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] hashString = mac.doFinal(text.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashString) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String hashSHA512(String key, String data) {
        try {
            if (key != null && data != null) {
                Mac hmac512 = Mac.getInstance("HmacSHA512");
                byte[] hmacKeyBytes = key.getBytes();
                SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
                hmac512.init(secretKey);
                byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                byte[] result = hmac512.doFinal(dataBytes);
                StringBuilder sb = new StringBuilder(2 * result.length);
                byte[] var8 = result;
                int var9 = result.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    byte b = var8[var10];
                    sb.append(String.format("%02x", b & 255));
                }

                return sb.toString();
            } else {
                throw new NullPointerException();
            }
        } catch (Exception var12) {
            return "";
        }
    }

    public static String hashAllFields(String key, Map<String, String> fields) {
        List<String> fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while(itr.hasNext()) {
            String fieldName = (String)itr.next();
            String fieldValue = (String)fields.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }

            if (itr.hasNext()) {
                sb.append("&");
            }
        }

        return hashSHA512(key, sb.toString());
    }

    public static String encryptAESParamApi(String data, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, paramApiKeySpecs(secretKey));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return "";
        }
    }

    public static String decryptAESParamApi(String encrypted, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, paramApiKeySpecs(secretKey));
            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            return null;
        }
    }

    private static SecretKeySpec paramApiKeySpecs(String secretKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(secretKey.getBytes());
            byte[] key = new byte[16];
            System.arraycopy(digest.digest(), 0, key, 0, key.length);
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            return null;
        }
    }
}

