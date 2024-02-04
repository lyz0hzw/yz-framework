package org.learn.framework.config;


import org.learn.framework.annotation.Component;
import org.learn.framework.annotation.Value;

/**
 * 数据库配置
 */
@Component
public class DataConfig {

    @Value("${yz-framework.data.connectionUrl}")
    private String connectionUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";

    @Value("${yz-framework.data.driverName}")
    private String driverName = "com.mysql.jdbc.Driver";

    @Value("${yz-framework.data.userName}")
    private String userName = "root";

    @Value("${yz-framework.data.passWord}")
    private String passWord = "123456";

    // 下面两个字段均为-1表示不开启缓存
    @Value("${yz-framework.data.cacheUnit}")
    private long cacheUnit = -1;

    @Value("${yz-framework.data.cacheSize}")
    private int cacheSize = -1;

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public long getCacheUnit() {
        return cacheUnit;
    }

    public void setCacheUnit(long cacheUnit) {
        this.cacheUnit = cacheUnit;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

}
