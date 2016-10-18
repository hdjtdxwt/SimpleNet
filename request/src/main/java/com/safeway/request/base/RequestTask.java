package com.safeway.request.base;

import com.safeway.request.inter.ResponseListener;

/**
 * Created by Administrator on 2016/7/9.
 */
public class RequestTask<T> implements Comparable<RequestTask<T>> {
    private boolean isCanceled;

    public void cancel() {
        isCanceled = true;
    }

    private int priority = 50;

    public boolean isCanceled() {
        return isCanceled;
    }

    public long time;//加入到请求队列的时间
    private Request<T> request;
    private ResponseListener<T> responseListener;

    public RequestTask(Request<T> request, ResponseListener responseListener) {
        this.request = request;
        this.responseListener = responseListener;
        this.priority = request.getPriority();
        time = System.currentTimeMillis();
    }

    public Request<T> getRequest() {
        return request;
    }

    public void setRequest(Request<T> request) {
        this.request = request;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public int compareTo(RequestTask<T> another) {
        return (int) (this.priority - another.priority);
    }
}

