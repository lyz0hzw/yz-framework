package org.learn.test.example;

import org.learn.framework.context.ContextContainer;
import org.learn.framework.util.ClassUtils;
import org.learn.framework.util.ApplicationContextAwareUtil;
import org.learn.test.example.project.inner.A;
import org.learn.test.example.project.inner.pack2.injectApplicationContext;

import java.util.Set;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/7 18:55
 * @title TestApplicationContextAware
 * @description <TODO description class purpose>
 */

public class TestApplicationContextAware {
    public static void main(String[] args) {
        ClassUtils.setMainClass(A.class);
        ClassUtils.initializeMainClass();

        Set<Class<?>> classSet = ClassUtils.getClassesWithoutInterface();
        // 查找带有@的类，并为其注入ApplicationContext
        ApplicationContextAwareUtil.initialize(classSet);
        ApplicationContextAwareUtil.invoke();
        injectApplicationContext bean = (injectApplicationContext)ContextContainer.getApplicationContext().getBean(injectApplicationContext.class);



    }
}
