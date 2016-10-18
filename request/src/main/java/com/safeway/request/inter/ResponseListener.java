package com.safeway.request.inter;

/**
 * Created by Administrator on 2016/7/9.
 */
public interface ResponseListener<T > {
    void onSuccess(T t, int code);
    void onFail(Exception e);
}
