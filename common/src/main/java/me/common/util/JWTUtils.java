package me.common.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTUtils {


    public static String createToken(String content, Long expireMilis, String secretKey) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims()
                .setSubject(content);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (expireMilis > 0) {
            long expMillis = nowMillis + expireMilis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();

    }

    public static Claims decodeToken(String jwt, String secretKey) {
        try {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt).getBody();
        } catch (Exception e) {

        }
        return null;
    }

    public static void applySession(HttpServletResponse response, String content, Long expireMilis, String secretKey, String tokenName) {
        String token = createToken(content, expireMilis, secretKey);

        ServletUtils.setCookie(response, tokenName, token, Math.toIntExact(expireMilis / 1000L));
    }

    public static String getContent(HttpServletRequest request, String secretKey, String tokenName) {
        String token = ServletUtils.getCookie(request, tokenName);
        if (ValidateUtils.isNullOrEmpty(token)) {
            token = request.getHeader(tokenName);
        }

        return getContent(token, secretKey);
    }

    public static String getContent(String token, String secretKey) {
        if (!ValidateUtils.isNullOrEmpty(token)) {
            Claims claims = decodeToken(token, secretKey);

            if (claims != null && claims.getExpiration().after(DateTimeUtils.getNow())) {
                return claims.getSubject();
            }
        }
        return null;
    }
    
    public static String getAuthorization(HttpServletRequest request, String secretKey, String authField) {
        String authorization = ServletUtils.getAuthorization(request, authField);

        return getContent(authorization, secretKey);
    }

}
