package org.learn.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hongda.li@hand-china.com 2022/3/11 23:56
 */
public interface Filter {

    /**
     * 获取执行优先级
     * @return 执行优先级
     */
    int getOrder();

    /**
     * 执行过滤业务逻辑
     * @param request 请求
     * @param response 响应
     * @return 是否放行
     * @throws IOException IOException
     */
    boolean execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
