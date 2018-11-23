package com.wizzstudio.substitute.util;

import com.wizzstudio.substitute.constants.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public static Cookie getCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        Cookie cookie;
        try {
            cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(Constant.TOKEN)).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return cookie;
    }


    /**
     * 生成cookie
     */
    public static String tokenGenerate() {
        return UUID.randomUUID().toString();
    }
}
