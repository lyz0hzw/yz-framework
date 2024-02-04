package org.learn.framework.config;

import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Value;
import org.learn.framework.log.LogConstance;

@Component
public class LogConfig {

    @Value("${yz-framework.log.debugPath}")
    private String debugPath = LogConstance.DEBUG_PATH;

    @Value("${yz-framework.log.errorPath}")
    private String errorPath = LogConstance.ERROR_PATH;

    public String getDebugPath() {
        return this.debugPath;
    }

    public String getErrorPath(){
        return this.errorPath;
    }

    public void setErrorPath(String errorPath) {
        this.errorPath = (errorPath.endsWith("/") || errorPath.endsWith("\\")) ? errorPath : errorPath + "\\";
    }

    public void setDebugPath(String debugPath){
        this.debugPath = (debugPath.endsWith("/") || debugPath.endsWith("\\")) ? debugPath : debugPath + "\\";
    }
}
