package me.common.helper;

import me.common.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class OAuthHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthHelper.class);

    public static String genCodeChallenge(String codeVerifier) {
        String codeChallenge = null;
        if (ValidateUtils.notNullOrEmpty(codeVerifier)) {
            try {
                byte[] bytes = codeVerifier.getBytes("US-ASCII");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(bytes, 0, bytes.length);
                byte[] digest = md.digest();
                codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return codeChallenge;
    }


    public static String genCodeVerifier() {
        SecureRandom randomCode = new SecureRandom();
        byte[] code = new byte[32];

        randomCode.nextBytes(code);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }
}
