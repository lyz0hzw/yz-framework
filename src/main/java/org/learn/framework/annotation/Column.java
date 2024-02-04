package org.learn.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yixi
 * 标记在实体类字段上
 * 用以实现JavaBean到数据库的ORM字段映射
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Column {

    String value() default "";
}
