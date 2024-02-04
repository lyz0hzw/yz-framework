package org.learn.framework.context;

import java.util.Set;

/**
 * 该接口主要来规范容器中的Bean对象是非延时加载，即在创建容器对象的时候就对Bean进行初始化，并存储到一个容器中。
 */
public interface ApplicationContext {
    /**
     * 是否包含某个Java对象
     * @param beanName Java对象的名称
     * @return 是否包含
     */
    boolean containsBean(String beanName);

    /**
     * 是否包含某个Java对象
     * @param beanClass Java对象的类
     * @return 是否包含
     */
    boolean containsBean(Class<?> beanClass);

    /**
     * 是否包含某个Java对象
     * @param bean Java对象
     * @return 是否包含
     */
    boolean containsBean(Object bean);

    /**
     * 获取所有Java对象的名称
     * @return 名称集合
     */
    Set<String> getNames();
    /**
     * 获取所有Java对象的类
     * @return 类的集合
     */
    Set<Class<?>> getClasses();
    /**
     * 根据类获取Java对象
     * @param beanClass Java对象的类
     * @return Java对象
     */
    Object getBean(Class<?> beanClass);
    /**
     * 根据名称获取Java对象
     * @param beanName Java对象的名称
     * @return Java对象
     */
    Object getBean(String beanName);
    /**
     * 根据Java对象设置Java对象
     * @param bean Java对象
     */
    void setBean(Object bean);

    /**
     * 根据类设置Java对象
     * @param beanClass Java对象的类
     */
    void setBean(Class<?> beanClass);

    /**
     * 根据Java对象和Java对象的名称设置Java对象
     * @param bean Java对象
     * @param beanName Java对象的名称
     */
    void setBean(Object bean, String beanName);
    /**
     * 根据Java对象的类和Java对象的名称设置Java对象
     * @param beanClass Java对象的类
     * @param beanName Java对象的名称
     */
    void setBean(Class<?> beanClass, String beanName);
    /**
     * 通过动态代理生成的对象注入容器
     * @param beanClass Java对象代理前的类
     * @param bean Java对象代理后的实例
     * @param beanName Java对象的名称
     */
    void setBeanByProxy(Class<?> beanClass, Object bean, String beanName);

    void setBeanByProxy(Class<?> beanClass, Object bean);
    /**
     * 根据Java对象的类移除对象
     * @param beanClass Java对象的类
     */
    void removeBean(Class<?> beanClass);

    /**
     * 移除所有Java对象
     */
    void removeAll();

    /**
     * 初始化Java对象
     * @param bean 需要初始化的Bean对象
     */
    void initBean(Object bean);

    /**
     * 销毁Java对象
     * @param bean 需要销毁的Bean对象
     */
    void destroyBean(Object bean);
}
