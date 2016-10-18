package com.safeway.request.httpstack;

import com.safeway.request.base.RequestTask;
import com.safeway.request.cache.Cache;
import com.safeway.request.cache.LruMemCache;
import com.safeway.request.core.RequestQueue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 请求线程的父抽象类
 */
public abstract class HttpExecutor<T> extends Thread{
    boolean isStop;//线程池是否停止运行

    public static RequestQueue taskQueue = RequestQueue.getInstance();

    public static Cache cache = LruMemCache.getInstance();

    //执行Runnable请求的线程池
    public final static ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 8, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public void stopExecotr(){
        isStop=true;
    }

    public void setStop(boolean stop){
        this.isStop =stop;
    }
}
