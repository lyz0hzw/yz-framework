package org.learn.framework.data;

import cn.hutool.log.Log;
import org.learn.framework.annotation.Priority;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.Level;
import org.learn.framework.util.ClassUtils;


/**
 * @author yixi
 */
@Priority(Level.Middle)
public class DataBaseAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ClassUtils.getClassesWithAnnotation(YzMapper.class,false).forEach(clazz -> {
            Log.get().info("扫描到YzMapper接口，正在进行动态代理[{}]",clazz.getName());
            DataBaseProxy.invokeProxy(clazz);
        });
    }
}
