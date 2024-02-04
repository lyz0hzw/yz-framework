package org.learn.framework.log;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 日志创建工厂
 */
public class YzLogFactory extends LogFactory {

    public static final String LOG_FACTORY_NAME = "YzLogFactory";

    public YzLogFactory(String name) {
        super(name);
    }

    @Override
    public Log createLog(String s) {
        return new YzLog(s);
    }

    @Override
    public Log createLog(Class<?> aClass) {
        return new YzLog(ClassUtil.getShortClassName(aClass.getName()));
    }
}