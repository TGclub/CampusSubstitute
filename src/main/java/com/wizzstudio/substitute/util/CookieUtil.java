package com.wizzstudio.substitute.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * cookie工具类
 * Created By Cx On 2018/7/30 10:28
 * Modified By Kikyou 2018/10/31 00:34
 */
public class CookieUtil {

    /**
     * 设置cookie值
     * expire：过期时间，单位：秒
     */
    public static void setCookie(HttpServletResponse response, String key, String value, Integer expire) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
    }

    /**
     * 获取key为name的cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {

                return cookie;
            }
        }
        return null;
    }

    /**
     * 生成cookie
     */
    public static String tokenGenerate() {
        return UUID.randomUUID().toString();
    }
}
