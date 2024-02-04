package org.learn.framework.web.tomcat;


import org.learn.framework.annotation.Exclude;
import org.learn.framework.web.WebFilterAware;
import org.learn.framework.web.simple.WebServerAware;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongda.li 2022-04-01 11:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Exclude({WebServerAware.class, WebFilterAware.class}) // 移除自建服务器感知和过滤器感知
public @interface EnableTomcat {
}
