package com.safeway.request.requests;

import com.safeway.request.base.Request;
import com.safeway.request.base.Response;

/**
 * Created by Thinkpad on 2016/6/23.
 */
public class StringRequest extends Request<String> {
    public StringRequest(){
    }

    public String parseResponse(Response<String> response) {
        return new String(response.getData());
    }

}
