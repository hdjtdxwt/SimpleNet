package com.safeway.request.cache;

/**
 * Created by Thinkpad on 2016/6/23.
 */
public interface Cache<K,V> {
    public V get(K key);
    public void put(K key,V value);
    public void remove(K key);
}
