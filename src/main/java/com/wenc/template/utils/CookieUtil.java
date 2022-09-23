package com.wenc.template.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

    /***
     * 通过cookie名直接查询value
     * @param request
     * @param name cookie名
     * @return
     */
    public static String getValue(HttpServletRequest request, String name){
        if (request == null || name == null){
            try {
                throw new IllegalAccessException("参数为空");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
