package org.learn.framework.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明该类是一个切面类，通过targets和methods属性确定需要代理的类和类的方法
 * 若methods属性为 *，则默认代理该类的所有方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Aspect {

    Class<?> target();

    String[] methods() default {"*"};
}
