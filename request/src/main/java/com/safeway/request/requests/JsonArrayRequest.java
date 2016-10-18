package com.safeway.request.requests;

import com.safeway.request.base.Request;
import com.safeway.request.base.Response;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Thinkpad on 2016/6/23.
 */
public class JsonArrayRequest extends Request<JSONArray> {

    @Override
    public JSONArray parseResponse(Response response) {
        byte[]data = response.getData();
        JSONArray obj = null;
        if(data!=null){
            try {
                obj = new JSONArray(new String(data));
                return obj;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
