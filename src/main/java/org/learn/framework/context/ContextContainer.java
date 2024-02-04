package org.learn.framework.context;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/7 14:59
 * @title LyzContextContainer
 * @description <TODO description class purpose>
 */

public class ContextContainer implements ApplicationContext {
    private static ContextContainer APPLICATION_CONTEXT = new ContextContainer();

    private final Map<String, Class<?>> NAME_MAP;

    private final Map<Class<?>, Object> BEAN_MAP;

    private ContextContainer(){
        this.NAME_MAP = new ConcurrentHashMap<>();
        this.BEAN_MAP = new ConcurrentHashMap<>();
    }

    public static ContextContainer getApplicationContext(){
        return APPLICATION_CONTEXT;
    }

    @Override
    public boolean containsBean(String beanName) {
        return NAME_MAP.containsKey(beanName) && BEAN_MAP.containsKey(NAME_MAP.get(beanName));
    }

    @Override
    public boolean containsBean(Class<?> beanClass) {
        return BEAN_MAP.containsKey(beanClass);
    }

    @Override
    public boolean containsBean(Object bean) {
        return BEAN_MAP.containsKey(bean.getClass());
    }

    @Override
    public Set<String> getNames() {
        return NAME_MAP.keySet();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return BEAN_MAP.keySet();
    }

    @Override
    public Object getBean(Class<?> beanClass) {
        Object o = BEAN_MAP.get(beanClass);
        if (o != null){
            return beanClass.cast(o);
        }
        return null;
    }

    @Override
    public Object getBean(String beanName) {
        return BEAN_MAP.get(NAME_MAP.get(beanName));
    }



    /**
     * 首字母小写
     * @param beanName 类名
     * @return 首字母小写
     */
    private String formatName(String beanName) {
        if (Character.isLowerCase(beanName.charAt(0))) {
            return beanName;
        } else {
            return Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
        }
    }
    private void showSetBeanInfo(String beanName,String className){
        Log.get().info("Bean对象设置成功[{}][{}]",className,beanName);
    }

    @Override
    public void setBean(Object bean) {
        BEAN_MAP.put(bean.getClass(), bean);
        NAME_MAP.put(formatName(bean.getClass().getSimpleName()), bean.getClass());
        showSetBeanInfo(formatName(bean.getClass().getSimpleName()), bean.getClass().getName());
    }

    @Override
    public void setBean(Class<?> beanClass) {
        Object instance = ReflectUtil.newInstance(beanClass);
        BEAN_MAP.put(beanClass, instance);
        NAME_MAP.put(formatName(beanClass.getSimpleName()), beanClass);
        showSetBeanInfo(formatName(beanClass.getSimpleName()), beanClass.getName());
    }

    @Override
    public void setBean(Object bean, String beanName) {
        if ("".equals(beanName)){
            setBean(bean);
        } else {
            BEAN_MAP.put(bean.getClass(), bean);
            NAME_MAP.put(formatName(beanName), bean.getClass());
            showSetBeanInfo(formatName(beanName), bean.getClass().getName());
        }
    }

    @Override
    public void setBean(Class<?> beanClass, String beanName) {
        if ("".equals(beanName)){
            setBean(beanClass);
            return;
        }
        Object instance = ReflectUtil.newInstance(beanClass);
        BEAN_MAP.put(beanClass, instance);
        NAME_MAP.put(beanName, beanClass);
        showSetBeanInfo(beanName, beanClass.getName());
    }

    @Override
    public void setBeanByProxy(Class<?> beanClass, Object bean, String beanName) {
        if ("".equals(beanName)) {
            NAME_MAP.put(formatName(beanClass.getSimpleName()),beanClass);
            showSetBeanInfo(formatName(beanClass.getSimpleName()), beanClass.getName());
        }else {
            NAME_MAP.put(beanName, beanClass);
            showSetBeanInfo(beanName, beanClass.getName());
        }
        BEAN_MAP.put(beanClass, bean);
    }

    @Override
    public void setBeanByProxy(Class<?> beanClass, Object bean) {
        NAME_MAP.put(formatName(beanClass.getSimpleName()),beanClass);
        showSetBeanInfo(formatName(beanClass.getSimpleName()), beanClass.getName());
        BEAN_MAP.put(beanClass, bean);
    }

    @Override
    public void removeBean(Class<?> beanClass) {
        destroyBean(getBean(beanClass));
        Object remove = BEAN_MAP.remove(beanClass);
        NAME_MAP.remove(formatName(beanClass.getSimpleName()));
        if (remove != null){
            Log.get().info("Bean对象移除成功[{}]", beanClass.getName());
        }
    }

    @Override
    public void removeAll() {
        for (Iterator<Map.Entry<Class<?>, Object>> it = BEAN_MAP.entrySet().iterator(); it.hasNext();){
            Map.Entry<Class<?>, Object> item = it.next();
            destroyBean(item.getValue());
            it.remove();
            Log.get().info("Bean对象移除成功[{}]", item.getKey().getName());
        }
        NAME_MAP.clear();
    }

    @Override
    public void initBean(Object bean) {
        if (bean instanceof InitBean) {
            ((InitBean) bean).init();
        }
    }

    @Override
    public void destroyBean(Object bean) {
        if (bean instanceof DestroyBean) {
            ((DestroyBean) bean).destroy();
        }
    }
}
