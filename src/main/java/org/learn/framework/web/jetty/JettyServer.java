package org.learn.framework.web.jetty;

import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.web.servlet.DispatcherServlet;

import cn.hutool.log.Log;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * @author hongda.li 2022-04-01 13:44
 */
public class JettyServer {

    private WebConfig config;

    public void start() {
//        System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");
//        this.config = Environment.getConfig(WebConfig.class);
//        Server server = new Server(createQueuedThreadPool());
//        server.setStopAtShutdown(true);
//        ServerConnector serverConnector = createServerConnectorAndAddToServer(server);
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[]{
//                createResourceHandler(),
//                createWebAppContext(),
//                new DefaultHandler()
//        });
//        server.setHandler(handlers);
//        try {
//            server.start();
//            String startUrl = "http://localhost:" + config.getPort() + config.getContextPath();
//            Log.get().info("Jetty服务启动成功[{}]", startUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
//
//    private QueuedThreadPool createQueuedThreadPool() {
//        QueuedThreadPool threadPool = new QueuedThreadPool();
//        threadPool.setMinThreads(100);
//        threadPool.setMaxThreads(500);
//        return threadPool;
//    }
//
//    private HttpConfiguration createHttpConfiguration() {
//        HttpConfiguration httpConfig = new HttpConfiguration();
//        httpConfig.setSecurePort(8443);
//        httpConfig.setOutputBufferSize(32768);
//        httpConfig.setRequestHeaderSize(8192);
//        httpConfig.setResponseHeaderSize(8192);
//        httpConfig.setSendServerVersion(true);
//        httpConfig.setSendDateHeader(false);
//        return httpConfig;
//    }
//
//    public WebAppContext createWebAppContext() {
//        WebAppContext webAppContext = new WebAppContext();
//        webAppContext.setContextPath(config.getContextPath());
//        webAppContext.setResourceBase(config.getRoot());
//        ServletHolder servletHolder = new ServletHolder(DispatcherServlet.class);
//        servletHolder.setAsyncSupported(false);
//        servletHolder.setInitOrder(1);
//        webAppContext.addServlet(servletHolder, "/*");
//        return webAppContext;
//    }
//
//    public ResourceHandler createResourceHandler() {
//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setResourceBase(config.getRoot());
//        return resourceHandler;
//    }
//
//    public ServerConnector createServerConnectorAndAddToServer(Server server) {
//        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(createHttpConfiguration()));
//        connector.setHost("localhost");
//        connector.setPort(config.getPort());
//        connector.setIdleTimeout(30000L);
//        server.addConnector(connector);
//        return connector;
//    }
}
