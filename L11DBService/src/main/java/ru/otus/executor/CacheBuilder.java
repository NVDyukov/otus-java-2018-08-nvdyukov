package ru.otus.executor;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheBuilder {
    private CacheManager manager;
    private int entries = 2;

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public CacheBuilder() {
        manager = CacheManagerBuilder
                .newCacheManagerBuilder()
                .build();
        manager.init();
    }

    public Cache build(String name, Class<?> classKey, Class<?> classValue) {
        return manager.createCache(name, CacheConfigurationBuilder
                .newCacheConfigurationBuilder(classKey, classValue, ResourcePoolsBuilder.heap(entries)));
    }

    public void close() {
        manager.close();
    }
}
