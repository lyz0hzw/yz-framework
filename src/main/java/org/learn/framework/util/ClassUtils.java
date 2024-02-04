package org.learn.framework.util;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import org.learn.framework.annotation.ComponentScan;
import org.learn.framework.annotation.Exclude;
import org.learn.framework.annotation.BootApplication;
import org.learn.framework.context.ExcludeClass;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {

    private static final String LYZ_FRAMEWORK_PACKAGE = "org.learn.framework";

    /**
     * 应用类容器
     */
    private static final Set<Class<?>> CLASS_SET = new HashSet<>();

    /**
     * 应用启动主类
     */
    private static Class<?> mainClass;

    /**
     * 设置主类
     * @param mainClass 主类
     */
    public static void setMainClass(Class<?> mainClass){
        ClassUtils.mainClass = mainClass;
    }

    /**
     * 获取主类
     * @return 主类
     */
    public static Class<?> getMainClass() {
        return mainClass;
    }

    /**
     * 判断主类上是否有某注解
     * @param annotationType 特定注解
     * @return 是否有某注解
     */
    public static boolean mainClassWithAnnotation(Class<? extends Annotation> annotationType){
        return AnnotationUtil.hasAnnotation(mainClass, annotationType);
    }

    /**
     * 初始化类信息
     * @param classes 所有类的集合
     */
    public static void initialize(Set<Class<?>> classes){
        CLASS_SET.addAll(classes);
    }

    public static void initializeMainClass(){
        // 以主类为基准，初始化扫描当前工程下的所有类
        initialize(ClassUtil.scanPackage(ClassUtil.getPackage(mainClass)));
        // 初始化yz-Framework下的的所有类
        initialize(ClassUtil.scanPackage(LYZ_FRAMEWORK_PACKAGE));
        // 初始化用户自定义扫描目录下的所有类
        for (String packageName : mainClass.getAnnotation(BootApplication.class).scanBasePackages()) {
            initialize(ClassUtil.scanPackage(packageName));
        }
        // 扫描有ComponentScan注解的类，并初始化其注解值，但是需要确保该类可以被扫描到
        for (Class<?> classWithComponentScan : getClassesWithAnnotation(ComponentScan.class, false)) {
            for (String packageName : classWithComponentScan.getAnnotation(ComponentScan.class).value()) {
                initialize(ClassUtil.scanPackage(packageName));
            }
        }
    }

    /**
     * 获取所有类
     * @return 类集合
     */
    public static Set<Class<?>> getClasses(){
        return CLASS_SET;
    }
    public static Set<Class<?>> getClassesWithoutInterface(){
        return CLASS_SET.stream()
                .filter(clazz -> !clazz.isAnnotation()) // 非注解类
                .filter(clazz -> !clazz.isInterface()) // 非接口类
                .collect(Collectors.toSet());
    }

    /**
     * 获取带有特定注解的类集合，排除接口
     * @param annotationType 特定注解
     * @return 类集合
     */
    public static Set<Class<?>> getClassesWithAnnotation(Class<? extends Annotation> annotationType){
        return getClassesWithAnnotation(annotationType,true);
    }

    /**
     * 获取带有组合注解的类集合
     * @param annotationType 组合注解
     * @return 类集合
     */
    public static Set<Class<?>> getClassWithCombination(Class<? extends Annotation> annotationType, boolean excludeInterface){
        if (excludeInterface){
            return CLASS_SET.stream()
                    .filter(clazz -> AnnotationUtil.hasAnnotation(clazz, annotationType))
                    .filter(clazz -> !clazz.isAnnotation()) // 非注解类
                    .filter(clazz -> !clazz.isInterface()) // 非接口类
                    .collect(Collectors.toSet());
        }
        return CLASS_SET.stream()
                .filter(clazz -> AnnotationUtil.hasAnnotation(clazz, annotationType))
                .collect(Collectors.toSet());
    }

    public static Set<Class<?>> getClassesWithAnnotation(Class<? extends Annotation> annotationType, boolean excludeInterface){
        if (excludeInterface) {
            return CLASS_SET.stream()
                    .filter(clazz -> clazz.getAnnotation(annotationType) != null)
                    .filter(clazz -> !clazz.isAnnotation())
                    .filter(clazz -> !clazz.isInterface())
                    .collect(Collectors.toSet());
        }
        return CLASS_SET.stream()
                .filter(clazz -> clazz.getAnnotation(annotationType) != null)
                .collect(Collectors.toSet());
    }

    /**
     * 获取继承、实现特定类或接口的集合（不包含父类或接口）
     * @param extendsClass 父类或接口
     * @return 类集合
     */
    public static Set<Class<?>> getClassesExtendClass(Class<?> extendsClass){
        return CLASS_SET.stream()
                .filter(extendsClass::isAssignableFrom) // 一个继承/实现extendClass的子类
                .filter(clazz -> clazz != extendsClass)
                .collect(Collectors.toSet());
    }

    public static void excludeClass(){
        // 根据主类注解的excludes()属性排除特定类, 不注入ApplicationContext
        for (Class<?> exclude : mainClass.getAnnotation(BootApplication.class).excludes()) {
            ApplicationContextAwareUtil.removeClass(exclude);
        }
        // 根据Exclude注解排除特定类
        ClassUtils.getClassWithCombination(Exclude.class, true).forEach(excludeClazz
                -> {
            Class<?>[] value = AnnotationUtil.getAnnotation(excludeClazz, Exclude.class).value();
            for (Class<?> aClass : value) {
                ApplicationContextAwareUtil.removeClass(aClass);
            }
        });
        // 根据ExcludeClass接口排除特定类
        ClassUtils.getClassesExtendClass(ExcludeClass.class).forEach(exclude -> ApplicationContextAwareUtil.removeClass(
                ReflectUtil.invoke(ReflectUtil.newInstance(exclude),
                        ReflectUtil.getMethodByName(exclude, "exclude"))));
    }
}
