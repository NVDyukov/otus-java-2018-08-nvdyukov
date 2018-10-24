package ru.otus;

import java.util.Date;

public class MyElement<K, V> {
    private final K key;
    private final V value;
    private final long creationTime;
    private long lastAccessTime;


    public MyElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }

    @Override
    public String toString() {
        return "MyElement{ " +
                "key=" + key +
                ", value=" + value +
                ", creationTime=" + new Date(creationTime).toInstant() +
                ", lastAccessTime=" + new Date(creationTime).toInstant() +
                " }";
    }
}
