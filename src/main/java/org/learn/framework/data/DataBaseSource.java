package org.learn.framework.data;

import cn.hutool.db.ds.simple.SimpleDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.learn.framework.config.DataConfig;
import org.learn.framework.context.Environment;

import javax.sql.DataSource;

/**
 * @author yixi
 * JDBC数据源
 */
public class DataBaseSource {

    private final DataSource dataSource;

    /**
     * 判断是否引入了阿里巴巴的连接池，如果引入则使用阿里巴巴连接池，否则使用HuTool的连接池
     * HuTool的连接池暂未实现池化的功能，经测试在高并发下会出现端口绑定异常
     */
    public static final boolean ENABLE_DRUID = Environment.conditionOn("com.alibaba.druid.pool.DruidPooledConnection");

    private static class Holder {
        private static final DataBaseSource INSTANCE = new DataBaseSource();
    }

    private DataBaseSource() {
        DataConfig dataConfig = Environment.getConfig(DataConfig.class);
        if (ENABLE_DRUID){
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(dataConfig.getConnectionUrl());
            dataSource.setUsername(dataConfig.getUserName());
            dataSource.setPassword(dataConfig.getPassWord());
            this.dataSource = dataSource;
        }else {
            this.dataSource = new SimpleDataSource(dataConfig.getConnectionUrl(),
                    dataConfig.getUserName(), dataConfig.getPassWord());
        }
    }

    public static DataSource getDataSource() {
        return Holder.INSTANCE.dataSource;
    }
}
