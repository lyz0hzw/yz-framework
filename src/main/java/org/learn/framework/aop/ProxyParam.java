package org.learn.framework.aop;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Method;

/**
 * 代理参数
 */
public class ProxyParam {

    private Object target;  // 目标对象

    private Method method;   // 目标方法

    private Object[] params;  // 参数列表

    private Throwable throwable; // 可抛出异常

    public Object invoke(){
        return ReflectUtil.invoke(target,method,params);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
