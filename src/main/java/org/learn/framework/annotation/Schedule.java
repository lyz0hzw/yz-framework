package org.learn.framework.annotation;


import org.learn.framework.task.JobUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongda.li@hand-china.com 2022/3/16 15:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD})
public @interface Schedule {

    long delay() default 0;

    long period() default 1;

    String startTime() default "";

    JobUnit unit() default JobUnit.SECOND;
}
