package org.learn.framework.annotation;

import cn.hutool.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface ComponentScan {
    @AliasFor(attribute = "basePackages")
    String[] value() default {};

    @AliasFor(attribute = "value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
