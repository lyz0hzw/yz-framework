package org.learn.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在方法上
 * 声明方法返回值为一个Java对象
 * 多用于JavaConfig配置类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Bean {

    /**
     * @return Java对象的名称
     */
    String value() default "";
}
