package org.learn.framework.web.servlet;


import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.exception.FilterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器执行
 */
public class GlobalFilter {

    public boolean executeFilter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        BoundedPriorityQueue<Class<?>> queue = FilterAware.getQueue();

        for (Class<?> filter : queue.toList()) {
            try {
                Object filterInstance = ContextContainer.getApplicationContext().getBean(filter);
                if (filterInstance != null) {
                    Log.get().info("开始执行过滤器[{}]", filter.getName());
                    boolean execute = ReflectUtil.invoke(filterInstance, ReflectUtil.getMethod(filter, "execute", HttpServletRequest.class, HttpServletResponse.class), request, response);
                    if (!execute) {
                        Log.get().debug("过滤器成功拦截请求[{}]", filter.getName());
                        return false;
                    }
                }
            } catch (UtilException | SecurityException e) {
                Log.get().error("过滤器执行错误[{}]", filter.getName());
                e.printStackTrace();
                throw new FilterException(FilterException.FILTER_ERROR);
            }
        }
        return true;
    }
}
