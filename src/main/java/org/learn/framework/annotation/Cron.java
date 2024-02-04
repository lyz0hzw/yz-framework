package org.learn.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongda.li@hand-china.com 2022/3/16 13:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD})
public @interface Cron {

    /**
     * Cron表达式
     * @return 表达式字符串
     */
    String value();
}
