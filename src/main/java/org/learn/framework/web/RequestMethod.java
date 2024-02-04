package org.learn.framework.web;

/**
 * 请求方法
 */
public enum RequestMethod {

    /**
     * GET方法
     */
    GET("GET"),

    /**
     * POST方法
     */
    POST("POST"),

    /**
     * DELETE方法
     */
    DELETE("DELETE"),

    /**
     * PUT方法
     */
    PUT("PUT");

    private final String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
