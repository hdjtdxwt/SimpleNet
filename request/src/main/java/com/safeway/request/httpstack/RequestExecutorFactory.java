package com.safeway.request.httpstack;

/**
 * Created by Administrator on 2016/7/9.
 */
public class RequestExecutorFactory {
    public static HttpExecutor buildDefaultExecutor(){
        return new OkHttpExecutor();
    }
}
