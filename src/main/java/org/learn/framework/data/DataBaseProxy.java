package org.learn.framework.data;

import cn.hutool.core.util.TypeUtil;
import org.learn.framework.aop.YzProxy;
import org.learn.framework.context.Environment;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 数据库动态代理
 */
public class DataBaseProxy {

    public static void invokeProxy(Class<?> interfaceClazz){
        DataBaseSession session = Environment.getBean(DataBaseSession.class); // 用的是自定义的session
        YzProxy.newProxyInstanceByInterface(
                interfaceClazz
                , interfaceClazz.getAnnotation(YzMapper.class).value()
                , (proxy, method, args) -> { // 代理每个方法
                    // 获取返回参数，例如 User addUser(); -> User.class
                    Class<?> returnClass = TypeUtil.getReturnClass(method);
                    // 获取泛型中的第一个参数，例如 : List<User> findUser() -> User.class
                    Type typeArgument = TypeUtil.getTypeArgument(TypeUtil.getReturnType(method));
                    Find find = method.getAnnotation(Find.class); // 这里的value指的是sql语句
                    Modify modify = method.getAnnotation(Modify.class);
                    if (find != null) {
                        // 如果返回的是List类型
                        if (returnClass == List.class){
                            return session.findList(Class.forName(typeArgument.getTypeName()), find.value(), args);
                        }
                        // 如果返回的是数字类型，如Long、Integer
                        if (Number.class.isAssignableFrom(returnClass)){
                            return session.findCount(find.value(), args);
                        }
                        // 如果返回的是String类型
                        if (returnClass == String.class) {
                            return session.findString(find.value(), args);
                        }
                    }
                    if (modify != null) {
                        return session.modify(modify.value(), args);
                    }
                    return null;
                });
    }
}
