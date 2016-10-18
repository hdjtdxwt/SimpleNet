package com.safeway.request.base;


import android.os.Parcel;
import android.os.Parcelable;

import com.safeway.request.inter.ResponseListener;

import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * 请求结果类,继承自BasicHttpResponse,将结果存储在rawData中.
 *
 * @author mrsimple
 */
public class Response<T> implements Parcelable {
    public int statusCode;

    public byte[] data;



    public Response() {
    }

    protected Response(Parcel in) {
        statusCode = in.readInt();
        data = in.createByteArray();
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Response(int statusCode, byte[] data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(data);
        dest.writeInt(statusCode);
    }

}