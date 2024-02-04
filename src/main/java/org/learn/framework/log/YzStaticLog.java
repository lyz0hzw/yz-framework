package org.learn.framework.log;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.learn.framework.context.Environment;

/**
 * @description 日志打印输出
 */
public class YzStaticLog {

    public static void error(String className, String template, Object... values) {
        show(LogConstance.ERROR_LEVEL, className, template, values);
    }

    public static void debug(String className, String template, Object... values) {
        show(LogConstance.DEBUG_LEVEL, className, template, values);
    }

    public static void log(String className, String template, Object... values) {
        show(LogConstance.INFO_LEVEL, className, template, values);
    }

    public static void show(String level, String className, String template, Object... values) {
        formatLog(level, className, getMessage(template, values));
    }

    public static String getMessage(String template, Object... values) {
        if (! ArrayUtil.isEmpty(values) && ! StrUtil.contains(template, "{}")) {
            return StrUtil.format(buildTemplateSplitBySpace(values.length + 1), ArrayUtil.insert(values, 0, template));
        } else {
            return StrUtil.format(template, values);
        }
    }

    private static synchronized void formatLog(String level, String className, String message) {
        System.out.format("\33[%d;2m%s", LogConstance.NORMAL, Environment.getSystemTime());
        if (level.equals(LogConstance.DEBUG_LEVEL)) {
            System.out.format("\33[%d;2m%-8s", LogConstance.DEBUG, "  DEBUG");
            System.out.format("\33[%d;2m%s", LogConstance.DEBUG, " --- ");
        } else if (level.equals(LogConstance.ERROR_LEVEL)) {
            System.out.format("\33[%d;2m%-8s", LogConstance.ERROR, "  ERROR");
            System.out.format("\33[%d;2m%s", LogConstance.ERROR, " --- ");
        } else {
            System.out.format("\33[%d;2m%-8s", LogConstance.INFO, "  INFO");
            System.out.format("\33[%d;2m%s", LogConstance.INFO, " --- ");
        }
        System.out.format("\33[%d;2m%-48s", LogConstance.CLASSNAME, "[" + ClassUtil.getShortClassName(className) + "]");
        System.out.format("\33[%d;2m%s", LogConstance.MESSAGE, " : " + message);
        System.out.format("%s", LogConstance.LINE_SEPARATOR);
        System.out.format("\33[%d;2m%s", LogConstance.MESSAGE, "");
    }


    private static String buildTemplateSplitBySpace(int count) {
        return StrUtil.repeatAndJoin("{}", count, " ");
    }
}
