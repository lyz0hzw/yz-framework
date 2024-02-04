package org.learn.test.lowcode.config;

import org.learn.framework.annotation.Bean;
import org.learn.framework.annotation.Configuration;
import org.learn.framework.config.WebConfig;

/**
 * @author Application 
 */
@Configuration
public class WebServerConfiguration {
    
    @Bean
    public WebConfig config(){
        WebConfig config = new WebConfig();
        config.setPort(8088);
        return config;
    }
}
