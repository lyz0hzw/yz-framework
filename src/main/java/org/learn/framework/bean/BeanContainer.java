package org.learn.framework.bean;

import java.util.Set;

/**
 * Bean的集装箱，用以存储已经装配好的Bean对象
 */
public interface BeanContainer {

    /**
     * Bean对象是否存在
     * @param clazz Bean类型
     * @return 是否存在
     */
    boolean exists(Class<?> clazz);

    /**
     * 移除Bean
     * @param clazz Bean类型
     */
    void remove(Class<?> clazz);

    /**
     * 设置Bean
     * @param clazz Bean类型
     */
    void setBean(Class<?> clazz);

    /**
     * 设置Bean
     * @param bean Bean对象
     */
    void setBean(Object bean);

    /**
     * 获取Bean
     * @param clazz Bean类型
     * @param <T> Bean泛型
     * @return Bean对象
     */
    <T> T getBean(Class<T> clazz);

    /**
     * 获取全部装配好的Bean
     * @return Bean对象集合
     */
    Set<Class<?>> getBeans();
}
