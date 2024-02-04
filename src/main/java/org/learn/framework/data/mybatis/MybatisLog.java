package org.learn.framework.data.mybatis;

import cn.hutool.log.LogFactory;
import org.apache.ibatis.logging.Log;

/**
 * @author CDLX 2022-04-02 17:09
 */
public class MybatisLog implements Log {

    private String logName;
    private cn.hutool.log.Log log;

    public MybatisLog(String name){
        this.logName = name;
    }

    private void initLog(){
        if (this.log == null){
            this.log = LogFactory.get(this.logName);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable throwable) {
        initLog();
        log.error(s, throwable);
    }

    @Override
    public void error(String s) {
        initLog();
        log.error(s);
    }

    @Override
    public void debug(String s) {
        initLog();
        log.debug(s);
    }

    @Override
    public void trace(String s) {
        initLog();
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        initLog();
        log.warn(s);
    }
}
