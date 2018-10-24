package ru.otus;

public interface CacheEngine<K, V> extends AutoCloseable {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();
}
