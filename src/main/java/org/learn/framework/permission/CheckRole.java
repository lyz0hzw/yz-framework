package org.learn.framework.permission;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongda.li 2022-04-03 12:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface CheckRole {

    Class<? extends AbstractRole>[] value() default AbstractRole.class;
}
