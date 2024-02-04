package org.learn.framework.context;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 类与类中的特定方法
 */
public class YzClass {

    private Class<?> clazz;

    private Method method;

    private Map<String, List<String>> params;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }


    public void setMethod(Method method) {
        this.method = method;
    }

    public String getParameter(String name){
        List<String> values = params.get(name);
        return values != null ? values.get(0) : "";
    }

    public Map<String, List<String>> getParams() {
        return params;
    }

    public void setParams(Map<String, List<String>> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "YzClass{" +
                "clazz=" + clazz +
                ", method=" + method +
                ", params=" + params +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YzClass cogoClass = (YzClass) o;
        return Objects.equals(clazz, cogoClass.clazz) && Objects.equals(method, cogoClass.method) && Objects.equals(params, cogoClass.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, method, params);
    }
}
