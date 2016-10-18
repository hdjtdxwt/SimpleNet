package com.safeway.request.requests;

import com.safeway.request.base.Request;
import com.safeway.request.base.Response;
import com.safeway.request.inter.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Thinkpad on 2016/6/23.
 */
public class JsonObjectRequest extends Request<JSONObject> {
    public JsonObjectRequest(HttpMethod method, String url, ResponseListener<JSONObject> listener) {
        super(method, url, listener);
    }

    /**
     * 将Response的结果转换为JSONObject
     */
    public JSONObject parseResponse(Response response) {
        String jsonString = new String(response.getData());
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
