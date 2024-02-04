package org.learn.framework.aop;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.learn.framework.annotation.Priority;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.Level;
import org.learn.framework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author hongda.li@hand-china.com 2022/3/18 10:18
 */
@Priority(Level.Middle)
public class AopAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ClassUtils.getClassesWithAnnotation(Aspect.class).forEach(clazz -> {
            Aspect aspect = clazz.getAnnotation(Aspect.class);
            // 需要代理的类或者接口
            Class<?> target = aspect.target();
            // 需要代理的方法
            String[] methods = aspect.methods();
            // 开始执行代理逻辑
            invokeProxy(clazz, target, methods);
        });
    }

    private void invokeProxy(Class<?> aspect, Class<?> target, String[] methods) {
        ApplicationContext context = ContextContainer.getApplicationContext(); // bean管理器
        Object aspectInstance = context.getBean(aspect); // 切面实例
        // 带有过滤注解的方法
        Method[] beforeMethods = ReflectUtil.getMethods(aspect, method -> method.getAnnotation(Before.class) != null);
        Method[] afterMethods = ReflectUtil.getMethods(aspect, method -> method.getAnnotation(After.class) != null);
        Method[] exceptionMethods = ReflectUtil.getMethods(aspect, method -> method.getAnnotation(Exception.class) != null);

        Object targetInstance = context.getBean(target); // 获取被代理类对象实例
        Class<?> targetInterface = target.getInterfaces().length > 0 ? target.getInterfaces()[0] : null;
        if (targetInterface == null) {
            Log.get().info( "代理类缺少接口[{}]", target.getName());
            return;
        }
        Object proxyInstance = YzProxy.newProxyInstanceByInstance(
                targetInterface
                , targetInstance != null ? targetInstance : ReflectUtil.newInstance(target)
                , new cn.hutool.aop.aspects.Aspect() {
                    @Override
                    public boolean before(Object o, Method method, Object[] objects) {
                        return invokeBeforeOrAfter(o, method, objects, methods, beforeMethods, aspectInstance);
                    }

                    @Override
                    public boolean after(Object o, Method method, Object[] objects, Object o1) {
                        return invokeBeforeOrAfter(o, method, objects, methods, afterMethods, aspectInstance);
                    }

                    @Override
                    public boolean afterException(Object o, Method method, Object[] objects, Throwable throwable) {
                        if (isNotProxyMethod(methods, method.getName())) {
                            return true;
                        }
                        if (exceptionMethods.length > 0) {
                            ProxyParam param = new ProxyParam();
                            param.setTarget(o);
                            param.setMethod(method);
                            param.setParams(objects);
                            param.setThrowable(throwable);
                            Method exceptionMethod = exceptionMethods[0];
                            // TODO：这是为什么
                            Object invoke = ReflectUtil.invoke(aspectInstance, exceptionMethod, exceptionMethod.getParameterCount() == 1 ? param : null);
                            return invoke == null || (boolean) invoke;
                        }
                        return true;
                    }
                });
        context.setBeanByProxy(target, proxyInstance);
    }

    private boolean invokeBeforeOrAfter(Object o, Method method, Object[] objects, String[] methods, Method[] afterMethods, Object aspectInstance) {
        if (isNotProxyMethod(methods, method.getName())) {
            return true;
        }
        if (afterMethods.length > 0) {
            ProxyParam param = new ProxyParam();
            param.setTarget(o);
            param.setMethod(method);
            param.setParams(objects);
            Method afterMethod = afterMethods[0];
            Object invoke = ReflectUtil.invoke(aspectInstance, afterMethod, afterMethod.getParameterCount() == 1 ? param : null);
            return invoke == null || (boolean) invoke;
        }
        return true;
    }

    /**
     * 判断是否为代理方法
     * @param methods    代理方法列表
     * @param methodName 方法名称
     * @return 方法是否代理
     */
    private boolean isNotProxyMethod(String[] methods, String methodName) {
        if (methods.length == 1 && "*".equals(methods[0])) {
            return false;
        }
        for (String method : methods) {
            if (method.equals(methodName)) {
                return false;
            }
        }
        return true;
    }
}
