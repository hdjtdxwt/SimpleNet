package com.safeway.request.response;

import android.os.Handler;
import android.os.Looper;

import com.safeway.request.base.Request;
import com.safeway.request.base.Response;

import java.util.concurrent.Executor;

/**
 * Created by Thinkpad on 2016/6/23.
 */
public class ResponseDelivery {
    public static void delivery(Runnable r) {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(r);
    }

}
