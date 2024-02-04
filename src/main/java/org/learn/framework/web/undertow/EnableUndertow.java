package org.learn.framework.web.undertow;


import org.learn.framework.annotation.Exclude;
import org.learn.framework.web.WebFilterAware;
import org.learn.framework.web.simple.WebServerAware;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hongda.li 2022-03-31 20:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Exclude({WebServerAware.class, WebFilterAware.class})
public @interface EnableUndertow {
}
