package org.learn.framework.permission;

import cn.hutool.core.util.StrUtil;
import org.learn.framework.context.Environment;
import org.learn.framework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 权限集合
 */
public class PermissionPool {

    // tokenId : roleList
    private static final Map<String, List<AbstractRole>> PERMISSION_POOL = new ConcurrentHashMap<>();

    /**
     * 根据tokenId获取用户角色
     * @param tokenId
     * @return
     */
    public static List<AbstractRole> getUserRoles(String tokenId){
        if (StrUtil.isEmpty(tokenId)){
            return new ArrayList<>(0);
        }
        return PERMISSION_POOL.get(tokenId);
    }

    /**
     * 根据tokenId获取用户权限
     * @param tokenId
     * @return
     */
    public static List<String> getUserPermissions(String tokenId){
        // 获取用户的角色
        List<AbstractRole> abstractRoles = getUserRoles(tokenId);
        if (abstractRoles == null || abstractRoles.isEmpty()){
            return new ArrayList<>(0);
        }
        // 通过角色获取用户拥有的权限
        List<String> permissions = new ArrayList<>();
        abstractRoles.forEach(role -> permissions.addAll(role.getPermissions()));
        return permissions;
    }

    /**
     * 设置用户的角色
     * @param loginId
     */
    public static void setUserRoles(String loginId){
        Class<?> first = ClassUtils.getClassesExtendClass(Permission.class).stream().findFirst().get();
        Permission bean = (Permission) Environment.getBean(first);
        if (bean != null){
            PERMISSION_POOL.put(loginId, bean.getUserRoles(loginId));
        }
    }

    /**
     * 检查用户是否拥有角色
     * @param tokenId 用户ID
     * @param roles   允许访问的角色集合
     * @return
     */
    public static boolean checkRoles(String tokenId, Class<? extends AbstractRole>[] roles){
        if (roles.length == 1 && roles[0] == AbstractRole.class){
            return true;
        }
        // 允许访问的角色集合
        List<Class<? extends AbstractRole>> classes = Arrays.asList(roles);
        // 用户所具有的角色集合
        List<AbstractRole> abstractRoles = getUserRoles(tokenId);
        if (abstractRoles == null || abstractRoles.isEmpty()){
            return false;
        }
        for (AbstractRole abstractRole : abstractRoles) {
            // 若用户所具有的角色在允许访问的角色中存在
            if (classes.contains(abstractRole.getClass())){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查用户是否具有权限
     * @param tokenId      用户Id
     * @param permissions  允许访问的权限集合
     * @return
     */
    public static boolean checkPermissions(String tokenId, String[] permissions){
        if (permissions.length == 1 && "*".equals(permissions[0])){
            return true;
        }
        List<String> strings = Arrays.asList(permissions);
        for (String userPermission : getUserPermissions(tokenId)) {
            if (strings.contains(userPermission)){
                return true;
            }
        }
        return false;
    }

    public static void clearRoles(String tokenId){
        PERMISSION_POOL.remove(tokenId);
    }

}
