package org.learn.framework.web.undertow;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.log.Log;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.util.ClassUtils;
import org.learn.framework.web.servlet.DispatcherServlet;

import javax.servlet.*;

/**
 * @author hongda.li 2022-03-31 20:06
 */
public class UndertowServer {

    private static final String HOST_NAME = "localhost";

    public void start(){
//        WebConfig config = Environment.getConfig(WebConfig.class);
//        ServletInfo servletInfo = Servlets.servlet(DispatcherServlet.class).addMapping("/*");
//        DeploymentInfo deploymentInfo = Servlets.deployment()
//                .setClassLoader(ClassUtil.getClassLoader())
//                .setDeploymentName(ClassUtils.getMainClass().getName() + ".war")
//                .setContextPath(config.getContextPath())
//                .addServlets(servletInfo); // 将DispatcherServlet加入部署环境中
//        // 将deploymentInfo加入到manger中
//        DeploymentManager manager = Servlets.defaultContainer().addDeployment(deploymentInfo);
//        manager.deploy();
//        HttpHandler httpHandler = null;
//        try {
//            httpHandler = manager.start();
//        } catch (jakarta.servlet.ServletException e) {
//            e.printStackTrace();
//        }
//        PathHandler handler = Handlers.path(Handlers.redirect(config.getContextPath())).addPrefixPath(config.getContextPath(), httpHandler);
//        Undertow server = Undertow.builder()
//                .addHttpListener(config.getPort(), HOST_NAME)
//                .setHandler(handler)
//                .build();
//
//        server.start();
//        String startUrl = "http://localhost:" + config.getPort() + config.getContextPath();
//        Log.get().info("Undertow服务器启动成功[{}]", startUrl);
    }
}