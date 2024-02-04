package org.learn.framework.permission;

import java.util.List;

/**
 */
public abstract class AbstractRole {

    /**
     * 获取角色名称
     * @return 角色名称
     */
    public abstract String getRoleName();

    /**
     * 获取该角色的权限集合
     * @return 权限集合
     */
    public abstract List<String> getPermissions();
}
