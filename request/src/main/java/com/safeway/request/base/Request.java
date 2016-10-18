package com.safeway.request.base;

import com.safeway.request.inter.ResponseListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Thinkpad on 2016/6/23.
 * 注意：这个Request类保存的是一个请求的相关信息，一开始的想法是不需要这个类，直接使用Okhttp的Request，但是考虑到如果用户不想用Okhttp来请求
 * 那么其他的请求里头就用不上这个Okhttp里的Request了，所以还是得有个类来保存一下用户的请求信息
 */
public abstract class Request<T> implements Serializable {
    private String url;
    private boolean isCache=true;//是否要缓存
    private HttpMethod method;
    private Map<String, String> headers;
    private Map<String, String> bodyParams;
    private Object tag;
    public int priority;//请求有限级别（0~100)
    ResponseListener<T> listener;

    public Request() {
        headers = new HashMap<>();
        bodyParams = new HashMap<>();
    }

    public Request(HttpMethod method, String url, ResponseListener<T> listener) {
        this.method = method;
        this.url = url;
        this.listener = listener;
    }

    public static enum HttpMethod {
        GET("GET"),
        POST("POST"),
        DELETE("DELETE"),
        PUT("PUT");
        private String methodName;

        HttpMethod(String name) {
            this.methodName = name;
        }

        @Override
        public String toString() {
            return methodName;
        }
    }

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addParams(String key, String value) {
        bodyParams.put(key, value);
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getBodyParams() {
        return bodyParams;
    }

    public void setBodyParams(Map<String, String> bodyParams) {
        this.bodyParams = bodyParams;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void setPriority(int priority) {
        if (priority > 100) {
            priority = 100;
        } else if (priority < 0) {
            priority = 0;
        }
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * 从原生的网络请求中解析结果
     *
     * @param response
     * @return
     */
    public abstract T parseResponse(Response<T> response);

    @Override
    public boolean equals(Object o) {
        if(o instanceof Request){
            Request<T> req = (Request<T>)o;
            if(req.getMethod()==this.method && req.getUrl().equals(this.url)){
                if(req.bodyParams!=null && this.bodyParams!=null){
                    Iterator<Map.Entry<String,String>>iter =  bodyParams.entrySet().iterator();
                    boolean flag = true;
                    while(iter.hasNext()){
                        Map.Entry<String,String>entry = iter.next();
                        if(req.bodyParams.containsKey(entry.getKey()) && req.bodyParams.get(entry.getKey()).equals(bodyParams.get(entry.getKey()))){
                            continue;
                        }else{
                            flag = false;
                            return false;
                        }
                    }
                    return flag;
                }else if(bodyParams==null && req.bodyParams==null){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return url.hashCode()+method.hashCode();
    }
}
