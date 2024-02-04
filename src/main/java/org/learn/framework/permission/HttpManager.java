package org.learn.framework.permission;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class HttpManager {

    private static final String ACCESS_TOKEN = "yz_access_token";

    public static String getToken(String tokenName, HttpServletRequest request){
        // cookie中查找
        Cookie cookie = ServletUtil.getCookie(request, tokenName);
        if (cookie != null){
            return cookie.getValue();
        }
        // header中查找
        String header = request.getHeader(tokenName);
        if (StrUtil.isNotEmpty(header)){
            return header;
        }
        // 请求参数中查找
        String parameter = request.getParameter(tokenName);
        if (StrUtil.isNotEmpty(parameter)){
            return parameter;
        }
        return null;
    }

    public static String getAccessToken(HttpServletRequest request){
        return getToken(ACCESS_TOKEN, request);
    }

    public static void setAccessToken(String token, HttpServletResponse response){
        setToken(ACCESS_TOKEN, token, response);
    }

    public static void setToken(String tokenName, String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        // 默认有效期七天
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.setHeader(tokenName, token);
        response.addCookie(cookie);
    }

    public static void clearAccessToken(HttpServletResponse response){
        clearToken(ACCESS_TOKEN, response);
    }

    public static void clearToken(String tokenName, HttpServletResponse response){
        Cookie cookie = new Cookie(tokenName, null);
        response.addCookie(cookie);
    }
}
