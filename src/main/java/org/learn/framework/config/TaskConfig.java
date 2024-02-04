package org.learn.framework.config;


import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Value;

/**
 * 任务配置
 */
@Component
public class TaskConfig {

    @Value("${yz-framework.task.threadPoolSize}")
    private int threadPoolSize = 10;

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
}
