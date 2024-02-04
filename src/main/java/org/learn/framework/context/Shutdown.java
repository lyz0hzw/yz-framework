package org.learn.framework.context;

/**
 * @author hongda.li@hand-china.com 2022/3/16 16:36
 */
public interface Shutdown {

    /**
     * 关闭优先级
     * @return 返回优先级
     */
    int getOrder();

    /**
     * 下线方法
     */
    void shutdown();
}
