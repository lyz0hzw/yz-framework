package org.learn.framework.annotation;


import org.learn.framework.web.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在类或方法上
 * 用以标记API路径
 * 默认采用类名或方法名
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD})
public @interface RequestMapping {

    String value() default "";

    RequestMethod[] methods() default {RequestMethod.GET
            ,RequestMethod.POST
            ,RequestMethod.PUT
            ,RequestMethod.DELETE};

}
