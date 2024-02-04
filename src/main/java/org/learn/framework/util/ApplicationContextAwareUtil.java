package org.learn.framework.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.learn.framework.annotation.Priority;
import org.learn.framework.context.Level;
import org.learn.framework.context.ContextContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author yixi
 */
public class ApplicationContextAwareUtil {

    /**
     * 优先处理集合
     */
    private static final List<Class<?>> MAX_AWARE_LIST = new ArrayList<>();

    /**
     * 正常处理集合
     */
    private static final List<Class<?>> MIDDLE_AWARE_LIST = new ArrayList<>();

    /**
     * 延迟处理集合
     */
    private static final List<Class<?>> MIN_AWARE_LIST = new ArrayList<>();

    /**
     * 待移除处理集合
     */
    private static final List<Class<?>> REMOVE_CLASS_LIST = new ArrayList<>();

    private ApplicationContextAwareUtil(){}

    /**
     * 按照不同优先级，初始化ApplicationContextAware实例集合
     * @param classes 所有实现ApplicationContextAware接口的类
     */
    public static void initialize(Set<Class<?>> classes){
        classes.forEach(clazz -> {
            Priority priority = clazz.getAnnotation(Priority.class); // 返回一个类上的Priority注解，没有的话返回null
            if (priority == null) {
                MIN_AWARE_LIST.add(clazz); // 默认最低优先级
            }else {
                Level value = priority.value();
                switch (value) {
                    case MAX -> MAX_AWARE_LIST.add(clazz);
                    case Middle -> MIDDLE_AWARE_LIST.add(clazz);
                    case MIN -> MIN_AWARE_LIST.add(clazz);
                    default -> {
                    }
                }
            }
        });
    }

    public static void removeClass(Class<?> removeClazz){
        if (removeClazz != null){
            REMOVE_CLASS_LIST.add(removeClazz);
            Log.get().info("已移除组件[{}]", removeClazz.getName());
        }
    }

    public static List<Class<?>> getRemoveClassList(){
        return REMOVE_CLASS_LIST;
    }

    private static void beforeInvoke(){
        if (REMOVE_CLASS_LIST.size() == 0) {
            return;
        }
        for (Class<?> clazz : REMOVE_CLASS_LIST) { // 遍历移除组件
            Priority priority = clazz.getAnnotation(Priority.class);
            if (priority == null) {
                MIN_AWARE_LIST.remove(clazz);
            }else {
                Level value = priority.value();
                switch (value) {
                    case MAX -> MAX_AWARE_LIST.remove(clazz);
                    case Middle -> MIDDLE_AWARE_LIST.remove(clazz);
                    case MIN -> MIN_AWARE_LIST.remove(clazz);
                    default -> {
                    }
                }
            }
        }
    }

    /**
     * 按优先级调用invokeAwareList方法
     */
    public static void invoke(){
        // 反射执行前的处理操作
        beforeInvoke();

        // 反射执行invokeAwareList方法
        invokeAwareList(MAX_AWARE_LIST);
        invokeAwareList(MIDDLE_AWARE_LIST);
        invokeAwareList(MIN_AWARE_LIST);
    }

    /**
     * 通过反射执行setApplicationContext方法
     * @param list ApplicationContextAware实例集合
     */
    private static void invokeAwareList(List<Class<?>> list){
        list.forEach(aClass ->
                ReflectUtil.invoke(ReflectUtil.newInstance(aClass), // 通过反射进行实例化，然后执行setApplicationContext方法
                Objects.requireNonNull(   // 将ApplicationContext对象注入这个类
                        ReflectUtil.getMethodByName(aClass,"setApplicationContext"))
                        , ContextContainer.getApplicationContext()));
    }

    /**
     * 清除所有感知接口实现类集合
     */
    public static void clear(){
        MAX_AWARE_LIST.clear();
        MIDDLE_AWARE_LIST.clear();
        MIN_AWARE_LIST.clear();
        REMOVE_CLASS_LIST.clear();
    }
}
