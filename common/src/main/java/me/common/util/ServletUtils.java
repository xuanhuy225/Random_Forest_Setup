/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ServletUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ServletUtils.class);
    private static final int DEFAULT_SESSION = 60 * 60; // 1 hour 

    private static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        return df.format(new Date(System.currentTimeMillis()));
    }

    public static void print(HttpServletResponse resp, String content) {
        PrintWriter out = null;
        try {
            resp.setHeader("Date", getCurrentDate());
            resp.setCharacterEncoding("utf-8");
            out = resp.getWriter();
            out.print(content);
        } catch (IOException ex) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void printExcelFile(HttpServletResponse response, FileInputStream inStream, String fileName) throws IOException {

        OutputStream out = null;
        fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename*=UTF-8''" + fileName);
        try {
            out = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            inStream.close();
        } catch (Exception ex) {
            response.setContentType("text/html");
            LOG.error(ex.getMessage(), ex);
        } finally {
            if (out != null) {
                out.close();
            }
            if (inStream != null) {
                inStream.close();
            }
        }

    }

    public static void print(HttpServletResponse resp, JSONObject content) {
        resp.setHeader("Date", getCurrentDate());
        resp.setContentType("application/json; charset=utf-8");

        print(resp, content.toString());
    }

    public static void addCorsSpecify(HttpServletRequest request, HttpServletResponse response, String allowPath) {
        try {
            String referrer = request.getHeader("referer");
            URL urlReferer = new URL(referrer);
            if (ValidateUtils.isNullOrEmpty(allowPath) || urlReferer.getHost().endsWith(allowPath)) {
                response.addHeader("Access-Control-Allow-Origin", urlReferer.getProtocol() + "://" + urlReferer.getHost() + (urlReferer.getPort() > 0 ? ":" + urlReferer.getPort() : ""));
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, ex-authen,x-custom-oa,sentry-trace, authorization,zf_id,redirect,access-token");
                response.addHeader("Access-Control-Expose-Headers", "origin, content-type, accept ,Content-Disposition");
                response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
                response.addHeader("Access-Control-Allow-Credentials", "true");
            }
        } catch (MalformedURLException ex) {
        }
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return "";
    }

    public static String getAuthorization(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "", maxAge);
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, "", DEFAULT_SESSION);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (!ValidateUtils.isNullOrEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAge);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static String urlEncode(String text) throws UnsupportedEncodingException {
        return URLEncoder.encode(text, "UTF-8");
    }

    public static String urlDecode(String text) throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(text, "UTF-8");
    }

    public static boolean isLocalHost(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (referer == null || referer.length() == 0) {
            return false;
        }
        return referer.contains("localhost") || referer.contains("0.0.0.0") || referer.contains("127.0.0.1");
    }
}
