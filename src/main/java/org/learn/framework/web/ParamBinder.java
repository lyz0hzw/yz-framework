package org.learn.framework.web;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import org.learn.framework.annotation.PathVariable;
import org.learn.framework.annotation.RequestParam;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.YzClass;
import org.learn.framework.util.ParameterUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 将形参列表与方法执行的入口进行绑定，使用反射进行执行方法
 */
public class ParamBinder {

    private final YzClass yzClass;

    private final HttpServerRequest request;

    private final HttpServerResponse response;

    public ParamBinder(YzClass yzClass, HttpServerRequest request, HttpServerResponse response){
        this.yzClass = yzClass;
        this.request = request;
        this.response = response;
    }
    /**
     * 获取方法的参数名称
     * @return 参数名称集合
     */
    private List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<>();
        String[] parameterName = ParameterUtil.getParameterName(yzClass.getMethod());
        Parameter[] parameters = yzClass.getMethod().getParameters();
//        for (Parameter parameter : yzClass.getMethod().getParameters()) {
        for (int i = 0 ; i < parameterName.length ; ++i) {
            Parameter parameter = parameters[i];
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if (requestParam == null && pathVariable == null) {
                parameterNames.add(parameterName[i]); // ParameterUtil.getName(parameter)
            } else if (requestParam != null && pathVariable == null){
                parameterNames.add(requestParam.value());
            } else {
                parameterNames.add(pathVariable.value());
            }
        }
        return parameterNames;
    }

    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        Object instance = ContextContainer.getApplicationContext().getBean(yzClass.getClazz());
        // 参数类型列表
        Parameter[] parameters = yzClass.getMethod().getParameters();
        // 参数名列表
        List<String> parameterNames = getParameterNames();
        // 参数值列表
        List<Object> parameterValues = new ArrayList<>();
        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getType() == HttpServerRequest.class) {
                    parameterValues.add(request);
                } else if (parameters[i].getType() == HttpServerResponse.class) {
                    parameterValues.add(response);
                } else if (parameters[i].getType() == Integer.class || parameters[i].getType() == int.class) {
                    parameterValues.add(Convert.toInt(yzClass.getParameter(parameterNames.get(i)))); // 通过参数名取出参数列表对应的参数值
                } else if (parameters[i].getType() == Double.class || parameters[i].getType() == double.class) {
                    parameterValues.add(Convert.toDouble(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == Long.class || parameters[i].getType() == long.class) {
                    parameterValues.add(Convert.toLong(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == Boolean.class || parameters[i].getType() == boolean.class) {
                    parameterValues.add(Convert.toBool(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == String.class) {
                    parameterValues.add(yzClass.getParameter(parameterNames.get(i)));
                } else if (parameters[i].getType() == UploadFile.class) {
                    parameterValues.add(request.getMultipart().getFile(parameterNames.get(i)));
                } else {
                    Class<?> aClass = parameters[i].getType();
                    parameterValues.add(getBean(aClass));
                }
            }
        }
        return yzClass.getMethod().invoke(instance, parameterValues.toArray());
    }


    public Object getBean(Class<?> tClass) {
        try {
            Object instance = ReflectUtil.newInstance(tClass);
            Field[] fields = ReflectUtil.getFields(tClass);
            for (Field field : fields) {
                String parameter = yzClass.getParameter(field.getName());
                if (null != parameter) {
                    Method setMethod;
                    if (!field.getName().startsWith("is")) {
                        setMethod = ReflectUtil.getMethodByNameIgnoreCase(tClass, "set" + field.getName());
                    } else {
                        setMethod = ReflectUtil.getMethodByNameIgnoreCase(tClass, "set" + field.getName().substring(2));
                    }
                    ReflectUtil.invoke(instance, setMethod, parameter);
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
