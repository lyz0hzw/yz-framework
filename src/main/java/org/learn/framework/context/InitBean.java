package org.learn.framework.context;

/**
 * @author hongda.li@hand-china.com 2022/3/16 17:09
 */
public interface InitBean {

    /**
     * 实例化Bean后会执行的方法
     */
    void init();
}
