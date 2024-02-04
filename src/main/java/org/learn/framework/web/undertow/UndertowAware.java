package org.learn.framework.web.undertow;

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
 * @author hongda.li 2022-03-31 20:25
 */
@Priority(Level.Middle)
public class UndertowAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        boolean enable = ClassUtils.mainClassWithAnnotation(EnableUndertow.class);
        if (!enable){
            return;
        }
        WebConfig config = Environment.getConfig(WebConfig.class);
        config.setRequestBody(new RequestBody());
        applicationContext.setBean(config);
        ThreadUtil.execute(() -> new UndertowServer().start());
    }
}
