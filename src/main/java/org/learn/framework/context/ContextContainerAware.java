package org.learn.framework.context;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.learn.framework.annotation.*;
import org.learn.framework.util.ApplicationContextAwareUtil;
import org.learn.framework.util.ClassUtils;

import java.util.Arrays;

/**
 * @description 给实现了
 */
@Priority(Level.MAX) //在这个类中，再次为Component这些注解注入
public class ContextContainerAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        // 为Component注解（包含组合注解：Service、Repository、Controller）标记的类注入容器
        ClassUtils.getClassWithCombination(Component.class, true).forEach(aClass
                -> applicationContext.setBean(aClass, AnnotationUtil.getAnnotation(aClass, Component.class).value()));

        // 为Import注解标记的需要导入的类注入容器
        ClassUtils.getClassWithCombination(Import.class, false).forEach(aClass -> {
            for (Class<?> importClass : AnnotationUtil.getAnnotation(aClass, Import.class).value()) {
                applicationContext.setBean(importClass);
                Log.get().info("导入组件[{}]", importClass.getName());
            }
        });

        // 为Configuration注解标记的类中，被Bean注解标记的方法返回值注入容器
        ClassUtils.getClassesWithAnnotation(Configuration.class)
                .forEach(
                        aClass -> Arrays.asList(ReflectUtil.getMethods(aClass,
                                method -> method.getAnnotation(Bean.class) != null)).forEach(method
                -> applicationContext.setBean((Object) ReflectUtil.invoke(ReflectUtil.newInstance(aClass), method), method.getAnnotation(Bean.class).value())));

        // 排除某些不需要被容器管理的类
        ApplicationContextAwareUtil.getRemoveClassList().forEach(applicationContext::removeBean);

        // 自动装配
        applicationContext.getClasses().forEach(aClass -> new Refresher(aClass).refresh());

        // 如果Bean实现了InitBean接口，则执行Bean的初始化方法
        ClassUtils.getClassesExtendClass(InitBean.class).forEach(clazz -> {
            if (applicationContext.containsBean(clazz)){
                applicationContext.initBean(applicationContext.getBean(clazz));
            }else {
                ReflectUtil.invoke(ReflectUtil.newInstance(clazz),ReflectUtil.getMethodByName(clazz,"init"));
            }
        });
    }
}
