package org.example.cache;

import java.util.Optional;

/**
 * Cache service interface
 * 表示缓存服务接口
 * CacheService接口定义了缓存服务的基本操作，包括添加、获取、删除和清空缓存。
 * @param <K>
 * @param <V>
 */
public interface CacheService<K, V> {
    void put(K key, V value, long ttlSeconds);

    void put(K key, V value);
    Optional<V> get(K key);
    void remove(K key);
    void clear();
}
