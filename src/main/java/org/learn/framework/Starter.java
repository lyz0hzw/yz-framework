package org.learn.framework;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.learn.Main;
import org.learn.framework.annotation.BootApplication;
import org.learn.framework.cache.CacheBuilder;
import org.learn.framework.context.*;
import org.learn.framework.data.SessionCache;
import org.learn.framework.exception.StarterException;
import org.learn.framework.log.LogConstance;
import org.learn.framework.log.YzLogFactory;
import org.learn.framework.util.ApplicationContextAwareUtil;
import org.learn.framework.util.ClassUtils;

import java.util.Set;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/7 14:17
 * @title Starter
 * @description <TODO description class purpose>
 */

public class Starter {
    private static final TimeInterval INTERVAL = new TimeInterval();
    private static Log log;

    public static ApplicationContext run(Class<?> clazz) {
        INTERVAL.start();
        showBanner();
        LogFactory.setCurrentLogFactory(new YzLogFactory(YzLogFactory.LOG_FACTORY_NAME));
        log = Log.get();
        BootApplication bootApplication = clazz.getAnnotation(BootApplication.class);
        if (bootApplication == null) {
            log.error(StarterException.NONE_ANNOTATION);
            throw new StarterException(StarterException.NONE_ANNOTATION);
        }
        log.info("开始启动[{}]", clazz.getName());
        // 设置启动主类
        ClassUtils.setMainClass(clazz);
        // 初始化所有类
        ClassUtils.initializeMainClass();
        // 排除不需要的类
        ClassUtils.excludeClass();
        // 初始化感知接口
        ApplicationContextAwareUtil.initialize(ClassUtils.getClassesExtendClass(ApplicationContextAware.class));
        try {
            // 执行感知接口
            ApplicationContextAwareUtil.invoke();
        } catch (Exception e) {
            log.error("启动失败[{}]", clazz.getName());
            e.printStackTrace();
            throw new StarterException(e.getMessage());
        }

        log.info("[{}]启动成功，启动用时[{}ms]", clazz.getName(), INTERVAL.intervalMs());
        log.info("===============================================================================================");
        return ContextContainer.getApplicationContext();
    }
    public static void main(String[] args){
        run(Main.class);
        ClassUtils.getClassesWithoutInterface().forEach(clazz -> System.out.println(clazz));
    }
    private static void showBanner() {
        String banner = "   _____                      _____                   _             \n" +
                "  / ____|                    |  __ \\                 (_)            \n" +
                " | |     ___   __ _  ___     | |__) |   _ _ __  _ __  _ _ __   __ _ \n" +
                " | |    / _ \\ / _` |/ _ \\    |  _  / | | | '_ \\| '_ \\| | '_ \\ / _` |\n" +
                " | |___| (_) | (_| | (_) |   | | \\ \\ |_| | | | | | | | | | | | (_| |\n" +
                "  \\_____\\___/ \\__, |\\___/    |_|  \\_\\__,_|_| |_|_| |_|_|_| |_|\\__, |\n" +
                "               __/ |                                           __/ |\n" +
                "              |___/                                           |___/ ";
        System.out.format("\33[%d;2m%-12s", LogConstance.INFO, banner);
        System.out.format("%s", LogConstance.LINE_SEPARATOR);
    }

    /**
     * 优雅关闭程序
     * 先按优先级执行实现了shutdown接口的方法
     * 再移除所有容器中的bean，并执行他们的destroy方法
     * 最后正常退出程序
     */
    public static void shutdown() {
        log.debug("程序即将关闭");
        clear();
        System.exit(0);
    }

    /**
     * 清除框架全部运行数据
     */
    public static void clear() {
        // 如果Bean实现了Shutdown接口，则按照shutdown的关闭顺序的大小来执行关闭方法
        Set<Class<?>> shutdowns = ClassUtils.getClassesExtendClass(Shutdown.class);
        BoundedPriorityQueue<Class<?>> queue
                = new BoundedPriorityQueue<>(Math.max(shutdowns.size(), 1)
                , (o1, o2) -> {
            int order1 = ReflectUtil.invoke(
                    ReflectUtil.newInstance(o1)
                    , ReflectUtil.getMethodByName(o1, "getOrder"));
            int order2 = ReflectUtil.invoke(
                    ReflectUtil.newInstance(o2)
                    , ReflectUtil.getMethodByName(o2, "getOrder"));
            return Integer.compare(order1, order2);
        });
        queue.addAll(shutdowns);
        queue.toList().forEach(shutdown -> ReflectUtil.invoke(ReflectUtil.newInstance(shutdown), ReflectUtil.getMethodByName(shutdown, "shutdown")));
        ContextContainer.getApplicationContext().removeAll();
        // 如果Bean实现了DestroyBean接口，则执行Bean的销毁方法
        ClassUtils.getClassesExtendClass(DestroyBean.class).forEach(clazz -> {
            // 因为容器中的Bean已经被移除，所以只需要销毁不属于容器的Bean即可
            if (! ContextContainer.getApplicationContext().containsBean(clazz)) {
                ReflectUtil.invoke(ReflectUtil.newInstance(clazz), ReflectUtil.getMethodByName(clazz, "destroy"));
            }
        });

        // 关闭线程池
        log.debug("开始关闭线程池");
        GlobalThreadPool.shutdown(true);
        // 清空组件扫描器
        log.debug("开始清空组件扫描器");
        ClassUtils.getClasses().clear();
        // 清空感知接口集合
        log.debug("开始清空感知接口集合");
        ApplicationContextAwareUtil.clear();
        // 清空任务调度集合
        log.debug("开始清空任务调度集合");
//        JobAware.getJobList().clear();
        // 清空数据库会话缓存
        log.debug("开始清空数据库会话缓存");
        SessionCache.clear();
        // 清空全局缓存池
        log.debug("开始清空全局缓存池");
        CacheBuilder.clear();
        // 清空环境配置信息
        log.debug("开始清空环境配置信息");
        Environment.clearPropsCache();
        Environment.clearConfigCache();
    }
}
