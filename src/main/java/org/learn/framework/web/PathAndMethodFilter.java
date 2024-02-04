package org.learn.framework.web;

import cn.hutool.core.map.multi.ListValueMap;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.log.Log;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.context.YzClass;
import org.learn.framework.web.simple.YzFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 负责过滤掉非法的请求Api和请求方法
 */
public class PathAndMethodFilter implements YzFilter {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean execute(HttpServerRequest request, HttpServerResponse response) {
        WebConfig config = Environment.getConfig(WebConfig.class);
        RequestBody requestBody = config.getRequestBody();
        // 请求Api
        String requestUri = StrUtil.split(request.getURI().toString(),"?").get(0);
        if (isStaticResource(requestUri)){
            // 放行静态资源
            return true;
        }
        // 请求方法
        String requestMethod = request.getMethod();
        // 允许的请求方法集合
        RequestMethod[] requestMethods = requestBody.getRequestMethod(requestUri);
        if (requestMethods != null) {
            for (RequestMethod method : requestMethods) {
                if (method.getValue().equals(requestMethod)) {
                    Log.get().info("Api请求[{}][{}]", requestUri, requestMethod);
                    YzClass yzClass = requestBody.getClassAndMethod(requestUri);
                    ListValueMap<String, String> requestParams = request.getParams();
                    Map<String, List<String>> paramsMap = new HashMap<>(requestParams.size());
                    paramsMap.putAll(requestParams);
                    yzClass.setParams(paramsMap); // 设置请求参数
                    return true;
                }
            }
            Log.get().error("请求方法不匹配[{}][{}]", requestUri, requestMethod);
            response.sendError(405,"405 error : Request method don't match!");
            return false;
        }
        Log.get().error("请求路径不匹配[{}]", requestUri);
        response.send404("404 error : Request mapping don't match!");
        return false;
    }

    private boolean isStaticResource(String mapping){
        return mapping.endsWith(".ico") || mapping.endsWith(".html") || mapping.endsWith(".htm") || mapping.endsWith(".css") || mapping.endsWith(".js")
                || mapping.endsWith(".png") || mapping.endsWith(".jpg") || mapping.endsWith(".gif") || mapping.endsWith(".pdf");
    }
}
