package org.learn.framework.exception;

/**
 * @description 过滤器异常
 */
public class FilterException extends RuntimeException{

    public static final String FILTER_ERROR = "过滤器执行错误";

    public FilterException(){
        super();
    }

    public FilterException(String message){
        super(message);
    }
}
