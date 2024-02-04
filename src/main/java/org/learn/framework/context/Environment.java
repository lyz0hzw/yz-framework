package org.learn.framework.context;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Environment {

    public static final String YZ_STORAGE_ROOT = "/yz/storage/";

    /**
     * 配置文件本地缓存
     */
    private static final Map<String, Props> PROPS_MAP = new HashMap<>();

    private static final Map<Class<?>, Object> CONFIG_MAP = new HashMap<>();

    public static void clearPropsCache(){
        PROPS_MAP.clear();
    }

    public static String getSystemTime() {
        return DatePattern.NORM_DATETIME_MS_FORMAT.format(new Date());
    }

    public static String generateRootPath(String path){
        return Environment.YZ_STORAGE_ROOT + StrUtil.removePrefix(StrUtil.removePrefix(path, "/"), "\\");
    }

    /**
     * 先从配置缓存池中获取，再从应用上下文中获取
     * @param tClass 配置类型
     * @param <T> 泛型
     * @return 配置实例
     */
    public static <T> T getConfig(Class<T> tClass) {
        Object config = CONFIG_MAP.get(tClass);
        if (config != null){
            return (T) config;
        }
        T tConfig = getBean(tClass);
        CONFIG_MAP.put(tClass, tConfig);
        return tConfig;
    }

    public static void clearConfigCache(){
        CONFIG_MAP.clear();
    }

    public static Props getProps(String path) {
        Props props = PROPS_MAP.get(path);
        if (props != null) {
            return props;
        } else {
            // 尝试通过路径去加载配置文件
            Props newProp = null;
            try {
                newProp = new Props(path);
            } catch (NoResourceException e) {
                PROPS_MAP.put(path, null);
                return null;
            }
            PROPS_MAP.put(path, newProp);
            return newProp;
        }
    }

    public static Object getProps(String path, String propertyName) {
        Props props = PROPS_MAP.get(path);
        if (props != null) {
            return props.get(propertyName);
        } else {
            Props newProp = new Props(path);
            PROPS_MAP.put(path, newProp);
            return newProp.get(propertyName);
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) ContextContainer.getApplicationContext().getBean(clazz);
    }

    public static Object getBean(String beanName) {
        return ContextContainer.getApplicationContext().getBean(beanName);
    }

    public static boolean conditionOn(Class<?> clazz){
        return ContextContainer.getApplicationContext().containsBean(clazz);
    }

    public static boolean conditionOn(Object bean){
        return ContextContainer.getApplicationContext().containsBean(bean);
    }

    public static boolean conditionOn(String packageName){
        return ClassUtil.scanPackage(packageName).size() > 0;
    }
}
