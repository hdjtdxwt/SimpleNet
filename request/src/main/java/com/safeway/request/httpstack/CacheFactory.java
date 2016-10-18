package com.safeway.request.httpstack;

import com.safeway.request.cache.Cache;
import com.safeway.request.cache.LruMemCache;

/**
 * Created by Administrator on 2016/7/11.
 */
public class CacheFactory {
    public static Cache buildDefaultCache(){
        return LruMemCache.getInstance();
    }
}
