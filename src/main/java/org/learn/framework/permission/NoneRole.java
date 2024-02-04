package org.learn.framework.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 空的角色，若@CheckRole(NoneRole.class)，则无人可以访问该接口
 */
public class NoneRole extends AbstractRole{
    @Override
    public String getRoleName() {
        return "NoneRole";
    }

    @Override
    public List<String> getPermissions() {
        return new ArrayList<>(0);
    }
}
