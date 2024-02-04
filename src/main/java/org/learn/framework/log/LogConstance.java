package org.learn.framework.log;


import org.learn.framework.context.Environment;

/**
 * @author hzwang
 * @version 1.0
 * Create by 2023/9/7 14:17
 * @title LogConstance
 * @description 日志模块涉及的常量管理
 */
public interface LogConstance {

    String DEBUG_PATH = Environment.YZ_STORAGE_ROOT + "log/debug/";

    String ERROR_PATH = Environment.YZ_STORAGE_ROOT + "log/error/";

    /**
     * 日志等级
     */
    String INFO_LEVEL = "INFO";

    String DEBUG_LEVEL = "DEBUG";

    String ERROR_LEVEL = "ERROR";

    /**
     * 红色，打印错误日志
     */
    int ERROR = 31;

    /**
     * 绿色，打印INFO日志
     */
    int INFO = 32;

    /**
     * 黄色，打印DEBUG日志
     */
    int DEBUG = 33;

    /**
     * 紫色，打印消息
     */
    int MESSAGE = 35;

    /**
     * 蓝色，打印类名
     */
    int CLASSNAME = 36;

    /**
     * 灰色，打印时间
     */
    int NORMAL = 37;

    /**
     * 白色正常输出
     */
    int NONE = 38;

    String LINE_SEPARATOR = System.lineSeparator();
}
