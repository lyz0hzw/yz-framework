package org.learn.framework.exception;

/**
 * @author CDLX 2022-03-31 14:01
 */
public class StarterException extends RuntimeException{

    public static final String NONE_ANNOTATION = "主类缺少注解[@BootApplication]";

    public StarterException(){
        super();
    }

    public StarterException(String message){
        super(message);
    }
}
