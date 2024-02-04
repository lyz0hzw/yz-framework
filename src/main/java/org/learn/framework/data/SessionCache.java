package org.learn.framework.data;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

/**
 * 数据库会话缓存，缓存数据库查询结果，使用最近最久未使用缓存
 */
public class SessionCache {

    private static Cache<Action, Object> cache;

    private static long unit;

    public static void initialize(int cacheSize,long cacheUnit){
        cache = CacheUtil.newLRUCache(cacheSize); // 最近最久未使用缓存
        unit = cacheUnit;
    }

    public static void setCache(Action action,Object result){
        cache.put(action, result, unit);
    }

    public static Object getCache(Action action){
        return cache.get(action);
    }

    public static void clear(){
        if (cache != null) {
            cache.clear();
        }
    }
}
