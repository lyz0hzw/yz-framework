package org.learn.framework.exception;

/**
 * @author CDLX 2022-04-01 10:17
 */
public class WebHandlerException extends RuntimeException{

    public static final String SERVER_ERROR = "服务器执行出现异常";

    public WebHandlerException(){
        super();
    }

    public WebHandlerException(String message){
        super(message);
    }
}
