package org.learn.framework.annotation;


import org.learn.framework.context.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yixi
 * 标记在实现ApplicationContextAware接口的类上
 * 声明处理该类的优先级
 * 可选优先级有MAX、MIDDLE、MIN
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Priority {

    Level value() default Level.MIN;
}
