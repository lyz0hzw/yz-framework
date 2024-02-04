package org.learn.framework.web;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.learn.framework.annotation.Priority;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.Level;
import org.learn.framework.context.Refresher;
import org.learn.framework.util.ClassUtils;
import org.learn.framework.web.simple.YzFilter;

import java.util.Set;

/**
 * @author hongda.li@hand-china.com 2022/3/16 16:59
 */
@Priority(Level.Middle)
public class WebFilterAware implements ApplicationContextAware {

    private static BoundedPriorityQueue<Class<?>> queue;

    public static BoundedPriorityQueue<Class<?>> getQueue(){
        return WebFilterAware.queue;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Set<Class<?>> yzFilters = ClassUtils.getClassesExtendClass(YzFilter.class); // 寻找YzFilter的子类
        yzFilters.forEach(clazz -> {
            Log.get().info("扫描到过滤器[{}]",clazz.getName());
            applicationContext.setBean(clazz);
            new Refresher(clazz).refresh();
        });
        // 定义大小堆和比较器，并通过反射调用getOrder方法来获取顺序值用来排序
        queue = new BoundedPriorityQueue<>(yzFilters.size(), (o1, o2) -> {
            int order1 = ReflectUtil.invoke(applicationContext.getBean(o1), ReflectUtil.getMethodByName(o1, "getOrder"));
            int order2 = ReflectUtil.invoke(applicationContext.getBean(o2), ReflectUtil.getMethodByName(o2, "getOrder"));
            return Integer.compare(order1, order2);
        });

        queue.addAll(yzFilters);
    }
}
