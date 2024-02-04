package org.learn.framework.lowcode;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import org.learn.framework.log.YzStaticLog;
import org.learn.framework.util.CollectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hongda.li 2022-04-06 16:31
 */
public class MetaDataBuilder {

    private static final Map<String, List<CollectionUtil.Binary<String, String>>> META_DATA = new HashMap<>();

    private static final String CLASS_NAME = MetaDataBuilder.class.getName();

    /**
     * 根据表名生成类实体，查询数据库获取类结构
     * @param builder 低代码构建器
     */
    private static void initMetaData(LowCodeBuilder builder) {
        try {
            String url = "jdbc:mysql://localhost:3306/" + builder.getDataBaseName() + "?useSSL=false";
            // 查询数据库的表结构
            String sqlForDataBase = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='" + builder.getDataBaseName() + "';";
            // 数据源使用simple
            SimpleDataSource dataSource = new SimpleDataSource(url, builder.getUsername(), builder.getPassword());
            List<Entity> query = Db.use(dataSource).query(sqlForDataBase);
            // 遍历每一张表
            for (Entity entity : query) {
                String tableName = entity.getStr("TABLE_NAME");
                YzStaticLog.log(CLASS_NAME, "扫描到表名[{}]", tableName);
                // 查询列名和数据类型
                String sqlForTable = "select COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS where TABLE_NAME='" + tableName + "';";
                List<CollectionUtil.Binary<String, String>> binaries = new ArrayList<>();
                List<Entity> columns = Db.use(dataSource).query(sqlForTable);
                for (Entity column : columns) {
                    CollectionUtil.Binary<String, String> binary = new CollectionUtil.Binary<>();
                    binary.setX(column.getStr("COLUMN_NAME"));
                    binary.setY(column.getStr("DATA_TYPE"));
                    binaries.add(binary);
                    YzStaticLog.log(CLASS_NAME, "扫描到字段[{}]，类型为[{}]",
                            column.getStr("COLUMN_NAME"), column.getStr("DATA_TYPE"));
                }
                META_DATA.put(tableName, binaries);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<CollectionUtil.Binary<String, String>>> getMetaData(LowCodeBuilder builder){
        if (META_DATA.isEmpty()){
            initMetaData(builder);
            return META_DATA;
        }
        return META_DATA;
    }
}
