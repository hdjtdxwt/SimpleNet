package com.safeway.request.core;

import android.util.Log;

import com.safeway.request.base.Request;
import com.safeway.request.base.RequestTask;
import com.safeway.request.base.Response;
import com.safeway.request.cache.Cache;
import com.safeway.request.httpstack.HttpExecutor;
import com.safeway.request.httpstack.RequestExecutorFactory;
import com.safeway.request.inter.ResponseListener;

/**
 * Created by Administrator on 2016/7/9.
 */
public class RequestManager {
    public static RequestManager instance;
    private static HttpExecutor executor;//请求的执行者
    private static RequestQueue requestQueue;
    private static Cache<Request,Response> cache;

    private RequestManager(){

    }
    public static RequestManager getInstance(){
        if(instance==null){
            synchronized (RequestManager.class){
                if(instance==null){
                    instance = new RequestManager();
                    init();
                    Log.e("RequestManager-nstance","requestQueue = "+instance);
                }
            }
        }
        return instance;
    }
    //-------------------------------------------------
    private static void init(){
        executor = RequestExecutorFactory.buildDefaultExecutor();
        requestQueue = RequestQueue.getInstance();
        Log.e("RequestManager-init","requestQueue = "+requestQueue);
        executor.start();
    }
    public RequestManager configExecutor(HttpExecutor executor){
        this.executor = executor;
        return this;
    }

    public void enqueue(Request request, ResponseListener responseListener){
        RequestTask task = new RequestTask(request,responseListener);
        requestQueue.put(task);
    }
    public void stop(boolean isStop){
        executor.setStop(isStop);
    }
}
