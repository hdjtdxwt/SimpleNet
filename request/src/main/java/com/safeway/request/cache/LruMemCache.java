package com.safeway.request.cache;

import android.support.v4.util.LruCache;

import com.safeway.request.base.Request;
import com.safeway.request.base.Response;

/**
 * Created by Thinkpad on 2016/6/23.
 * key是Request对象，value值是Response对象
 * Request对象要重写equals()和hashCode方法
 */
public class LruMemCache implements Cache<Request, Response> {
    public static LruMemCache instance;

    public static LruMemCache getInstance() {
        if(instance==null){
            synchronized (LruMemCache.class){
                if(instance==null){
                    instance = new LruMemCache();
                }
            }
        }
        return instance;
    }

    private LruCache<Request, Response> mResponseCache;

    private LruMemCache() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        final int cacheSize = maxMemory / 8;
        mResponseCache = new android.support.v4.util.LruCache<Request, Response>(cacheSize) {
            @Override
            protected int sizeOf(Request key, Response value) {
                return (32 + value.getData().length) / 1024; //一个int4个字节，也就是32位
            }
        };
    }

    @Override
    public Response get(Request key) {
        return mResponseCache.get(key);
    }

    @Override
    public void put(Request key, Response value) {
        mResponseCache.put(key, value);
    }

    @Override
    public void remove(Request key) {
        mResponseCache.remove(key);
    }
}
