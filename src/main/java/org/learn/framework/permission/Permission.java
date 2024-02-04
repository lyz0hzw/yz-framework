package org.learn.framework.permission;


import java.util.List;

/**
 * @author hongda.li 2022-04-03 12:21
 */
public interface Permission {

    List<AbstractRole> getUserRoles(String loginId);

}
