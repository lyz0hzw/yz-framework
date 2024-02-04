package org.learn.framework.aop;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.Aspect;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ContextContainer;

import java.lang.reflect.InvocationHandler;

/**
 * 代理类的返回类型强制要求是接口类型，包括该接口有无实现类两种情况
 * YzProxy会将产生的代理类实例自动注入到ApplicationContext的容器当中
 */
public class YzProxy {

    /**
     * 接口代理
     * @param interfaceClass     代理的接口
     * @param invocationHandler  被代理类通过实现此接口提供动态代理功能
     * @return 代理对象
     * @param <T> 被代理对象的类型
     */
    public static <T> T newProxyInstanceByInterface(Class<T> interfaceClass, InvocationHandler invocationHandler){
       return newProxyInstanceByInterface(interfaceClass,"", invocationHandler);
    }

    public static <T> T newProxyInstanceByInterface(Class<T> interfaceClass, String beanName, InvocationHandler invocationHandler){
        T proxyInstance = ProxyUtil.newProxyInstance(invocationHandler, interfaceClass);
        // 将代理放置到容器列表内
        ApplicationContext context = ContextContainer.getApplicationContext();
        context.setBeanByProxy(interfaceClass, proxyInstance, beanName);
        return proxyInstance;
    }

    /**
     * 对象代理
     */
    public static <T> T newProxyInstanceByInstance(Class<T> interfaceClass, Object targetClass, Aspect aspect){
        return newProxyInstanceByInstance(interfaceClass, "", targetClass, aspect);
    }

    public static <T> T newProxyInstanceByInstance(Class<T> interfaceClass, String beanName, Object targetClass, Aspect aspect){
        T proxyInstance = (T) ProxyUtil.proxy(targetClass, aspect);
        ApplicationContext context = ContextContainer.getApplicationContext();
        context.setBeanByProxy(interfaceClass, proxyInstance, beanName);
        return proxyInstance;
    }
}
