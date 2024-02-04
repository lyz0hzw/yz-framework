package org.learn.framework.data.mybatis;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;
import org.apache.ibatis.annotations.Mapper;
import org.learn.framework.annotation.Autowired;
import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Priority;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.Environment;
import org.learn.framework.context.Level;
import org.learn.framework.data.YzMapper;
import org.learn.framework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hongda.li 2022-04-02 17:40
 */
@Priority(Level.Middle)
public class MybatisAware implements ApplicationContextAware {

    private static final List<Class<?>> MAPPERS = new ArrayList<>();

    public static final boolean ENABLE_MYBATIS = Environment.conditionOn("org.apache.ibatis.session.SqlSessionFactoryBuilder");

    public static List<Class<?>> getMappers(){
        return MAPPERS;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (!ENABLE_MYBATIS){
            return;
        }
        // 扫描带有mapper注解的类
        ClassUtils.getClassesWithAnnotation(Mapper.class, false).forEach(mapper -> {
            if (mapper.isInterface()){
                MAPPERS.add(mapper);
            }
        });
        // 扫描带有yzmapper注解的类
        ClassUtils.getClassesWithAnnotation(YzMapper.class, false).forEach(yzMapper -> {
            if (yzMapper.isInterface()){
                MAPPERS.add(yzMapper);
            }
        });

        // 为需要依赖注入的Mapper进行注入
        ClassUtils.getClassWithCombination(Component.class, true).forEach(component -> {
            for (Field field : ReflectUtil.getFields(component, field   // 过滤器查找autowired字段，并且字段类型具有mapper注解
                    -> field.getAnnotation(Autowired.class) != null
                    && MAPPERS.contains(field.getType()))) {
                ReflectUtil.setFieldValue(  // 将mapper注入到类中
                        applicationContext.getBean(component)
                        , field
                        , MybatisBuilder.buildSession(true).getMapper(field.getType()));
                Log.get().info("Mapper注入成功[{}]", field.getType().getName());
            }
        });
    }
}
