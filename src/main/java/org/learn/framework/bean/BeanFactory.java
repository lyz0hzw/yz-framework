package org.learn.framework.bean;

import cn.hutool.core.util.ReflectUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hongda.li 2022-04-10 22:51
 * 组件工厂，负责组件的自动装配
 */
public class BeanFactory implements BeanContainer{
    private static final Map<Class<?>, Object> ASSEMBLY_LINE = new ConcurrentHashMap<>(1024);
//    private static final Log log = LogFactory.getLog();

    private static final BeanContainer CONTAINER = new BeanFactory();
    private BeanFactory(){}

    public static BeanContainer getContainer(){
        return BeanFactory.CONTAINER;
    }

    @Override
    public boolean exists(Class<?> clazz) {
        return ASSEMBLY_LINE.containsKey(clazz);
    }

    @Override
    public void remove(Class<?> clazz) {
        ASSEMBLY_LINE.remove(clazz);
    }

    @Override
    public void setBean(Class<?> clazz) {
        ASSEMBLY_LINE.put(clazz, ReflectUtil.newInstance(clazz));
//        log.info("set bean[{}]", clazz.getName());
    }

    @Override
    public void setBean(Object bean) {
        ASSEMBLY_LINE.put(bean.getClass(), bean);
//        log.info("set bean[{}]", bean.getClass().getName());
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        if (ASSEMBLY_LINE.containsKey(clazz)){
            return (T) ASSEMBLY_LINE.get(clazz);
        }
        Class<?> beanClass = getBeans().stream()
                .filter(clazz::isAssignableFrom)
                .findFirst()
                .orElse(null);
        return (T) ASSEMBLY_LINE.get(beanClass);
    }

    @Override
    public Set<Class<?>> getBeans() {
        return ASSEMBLY_LINE.keySet();
    }
}
