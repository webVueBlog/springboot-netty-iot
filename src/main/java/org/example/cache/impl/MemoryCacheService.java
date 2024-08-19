package org.example.cache.impl;

import org.example.cache.CacheService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 内存缓存实现
@Service
public class MemoryCacheService<K, V> implements CacheService<K, V> {
    private final Map<K, V> cache = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value, long ttlSeconds) {
        put(key,value);
        // 可选：在指定时间后清除缓存项
        if (ttlSeconds > 0) {
            scheduleCacheExpiration(key, ttlSeconds);
        }
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    // 可选：在指定时间后清除缓存项的具体实现
    private void scheduleCacheExpiration(K key, long ttlSeconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            cache.remove(key);
        }, ttlSeconds, TimeUnit.SECONDS);
    }
}