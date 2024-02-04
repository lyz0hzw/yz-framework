package org.learn.framework.web;

import cn.hutool.http.ContentType;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import org.learn.framework.context.YzClass;
import org.learn.framework.exception.WebHandlerException;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

/**
 * 执行器
 */
public class WebHandler {

    public void invokeYzClass(HttpServletRequest request, HttpServletResponse response, YzClass yzClass) throws IOException {
        Object result;
        try {
            result = new org.learn.framework.web.servlet.ParamBinder(yzClass, request, response).invoke();
        } catch (InvocationTargetException | IllegalAccessException e) {
            response.sendError(500, "500 error : 服务器执行出现异常!");
            e.printStackTrace();
            throw new WebHandlerException(WebHandlerException.SERVER_ERROR);
        }
        if (result == null){
            return;
        }
        if (result instanceof String) {
            response.setContentType("text/html;charset=UTF-8");
            Log.get().info("ResponseBody：{}", result);
            response.getWriter().write((String) result);
        } else if (result instanceof File) {
            File file = (File) result;
            FileInputStream fis = new FileInputStream(file);
            String filename = URLEncoder.encode(file.getName(), "UTF-8");
            byte[] b = new byte[fis.available()];
            //noinspection ResultOfMethodCallIgnored
            fis.read(b);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            ServletOutputStream out = response.getOutputStream();
            out.write(b);
            out.flush();
            out.close();
        } else {
            String jsonStr = JSONUtil.toJsonStr(result);
            Log.get().info("ResponseBody：{}", jsonStr);
            PrintWriter writer = response.getWriter();
            writer.write(jsonStr);
            writer.flush();
            writer.close();
        }
    }

    public void invokeYzClass(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse, YzClass yzClass) {
        Object result;
        try {
            // 使用参数绑定器进行参数绑定并反射执行方法
            result = new ParamBinder(yzClass, httpServerRequest, httpServerResponse).invoke();
        } catch (InvocationTargetException | IllegalAccessException e) {
            // httpServerResponse.sendError(500, "500 error");
            e.printStackTrace();
            throw new WebHandlerException(WebHandlerException.SERVER_ERROR);
        }
        // 对返回结果进行处理
        if (result == null){
            return;
        }
        if (result instanceof File) {
            // 对返回值为文件类型的单独处理
            httpServerResponse.write((File) result);
        } else if (result instanceof String) {
            // 对返回值为String的按HTML格式
            Log.get().info("ResponseBody：{}", result);
            httpServerResponse.write((String) result, ContentType.TEXT_HTML.toString());
        } else {
            // 其余返回值类型按JSON格式处理
            String jsonStr = JSONUtil.toJsonStr(result);
            Log.get().info("ResponseBody：{}", jsonStr);
            httpServerResponse.write(jsonStr, ContentType.JSON.toString());
        }
    }
}
