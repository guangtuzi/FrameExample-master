package com.otcyan.frameexample_master.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.otcyan.jlog.JLog;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * .
 */

public class NetInterceptor implements Interceptor {

    private Gson mGson ;
    public NetInterceptor(){
        mGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //response
        Response response = chain.proceed(request);
        int code = response.code();
        String bodyString = response.body().string();
        String logStr = "[request]::" + response.request().url().toString() + ";\n[header]::" + response.request().headers().toString();
        if (200 == code) {
            if (!TextUtils.isEmpty(bodyString)) {
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(bodyString);
                JLog.e(logStr + "[response]::" + mGson.toJson(je));
            }
        } else {//失败
            JLog.e(logStr + "[response]::" + code + bodyString);
        }
        Response newResponse = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
        return newResponse;
    }
}
