package org.learn.framework.context;


import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import cn.hutool.setting.dialect.Props;
import org.learn.framework.annotation.Autowired;
import org.learn.framework.annotation.ConfigurationProperties;
import org.learn.framework.annotation.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Bean对象装配器，用以实现 @AutoWired注解
 */
public class Refresher {

    private final ApplicationContext CONTAINER;

    private final Object INSTANCE;

    private final List<Field> Fields;

    private final List<Field> Values;

    private final List<Method> Methods;

    /**
     * 获取类中带有@Autowired注解的属性（注入实体）和方法，带有@Value注解的属性（注入值）
     * @param clazz 类
     */
    public Refresher(Class<?> clazz){
        CONTAINER = ContextContainer.getApplicationContext();
        INSTANCE = CONTAINER.getBean(clazz);
        Fields = Arrays.asList(ReflectUtil.getFields(clazz, field -> field.getAnnotation(Autowired.class) != null));
        Values = Arrays.asList(ReflectUtil.getFields(clazz, field -> field.getAnnotation(Value.class) != null));
        Methods = Arrays.asList(ReflectUtil.getMethods(clazz,method -> method.getAnnotation(Autowired.class) != null));
    }

    public void refresh(){
        refreshFields();
        refreshValues();
        refreshMethods();
    }

    /**
     * 注入实体对象
     */
    private void refreshFields(){
        Fields.forEach(field -> {
            String beanName = field.getAnnotation(Autowired.class).value(); // 获取被注解（）里面的名称
            Class<?> fieldType = field.getType(); // 获取被注解的字段的类型
            Object bean = !"".equals(beanName) ? CONTAINER.getBean(beanName) : CONTAINER.getBean(fieldType);// 通过类->实例
            //通过接口实现类自动装配到接口中，说明Inter a;  注入InterImpl
            if (bean == null){
                for (Class<?> aClass : CONTAINER.getClasses()) {
                    for (Class<?> anInterface : aClass.getInterfaces()) {
                        if (anInterface == fieldType){ // 寻找类
                            // 型这个接口的实现类，并将其注入
                            ReflectUtil.setFieldValue(INSTANCE, field, CONTAINER.getBean(aClass));
                            return;
                        }
                    }
                }
            }
            //强制性非空检查
            if (bean == null){
                Log.get().debug("找不到Bean对象实例[{}]",field.getType().getName());
            }
            ReflectUtil.setFieldValue(INSTANCE, field, bean);
        });
    }

    /**
     * 注入方法参数中带有autowired的
     */
    private void refreshMethods(){
        Methods.forEach(method -> {
            Object[] params = new Object[method.getParameterTypes().length];
            for (int i = 0; i < method.getParameterTypes().length; i++) {
                params[i] = CONTAINER.getBean(method.getParameterTypes()[i]);
                if (params[i] == null) {
                    Log.get().debug("找不到Bean对象实例[{}]",method.getParameterTypes()[i].getName());
                }
            }
            ReflectUtil.invoke(INSTANCE, method, params);
        });
    }

    /**
     * 注入常量值
     */
    private void refreshValues(){
        Values.forEach(field -> { // 为每个带有注解的字段进行注入值
            String value = field.getAnnotation(Value.class).value();
            // 从配置文件中获取值
            ConfigurationProperties properties = INSTANCE.getClass().getAnnotation(ConfigurationProperties.class);
            // 如果是从配置文件中取值
            if (value.startsWith("${") && value.endsWith("}")){
                if (properties != null){ // 该类指明了配置文件所在位置
                    String path = properties.value();
                    String prefix = properties.prefix();
                    final Props props = Environment.getProps(path); // 获取application.properties文件
                    if (props == null){
                        Log.get().error("类资源中没有此文件[{}]",path);
                        return;
                    }
                    ReflectUtil.setFieldValue(
                            INSTANCE
                            , field
                            , props.get("".equals(prefix)
                                    ? value.substring(2,value.length() - 1) // 没有前缀
                                    : prefix + "." + value.substring(2,value.length() - 1))); // 拥有前缀
                }else { // 从默认的配置文件中获取值
                    final Props props = Environment.getProps("application.properties");
                    // 配置文件不为空
                    if (props != null) {
                        Object propValue = props.get(value.substring(2, value.length() - 1));
                        // 配置文件的属性值也不为空
                        if (propValue != null) {
                            ReflectUtil.setFieldValue(INSTANCE, field, propValue);
                        }
                    }
                }
            }else {
                ReflectUtil.setFieldValue(INSTANCE, field, value);
            }
        });
    }
}
