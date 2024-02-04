package org.learn.framework.web.servlet;


import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.learn.framework.annotation.Priority;
import org.learn.framework.context.*;
import org.learn.framework.util.ClassUtils;

import java.util.Set;

/**
 * 过滤器自动注入
 */
@Priority(Level.Middle)
public class FilterAware implements ApplicationContextAware {

    public static final boolean ENABLE_HTTP_SERVLET = Environment.conditionOn("javax.servlet.Servlet");

    private static BoundedPriorityQueue<Class<?>> queue;

    public static BoundedPriorityQueue<Class<?>> getQueue(){
        return FilterAware.queue;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (!ENABLE_HTTP_SERVLET){
            return;
        }

        Set<Class<?>> filters = ClassUtils.getClassesExtendClass(Filter.class);
        filters.forEach(clazz -> {
            Log.get().info("扫描到过滤器[{}]",clazz.getName());
            applicationContext.setBean(clazz);
            new Refresher(clazz).refresh();
        });

        queue = new BoundedPriorityQueue<>(filters.size(), (o1, o2) -> {
            int order1 = ReflectUtil.invoke(applicationContext.getBean(o1), ReflectUtil.getMethodByName(o1, "getOrder"));
            int order2 = ReflectUtil.invoke(applicationContext.getBean(o2), ReflectUtil.getMethodByName(o2, "getOrder"));
            return Integer.compare(order1, order2);
        });

        queue.addAll(filters);
    }
}
