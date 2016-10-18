package com.safeway.request.core;

import com.safeway.request.base.RequestTask;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Administrator on 2016/7/11.
 * 这一个类的作用是用来暂存所有的请求，虽然ExecutorService自带一个队列，但是那个队列里存的都是Runnable接口，Runnable接口没有优先级的
 * 而RequestTask可以有优先级，所以我们用一个队列来存RequestTask，优先级高的再队列的头部(就会先出队列)
 */
public class RequestQueue {
    //单例，就维护一个队列
    public static RequestQueue instance;

    //有优先级的线程安全的队列
    public static PriorityBlockingQueue<RequestTask> mQueue = new PriorityBlockingQueue<>();

    private RequestQueue() {
    }

    public static RequestQueue getInstance() {
        if (instance == null) {
            synchronized (RequestQueue.class) {
                instance = new RequestQueue();
            }
        }
        return instance;
    }


    //将指定元素插入此优先级队列。
    public void put(RequestTask e) {
        mQueue.add(e);
    }

    //获取并移除此队列的头，如果此队列为空，则返回 null。
    public RequestTask poll() {
        return mQueue.poll();
    }

}
