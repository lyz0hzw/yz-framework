package org.learn.framework.web.servlet;

import cn.hutool.core.text.StrSplitter;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.context.YzClass;
import org.learn.framework.web.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hongda.li 2022-04-01 10:22
 */
public class PathVariableFilter implements Filter{
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebConfig config = Environment.getConfig(WebConfig.class);
        RequestBody requestBody = config.getRequestBody();
        // Api的实际路径，例：/user/3
        String requestUri = request.getRequestURI();
        // Api的注解路径，例：/user/{id}
        String match = requestBody.match(requestUri);
        // 对没有{PathVariable}参数绑定的Api直接放行
        if (!match.contains("{") && !match.contains("}")) {
            return true;
        }
        YzClass cogoClass = requestBody.getClassAndMethod(requestUri);
        // 获取参数列表
        Map<String, List<String>> beforeParams = cogoClass.getParams();
        Map<String, String> pathVariableParams = handlePath(requestUri, match);
        pathVariableParams.keySet().forEach(name -> beforeParams.put(name, Collections.singletonList(pathVariableParams.get(name))));
        return true;
    }

    /**
     * 处理含有{}的请求Api
     * @param realApi 真实请求Api
     * @param annotationApi 注解请求Api
     */
    private Map<String, String> handlePath(String realApi, String annotationApi) {
        Map<String, String> params = new HashMap<>(16);
        List<String> annotationSplit = StrSplitter.splitPath(annotationApi);
        List<String> realSplit = StrSplitter.splitPath(realApi);
        for (int i = 0; i < annotationSplit.size(); i++) {
            String beforeFormatParamValue = annotationSplit.get(i);
            if (beforeFormatParamValue.contains("{") && beforeFormatParamValue.contains("}")) {
                // 去除“{”和“}”
                String paramName = beforeFormatParamValue.substring(1, beforeFormatParamValue.length() - 1);
                params.put(paramName, realSplit.get(i));
            }
        }
        return params;
    }
}
