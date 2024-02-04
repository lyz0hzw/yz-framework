package org.learn.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongda.li@hand-china.com 2022/3/17 9:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Value {

    /**
     * 普通属性注入，可以注入int、string、bool也可以对配置文件内容取值
     * @return 0 / "demo" / true / ${"yz.server.port"}
     */
    String value() default "";
}
