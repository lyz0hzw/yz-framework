package org.learn.framework.web.tomcat;

import cn.hutool.log.Log;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.web.servlet.DispatcherServlet;

import java.io.File;

/**
 * @author hongda.li 2022-04-01 11:25
 */
public class TomcatServer {

    public void start(){
        Tomcat tomcat = new Tomcat();
        configTomcat(tomcat);
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

    private void configTomcat(final Tomcat tomcat){
        WebConfig config = Environment.getConfig(WebConfig.class);
        int port = config.getPort();
        String contextPath = "/".equals(config.getContextPath()) ? "" : config.getContextPath();
        //初始化连接器
        Connector connector = new Connector("HTTP/1.1"); // 使用HTTP版本号
        connector.setPort(port);
        connector.setURIEncoding("UTF-8");
        //初始化端口号
        tomcat.setBaseDir(new File(config.getRoot()).getAbsolutePath());
        tomcat.setPort(port);
        tomcat.setConnector(connector);
        tomcat.getService().addConnector(connector);
        //初始化请求分发器  dispatcherServlet
        Context context = tomcat.addContext(contextPath, null);
        tomcat.addServlet(contextPath, "dispatcherServlet", DispatcherServlet.class.getName());
        context.addServletMappingDecoded("/*","dispatcherServlet");
        tomcat.setSilent(true);
        String startUrl = "http://localhost:" + config.getPort() + config.getContextPath();
        Log.get().info("Tomcat服务器启动成功[{}]", startUrl);
    }
}
