package org.learn.framework.cache;

import cn.hutool.log.Log;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存构造
 */
public class CacheBuilder {

    private static final int CACHE_CONTAINER_SIZE = 16;

    private static final String YZ_CACHE = "yz_cache"; // 默认缓存实例名称

    /**
     * 存放缓存实例的容器
     */
    private static final Map<String,CacheInstance> CACHE_CONTAINER = new ConcurrentHashMap<>(CACHE_CONTAINER_SIZE);

    private CacheBuilder(){}

    /**
     * 清空全局缓存池
     */
    public static void clear(){
        CACHE_CONTAINER.clear();
    }

    public static YzCache getCache(){
        return getCache(YZ_CACHE);
    }

    /**
     * 获取缓存，若缓存不存在则返回空
     * @param cacheName 缓存名称
     * @return 缓存实例
     */
    public static YzCache getCache(String cacheName){
        return CACHE_CONTAINER.get(cacheName);
    }

    /**
     * 构建默认缓存实例
     * @return 默认缓存实例
     */
    public static YzCache build(){
        return CacheBuilder.build(YZ_CACHE);
    }

    /**
     * 构造自定义缓存，若缓存存在则复用缓存
     * @param cacheName 缓存名称
     * @return 缓存实例
     */
    public static YzCache build(String cacheName){
        if (CACHE_CONTAINER.size() >= CACHE_CONTAINER_SIZE){
            Log.get().error("缓存创建失败，已达最大上限");
            throw new RuntimeException("缓存创建失败，已达最大上限");
        }
        CacheInstance cacheInstance = CACHE_CONTAINER.get(cacheName);
        if (cacheInstance != null){
            return cacheInstance;
        }
        CacheInstance instance = new CacheInstance(cacheName);
        CACHE_CONTAINER.put(cacheName, instance);
        return instance;
    }

    /**
     * 重新构造缓存，若缓存存在则会覆盖以前的缓存
     * @param cacheName 缓存名称
     * @return 缓存实例
     */
    public static YzCache rebuild(String cacheName){
        CacheInstance cacheInstance = new CacheInstance(cacheName);
        CACHE_CONTAINER.put(cacheInstance.getCacheName(), cacheInstance);
        return cacheInstance;
    }

    private static class CacheInstance implements YzCache{

        private final String cacheName;

        private static int queueSize = 1024;

        private final AtomicInteger SIZE = new AtomicInteger();

        private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<>();

        private static Queue<Object> cacheQueue = new ArrayBlockingQueue<>(queueSize);

        public CacheInstance(String cacheName){
            this.cacheName = cacheName;
        }

        @Override
        public String getCacheName(){
            return this.cacheName;
        }

        @Override
        public int size() {
            return this.SIZE.get();
        }

        @Override
        public boolean exists(String key) {
            return CACHE_MAP.containsKey(key);
        }

        @Override
        public boolean set(String key, Object value) {
            if (CACHE_MAP.containsKey(key)){
                Log.get().error("缓存中已包含该键值对");
                return false;
            }
            this.SIZE.incrementAndGet();
            CACHE_MAP.put(key, value);
            return true;
        }

        @Override
        public void update(String key, Object value) {
            if (!CACHE_MAP.containsKey(key)){
                this.SIZE.incrementAndGet();
            }
            CACHE_MAP.put(key, value);
        }

        @Override
        public boolean delete(String key) {
            Object remove = CACHE_MAP.remove(key);
            if (remove != null){
                this.SIZE.decrementAndGet();
                return true;
            }
            return false;
        }

        @Override
        public Object get(String key) {
            return CACHE_MAP.get(key);
        }

        @Override
        public boolean push(Object value) {
            queueSizeCheck();
            this.SIZE.incrementAndGet();
            return cacheQueue.offer(value);
        }

        @Override
        public Object pop() {
            this.SIZE.decrementAndGet();
            return cacheQueue.poll();
        }

        private void queueSizeCheck(){
            if (cacheQueue.size() == queueSize){
                queueSize = queueSize * 2; // 扩容
                Queue<Object> newCacheQueue = new ArrayBlockingQueue<>(queueSize);
                for (Object value : cacheQueue) {
                    newCacheQueue.offer(value);
                }
                cacheQueue = newCacheQueue;
            }
        }
    }
}
