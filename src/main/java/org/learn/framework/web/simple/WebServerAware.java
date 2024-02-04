package org.learn.framework.web.simple;


import org.learn.framework.annotation.Priority;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ApplicationContextAware;
import org.learn.framework.context.Environment;
import org.learn.framework.context.Level;
import org.learn.framework.web.RequestBody;
import org.learn.framework.web.simple.YzWebServer;

/**
 * @author hongda.li
 */
@Priority(Level.MIN)
public class WebServerAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        WebConfig config = Environment.getConfig(WebConfig.class);
        config.setRequestBody(new RequestBody());
        applicationContext.setBean(config);
        new YzWebServer().startServer();
    }
}
