package com.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ripflowers
 */
public class CookieUtils {

    public static void addCookie(HttpServletResponse response, String key, String value, int expireTime) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(expireTime);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals(name)) {
                continue;
            } else {
                return cookie.getValue();
            }
        }
        return "";
    }

    public static void  removeCookie(HttpServletResponse response, String name) {
        Cookie cookie=new Cookie(name,null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
