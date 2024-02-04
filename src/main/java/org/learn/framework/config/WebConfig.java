package org.learn.framework.config;


import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Value;
import org.learn.framework.web.RequestBody;

/**
 * @author yixi
 */
@Component
public class WebConfig {

    /**
     * 启动端口号
     */
    @Value("${yz-framework.web.port}")
    private int port = 8088;

    /**
     * 文件服务器路径
     */
    @Value("${yz-framework.web.root}")
    private String root = "/yz/storage/web/root/";

    /**
     * 上下文请求根路径
     */
    @Value("${yz-framework.web.contextPath}")
    private String contextPath = "/";

    /**
     * 请求映射信息
     */
    private RequestBody requestBody;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
