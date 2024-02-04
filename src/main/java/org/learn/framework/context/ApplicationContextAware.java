package org.learn.framework.context;

/**
 * 环境上下文感知
 */
public interface ApplicationContextAware {

    /**
     * 配置上下文信息
     * @param applicationContext 配置上下文对象
     */
    void setApplicationContext(ApplicationContext applicationContext);
}
