package org.learn.framework.web.tomcat;

import cn.hutool.core.thread.ThreadUtil;
import org.learn.framework.annotation.Priority;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.Environment;
import org.learn.framework.context.Level;
import org.learn.framework.util.ClassUtils;
import org.learn.framework.web.RequestBody;

/**
 * @author CDLX 2022-04-01 11:24
 */
@Priority(Level.Middle)
public class TomcatAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        boolean enable = ClassUtils.mainClassWithAnnotation(EnableTomcat.class);
        if (!enable){
            return;
        }
        WebConfig config = Environment.getConfig(WebConfig.class);
        config.setRequestBody(new RequestBody()); // 获取url 和 执行方法的映射关系
        applicationContext.setBean(config);
        ThreadUtil.execute(() -> new TomcatServer().start());
    }
}
