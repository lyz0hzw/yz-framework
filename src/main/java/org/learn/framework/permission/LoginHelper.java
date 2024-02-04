package org.learn.framework.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录初始化
 */
public class LoginHelper {

    private HttpServletRequest servletRequest;
    private HttpServletResponse servletResponse;

    public LoginHelper init(HttpServletRequest request, HttpServletResponse response){
        this.servletRequest = request;
        this.servletResponse = response;
        return this;
    }

    public void setLogin(String loginId){
        // 设置该loginId对应的角色集
        PermissionPool.setUserRoles(loginId);
        if (servletResponse != null){
            // loginId生成token， 在response的header中加入cookie，将生成的token放进去
            HttpManager.setAccessToken(TokenManager.createToken(loginId), servletResponse);
        }
    }

    public void refreshLogin(String loginId){
        HttpManager.setAccessToken(TokenManager.createToken(loginId), servletResponse);
    }

    public boolean isLogin(){
        if (servletRequest != null){
            return TokenManager.verifyToken(HttpManager.getAccessToken(servletRequest));
        }
        return false;
    }

    public String getLogin(){
        if (servletRequest != null){
            return TokenManager.getTokenValue(HttpManager.getAccessToken(servletRequest));
        }
        return null;
    }

    public void offLogin(){
        PermissionPool.clearRoles(getLogin());
        HttpManager.clearAccessToken(servletResponse);
    }
}
