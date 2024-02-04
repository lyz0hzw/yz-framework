package org.learn.framework.web.simple;

import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.SimpleServer;
import cn.hutool.log.Log;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.Environment;
import org.learn.framework.context.YzClass;
import org.learn.framework.exception.FilterException;
import org.learn.framework.web.RequestBody;
import org.learn.framework.web.WebFilterAware;
import org.learn.framework.web.WebHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yixi
 */
public class YzWebServer {

    private SimpleServer server;

    public void startServer() {
        String startUrl = configServer();
        server.getRawServer().start();
        Log.get().info("YzWeb服务启动成功[{}]", startUrl);
    }

    private String configServer() {
        // 获取WebConfig配置
        WebConfig config = Environment.getConfig(WebConfig.class);
        // 根据WebConfig配置创建服务器
        server = HttpUtil.createServer(config.getPort());
        // 添加过滤器
        server.addFilter(new Filter() {
            @Override
            public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
                // 执行过滤器链
                executeFilter(exchange, chain);
            }
            @Override
            public String description() {
                return "YzFilter";
            }
        });

        server.setRoot(config.getRoot());
        // 根据映射路径反射调用对应Controller中的方法
        RequestBody requestBody = config.getRequestBody();
        // 将方法与Api请求Action绑定,和每个方法绑定
        requestBody.getMapping().forEach(mapping -> {
            YzClass yzClass = requestBody.getClassAndMethod(mapping);
            // 上方的过滤器执行完，将会执行下面url方法
            server.addAction(mapping, (httpServerRequest, httpServerResponse) -> {
                httpServerResponse.setCharset(StandardCharsets.UTF_8);
                new WebHandler().invokeYzClass(httpServerRequest, httpServerResponse, yzClass);
            });
        });
        return "http://localhost:" + config.getPort() + "/";
    }

    private void executeFilter(HttpExchange exchange, Filter.Chain chain) throws IOException {
        HttpServerRequest request = new HttpServerRequest(exchange);
        HttpServerResponse response = new HttpServerResponse(exchange);
        response.setCharset(StandardCharsets.UTF_8);

        BoundedPriorityQueue<Class<?>> queue = WebFilterAware.getQueue();  // 获取过滤器执行链

        // 标记是否所有过滤器都允许放行
        boolean doFilter = true;

        for (Class<?> filter : queue.toList()) {
            boolean execute = false;
            try {
                Object filterInstance = ContextContainer.getApplicationContext().getBean(filter); // 获取字节码对应的实例对象
                if (filterInstance != null) {
                    // 执行过滤器的execute方法
                    execute = ReflectUtil.invoke(filterInstance, ReflectUtil.getMethod(filter, "execute", HttpServerRequest.class, HttpServerResponse.class), request, response);
                }
            } catch (UtilException | SecurityException e) {
                Log.get().error("过滤器执行错误[{}]", filter.getName());
                e.printStackTrace();
                throw new FilterException(FilterException.FILTER_ERROR);
            }
            if (! execute) {
                doFilter = false;
                break;
            }
        }

        // 只有所有过滤器允许放行最后才放行
        if (doFilter) {
            chain.doFilter(exchange);
        }
    }
}
