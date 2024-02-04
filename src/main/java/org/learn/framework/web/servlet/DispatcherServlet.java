package org.learn.framework.web.servlet;


import jakarta.servlet.Servlet;
import org.learn.framework.config.WebConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.web.RequestBody;
import org.learn.framework.web.WebHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hongda.li 2022-03-31 20:08
 */
public class DispatcherServlet extends HttpServlet {

    private RequestBody requestBody;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean filter = new GlobalFilter().executeFilter(req, resp);
        if (!filter){
            return;
        }
        if (requestBody == null){
            requestBody = Environment.getConfig(WebConfig.class).getRequestBody();
        }
        new WebHandler().invokeYzClass(req, resp, requestBody.getClassAndMethod(req.getRequestURI()));
    }
}
