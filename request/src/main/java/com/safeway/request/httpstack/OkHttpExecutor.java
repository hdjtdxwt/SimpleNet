package com.safeway.request.httpstack;

import android.util.Log;

import com.safeway.request.base.Request;
import com.safeway.request.base.RequestTask;
import com.safeway.request.base.Response;
import com.safeway.request.inter.ResponseListener;
import com.safeway.request.response.ResponseDelivery;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;

/**
 * 使用okhttp来完成请求
 */
public class OkHttpExecutor<T> extends HttpExecutor {
    OkHttpClient client = new OkHttpClient();

    public void stopExecotr() {
        isStop = true;
    }

    public void run() {
        while(!isStop){
            final RequestTask<T> task = taskQueue.poll();
            if (task != null && !task.isCanceled()) {
                Log.e("run","task==null ? "+(task==null)+"-----"+taskQueue);
                Runnable runnable= new Runnable() {
                    @Override
                    public void run() {
                        final ResponseListener listener = task.getResponseListener();
                        final Request req = task.getRequest();
                        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder().url(req.getUrl());
                        if (cache.get(req)!=null) {
                            Log.e("cache","---该请求有缓存");
                            final Response<T> response = (Response<T>) cache.get(req);
                            ResponseDelivery.delivery(new Runnable() {
                                @Override
                                public void run() {
                                    if (response != null && listener != null) {
                                        T result = task.getRequest().parseResponse(response);
                                        listener.onSuccess(result, response.statusCode);
                                    }
                                }
                            });
                        }else{
                            Map<String, String> header = req.getHeaders();
                            if (header != null && header.size() > 0) {
                                Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
                                while (iterator.hasNext()) {
                                    Map.Entry<String, String> entry = iterator.next();
                                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                                }
                            }

                            Map<String, String> body = req.getBodyParams();
                            FormBody formBody = null;
                            if (body != null && body.size() > 0) {
                                Iterator<Map.Entry<String, String>> iterator = body.entrySet().iterator();
                                FormBody.Builder requestBodyBuilder = new FormBody.Builder();

                                while (iterator.hasNext()) {
                                    Map.Entry<String, String> entry = iterator.next();
                                    requestBodyBuilder.add(entry.getKey(), entry.getValue());
                                }
                                formBody = requestBodyBuilder.build();
                                Request.HttpMethod method = req.getMethod();
                                if (method == Request.HttpMethod.POST) {
                                    requestBuilder.post(formBody);
                                } else if (method == Request.HttpMethod.DELETE) {
                                    requestBuilder.delete(formBody);
                                } else if (method == Request.HttpMethod.PUT) {
                                    requestBuilder.put(formBody);
                                }
                            }
                            final okhttp3.Request request = requestBuilder.build();
                            Call call = client.newCall(request);
                            Log.e("OkHttpServer", "ThreadName:" + Thread.currentThread().getName());
                            //请求加入调度
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, final IOException e) {
                                    Log.e("onFailure",e.getMessage());
                                    ResponseDelivery.delivery(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (listener != null) {
                                                listener.onFail(e);
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(final Call call, final okhttp3.Response response) throws IOException {
                                    Log.e("onResponse","onResponse---执行了");
                                    ResponseDelivery.delivery(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (response != null && listener != null) {
                                                    byte[] data = response.body().bytes();
                                                    int code = response.code();
                                                    Response<T>tResponse = new Response(code,data);
                                                    if(req.isCache()){//存到内存缓存中
                                                        cache.put(req,tResponse);
                                                        Log.e("cache","---保存请求结果作为缓存内容");
                                                    }
                                                    T result = task.getRequest().parseResponse(tResponse);
                                                    listener.onSuccess(result, code);
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        }

                    }
                };
                executorService.execute(runnable);
            }

        }

    }
}
