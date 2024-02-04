package org.learn.test.example;

import org.learn.test.example.project.inner.A;
import org.learn.framework.util.ClassUtils;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/7 16:48
 * @title TestBean
 * @description <TODO description class purpose>
 */

public class TestScanBean {
    public static void main(String[] args) {
        ClassUtils.setMainClass(A.class);
        ClassUtils.initializeMainClass();
        System.out.println("扫描主类下所有包的结果：");
//        LyzClassUtil.getClasses().forEach(clazz -> System.out.println(clazz));
        ClassUtils.getClassesWithoutInterface().forEach(clazz -> System.out.println(clazz));
    }
}
