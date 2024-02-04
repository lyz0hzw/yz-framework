package org.learn.framework.config;


import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Value;

/**
 * @author CDLX 2022-04-02 20:21
 */
@Component
public class PermissionConfig {

    /**
     * 默认30分钟过期
     */
    @Value("${yz-framework.permission.tokenTime}")
    private int tokenTime = 30 * 60;

    @Value("${yz-framework.permission.autoRefreshToken}")
    private boolean autoRefreshToken = true;

    public int getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(int tokenTime) {
        this.tokenTime = tokenTime;
    }

    public boolean isAutoRefreshToken() {
        return autoRefreshToken;
    }

    public void setAutoRefreshToken(boolean autoRefreshToken) {
        this.autoRefreshToken = autoRefreshToken;
    }
}
