package org.learn.framework.context;

/**
 * @author hongda.li@hand-china.com 2022/3/10 16:45
 */
public interface ExcludeClass {

    /**
     * 排除实现了ApplicationContextAware的指定类
     * @return 返回指定类
     */
    Class<? extends ApplicationContextAware> exclude();
}
