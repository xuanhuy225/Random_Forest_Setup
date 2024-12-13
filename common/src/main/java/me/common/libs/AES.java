package me.common.libs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;



public class AES {

    private static final Logger LOG = LoggerFactory.getLogger(AES.class);
    private SecretKeySpec _secretKey;
    private byte[] _key;
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public AES(String key) {
        setKey(key);
    }

    private void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            _key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            _key = sha.digest(_key);
            _key = Arrays.copyOf(_key, 16);
            _secretKey = new SecretKeySpec(_key, "AES");
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("AES::setKey", ex);
        }
    }

    public String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, _secretKey);

            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            LOG.error("AES::encrypt", e);
        }

        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, _secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            LOG.error("AES::decrypt", e);
        }

        return null;
    }

    public String encryptUrl(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, _secretKey);

            return Base64.getUrlEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOG.error("AES::encrypt", e);
        }

        return null;
    }

    public String decryptUrl(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, _secretKey);
            return new String(cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOG.error("AES::decrypt", e);
        }

        return null;
    }
}
