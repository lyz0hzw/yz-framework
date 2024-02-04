package org.learn.framework.web.servlet;

import cn.hutool.core.util.ReflectUtil;
import org.learn.framework.annotation.PathVariable;
import org.learn.framework.annotation.RequestParam;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.YzClass;
import org.learn.framework.util.ParameterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数绑定
 */
public class ParamBinder {

    private final YzClass yzClass;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    public ParamBinder(YzClass yzClass, HttpServletRequest request, HttpServletResponse response) {
        this.yzClass = yzClass;
        this.request = request;
        this.response = response;
    }

    public Object invoke() throws InvocationTargetException, IllegalAccessException {
        Object instance = ContextContainer.getApplicationContext().getBean(yzClass.getClazz());
        Parameter[] parameters = yzClass.getMethod().getParameters();
        List<String> parameterNames = getParameterNames();
        List<Object> parameterValues = new ArrayList<>();
        if (parameters != null && parameters.length > 0){
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getType() == HttpServletRequest.class) {
                    parameterValues.add(request);
                } else if (parameters[i].getType() == HttpServletResponse.class) {
                    parameterValues.add(response);
                } else if (parameters[i].getType() == HttpSession.class) {
                    parameterValues.add(request.getSession());
//                } else if (parameters[i].getType() == LoginHelper.class) {
//                    parameterValues.add(new LoginHelper().init(request, response));
                }else if (parameters[i].getType() == Integer.class || parameters[i].getType() == int.class){
                    parameterValues.add(Integer.parseInt(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == Double.class || parameters[i].getType() == double.class) {
                    parameterValues.add(Double.parseDouble(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == Long.class || parameters[i].getType() == long.class) {
                    parameterValues.add(Long.parseLong(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == Boolean.class || parameters[i].getType() == boolean.class) {
                    parameterValues.add(Boolean.parseBoolean(yzClass.getParameter(parameterNames.get(i))));
                } else if (parameters[i].getType() == String.class) {
                    parameterValues.add(yzClass.getParameter(parameterNames.get(i)));
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

    /**
     * 获取方法的参数名称
     *
     * @return 参数名称集合
     */
    private List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<>();
        for (Parameter parameter : yzClass.getMethod().getParameters()) {
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if (requestParam == null && pathVariable == null) {
                parameterNames.add(ParameterUtil.getName(parameter));
            } else if (requestParam != null && pathVariable == null){
                parameterNames.add(requestParam.value());
            } else {
                parameterNames.add(pathVariable.value());
            }
        }
        return parameterNames;
    }
}
