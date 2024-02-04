package org.learn.framework.permission;

import cn.hutool.log.Log;
import org.learn.framework.config.PermissionConfig;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.web.servlet.Filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author hongda.li 2022-04-03 15:05
 */
public class PermissionFilter implements Filter {
    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LoginHelper helper = new LoginHelper().init(request, response);
        Method method = Environment
                .getConfig(WebConfig.class)
                .getRequestBody()
                .getClassAndMethod(request.getRequestURI())
                .getMethod();
        String loginId = helper.getLogin(); // 从请求中获取token
        if (method.getAnnotation(CheckLogin.class) != null){
            boolean login = helper.isLogin();
            if (!login){
                Log.get().debug("该用户处于未登录状态");
                response.sendError(401,"该用户处于未登录状态");
                return false;
            }
            // 重新设置登录状态
            if (Environment.getConfig(PermissionConfig.class).isAutoRefreshToken()){
                helper.refreshLogin(loginId);
            }
        }
        CheckRole checkRole = method.getAnnotation(CheckRole.class);
        if (checkRole != null){
            boolean checkRoles = PermissionPool.checkRoles(loginId, checkRole.value());
            if (!checkRoles){
                Log.get().debug("该用户不具有角色权限[{}]", loginId);
                response.sendError(403,"该用户不具有角色权限[" + loginId + "]");
                return false;
            }
        }
        CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);
        if (checkPermission != null){
            boolean checkPermissions =  PermissionPool.checkPermissions(loginId, checkPermission.value());
            if (!checkPermissions){
                Log.get().debug("该用户不具有访问权限[{}]", loginId);
                response.sendError(403,"该用户不具有访问权限[" + loginId + "]");
                return false;
            }
        }
        return true;
    }
}
