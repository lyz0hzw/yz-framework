package org.learn.framework.cache;


public interface YzCache {

    /**
     * 获取缓存名称
     * @return 名称
     */
    String getCacheName();

    /**
     * 获取缓存大小
     * @return 缓存大小
     */
    int size();

    /**
     * 是否存在某键值对
     * @param key 键
     * @return 是否存在
     */
    boolean exists(String key);

    /**
     * 设置键值对
     * @param key 键
     * @param value 值
     * @return 是否设置成功
     */
    boolean set(String key, Object value);

    /**
     * 更新键值对
     * @param key 键
     * @param value 值
     */
    void update(String key, Object value);

    /**
     * 删除某键值对
     * @param key 键
     * @return 是否删除成功
     */
    boolean delete(String key);

    /**
     * 设置键值对
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 添加值到队列中
     * @param value 值
     * @return 是否添加成功
     */
    boolean push(Object value);

    /**
     * 从队列中获取值
     * @return 值
     */
    Object pop();
}
