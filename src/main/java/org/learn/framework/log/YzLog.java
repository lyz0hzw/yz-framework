package org.learn.framework.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.log.AbstractLog;
import cn.hutool.log.level.Level;
import org.learn.framework.config.LogConfig;
import org.learn.framework.context.ApplicationContext;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.Environment;
import org.learn.framework.context.Refresher;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuyz
 * @description 实现日志记录
 */
public class YzLog extends AbstractLog{

    private LogConfig config;

    private final String loggerName;

    private static final String TEMPLATE = "%s  %s [%s] : %s\r\n";

    private File debugFile;

    private FileWriter debugWriter;

    private File errorFile;

    private FileWriter errorWriter;

    private final AtomicBoolean initFinished = new AtomicBoolean(false);

    public YzLog(String name) {
        this.loggerName = name;
    }

    public YzLog(Class<?> clazz) {
        this(clazz == null ? "null" : clazz.getName());
    }

    private void init() {
        LogConfig config = Environment.getConfig(LogConfig.class);
        if (config == null) {
            ApplicationContext context = ContextContainer.getApplicationContext();
            context.setBean(LogConfig.class);
            new Refresher(LogConfig.class).refresh();
        }
        this.config = Environment.getConfig(LogConfig.class);
        this.debugFile = new File(this.config.getDebugPath() + DateUtil.today() + ".txt");
        this.errorFile = new File(this.config.getErrorPath() + DateUtil.today() + ".txt");
        this.debugWriter = new FileWriter(debugFile);
        this.errorWriter = new FileWriter(errorFile);
    }

    private void initCheck(){
        if (!initFinished.get()) {
            init();
            initFinished.set(true);
        }
    }

    public boolean isTimeOut(File logFile) {
        initCheck();
        return !logFile.getAbsolutePath().endsWith(DateUtil.today() + ".txt");
    }

    public void log(String message, Object... values){
        initCheck();
        YzStaticLog.log(loggerName, message, values);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String message, Object... values) {
        // Hutool在设置LogFactory时会调用debug()方法，但此时配置类还无法加载
        // 所以只能避免Hutool执行debug方法中的initCheck()，因此在此做拦截
        if ("Custom Use [{}] Logger.".equals(message)){
            return;
        }
        initCheck();
        YzStaticLog.debug(loggerName, message, values);
        if (isTimeOut(debugFile)) {
            debugFile = new File(config.getDebugPath() + DateUtil.today() + ".txt");
            debugWriter = new FileWriter(debugFile);
        }
        debugWriter.append(String.format(TEMPLATE, DateUtil.now(), loggerName, "DEBUG", YzStaticLog.getMessage(message, values)));
    }

    @Override
    public void debug(String s, Throwable throwable, String s1, Object... objects) {
        debug(s1,objects);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String message, Object... values) {
        initCheck();
        YzStaticLog.error(loggerName, message, values);
        if (isTimeOut(errorFile)) {
            errorFile = new File(config.getErrorPath() + DateUtil.today() + ".txt");
            errorWriter = new FileWriter(errorFile);
        }
        errorWriter.append(String.format(TEMPLATE, DateUtil.now(), loggerName, "ERROR", YzStaticLog.getMessage(message, values)));
    }

    @Override
    public void error(String s, Throwable throwable, String s1, Object... objects) {
        error(s1,objects);
    }

    @Override
    public String getName() {
        return this.loggerName;
    }

    @Override
    public void log(String s, Level level, Throwable throwable, String s1, Object... objects) {
        log(s1,objects);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String s, Throwable throwable, String s1, Object... objects) {
        log(s1,objects);
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String s, Throwable throwable, String s1, Object... objects) {
        log(s1,objects);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String s, Throwable throwable, String s1, Object... objects) {
        debug(s1,objects);
    }
}
