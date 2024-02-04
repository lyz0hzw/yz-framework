package org.learn.framework.data;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.log.Log;
import org.learn.framework.annotation.Component;
import org.learn.framework.config.DataConfig;
import org.learn.framework.context.Environment;
import org.learn.framework.context.InitBean;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yixi
 */
@Component
public class DataBaseSession implements InitBean {

    private boolean enableCache = false;

    /**
     * 执行事务操作
     * @param actions sql动作列表
     * @return
     */
    public boolean beginTransaction(Action... actions) {
        try {
            Db.use(DataBaseSource.getDataSource()).tx(db -> {
                for (Action action : actions) {
                    modify(action);
                }
            });
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modify(Action action) {
        return modify(action.getSql(), action.getParams());
    }

    public boolean modify(String sql, Object... params) {
        Log.get().info(sql, params);
        try {
            // 在事务中执行sql操作
            int execute = Db.use(DataBaseSource.getDataSource()).execute(sql, params);
            return execute != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取数据库表的记录数
     * @param action sql执行动作
     * @return
     */
    public Number findCount(Action action) {
        return findCount(action.getSql(), action.getParams());
    }

    public Number findCount(String sql, Object... params) {
        try {
            return Db.use(DataBaseSource.getDataSource()).queryNumber(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String findString(Action action) {
        return findString(action.getSql(), action.getParams());
    }

    public String findString(String sql, Object... params) {
        try {
            return Db.use(DataBaseSource.getDataSource()).queryString(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回指定对象
     * @param clazz 对象类型的字节码
     * @param action sql动作
     * @return 对象
     * @param <T> 对象类型
     */
    public <T> T findOne(Class<T> clazz, Action action) {
        return findOne(clazz, action.getSql(), action.getParams());
    }

    public <T> T findOne(Class<T> clazz, String sql, Object... params) {
        List<T> list = findList(clazz, sql, params);
        if (list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    public <T> List<T> findPage(Class<T> clazz, String sql, int page, int size, Object... params) {
        if (page < 1) {
            return null;
        }
        sql = sql + " limit " + size + " offset " + ((page - 1) * size);
        return findList(clazz, sql, params);
    }

    public <T> List<T> findList(Class<T> clazz, Action action) {
        return findList(clazz, action.getSql(), action.getParams());
    }

    public <T> List<T> findList(Class<T> clazz, String sql, Object... params) {
        Action action = new Action().setSql(sql).setParams(params);
        if (enableCache) {
            // 先从缓存中获取
            List<T> cache = (List<T>) SessionCache.getCache(action);
            if (cache != null) {
                Log.get().info("本地缓存取值成功[{}][{}]", sql, params);
                return cache;
            } else {
                Log.get().error("本地缓存取值失败[{}][{}]", sql, params);
            }
        }
        Log.get().debug(sql, params);
        // 获取对象字段信息
        T t;
        List<Entity> query;
        List<T> tList = new ArrayList<>();
        BeanInfo beanInfo = new BeanInfo(clazz);
        List<Field> fields = beanInfo.getFieldNames();
        List<String> columnNames = beanInfo.getColumnNames(); // 数据库列名字段
        List<Class<?>> fieldTypes = beanInfo.getFieldTypes();
        //开始执行查询操作
        try {
            query = Db.use(DataBaseSource.getDataSource()).query(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        //进行实体类字段映射转换，这里 查询的字段顺序和对象的申明顺序必须一致
        for (Entity entity : query) {  // 顺序查询
            t = ReflectUtil.newInstance(clazz); // 创建一个实例对象
            Object value = null;
            for (int i = 0; i < fieldTypes.size(); i++) { // 按照对象变量的声明顺序进行set操作
                if (fieldTypes.get(i) == String.class) {
                    value = entity.getStr(columnNames.get(i));
                } else if (fieldTypes.get(i) == int.class || fieldTypes.get(i) == Integer.class) {
                    value = entity.getInt(columnNames.get(i));
                } else if (fieldTypes.get(i) == double.class || fieldTypes.get(i) == Double.class) {
                    value = entity.getDouble(columnNames.get(i));
                } else if (fieldTypes.get(i) == boolean.class || fieldTypes.get(i) == Boolean.class) {
                    value = entity.getBool(columnNames.get(i));
                } else if (fieldTypes.get(i) == float.class || fieldTypes.get(i) == Float.class) {
                    value = entity.getFloat(columnNames.get(i));
                }
                ReflectUtil.setFieldValue(t, fields.get(i), value);
            }
            tList.add(t);
        }
        if (enableCache) {
            SessionCache.setCache(action, tList);
        }
        return tList;
    }

    @Override
    public void init() {
        DataConfig config = Environment.getConfig(DataConfig.class);
        int cacheSize = config.getCacheSize();
        long cacheUnit = config.getCacheUnit();
        enableCache = cacheSize != -1 && cacheUnit != -1;
        if (enableCache) {
            SessionCache.initialize(cacheSize, cacheUnit);
        }
    }
}
