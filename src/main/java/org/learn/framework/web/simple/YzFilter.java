package org.learn.framework.web.simple;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;

/**
 * 过滤器
 */
public interface YzFilter {

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
     */
    boolean execute(HttpServerRequest request, HttpServerResponse response);
}
