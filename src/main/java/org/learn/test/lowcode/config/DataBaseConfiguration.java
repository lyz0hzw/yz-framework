package org.learn.test.lowcode.config;

import org.learn.framework.annotation.Bean;
import org.learn.framework.annotation.Configuration;
import org.learn.framework.config.DataConfig;

/**
 * @author Application
 */
@Configuration
public class DataBaseConfiguration {
    
    @Bean
    public DataConfig config(){
        DataConfig config = new DataConfig();
        config.setUserName("root");
        config.setPassWord("123456");
        config.setConnectionUrl("jdbc:mysql://localhost:3306/epm-corpus?useSSL=false");
        return config;
    }
}