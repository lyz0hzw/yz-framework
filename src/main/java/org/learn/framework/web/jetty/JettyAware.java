package org.learn.framework.web.jetty;

import org.learn.framework.annotation.Priority;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.Environment;
import org.learn.framework.context.Level;
import org.learn.framework.util.ClassUtils;
import org.learn.framework.web.RequestBody;

/**
 * @author hongda.li 2022-04-01 13:59
 */
@Priority(Level.Middle)
public class JettyAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        boolean enable = ClassUtils.mainClassWithAnnotation(EnableJetty.class);
        if (!enable){
            return;
        }
        WebConfig config = Environment.getConfig(WebConfig.class);
        config.setRequestBody(new RequestBody());
        applicationContext.setBean(config);
        new JettyServer().start();
    }
}
