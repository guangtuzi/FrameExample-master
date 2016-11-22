package com.otcyan.jnet;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author snamon
 *         用于创建管理API服务.
 */
public class Api {

    private static ProtocolType mProtocolType;
    private Retrofit mRetrofit;

    private static String baseUrl;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder httpBuilder;

    private Api() {
        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        mRetrofit = retrofitBuilder.client(httpBuilder.build())
                .build();
    }

    /**
     * 初始化
     *
     * @param baseUrl1     url
     * @param protocolType 协议类型
     */
    public static void init(@NonNull String baseUrl1, @NonNull ProtocolType protocolType) {
            mProtocolType = protocolType;
            baseUrl = baseUrl1;
    }

    public static Api get() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 增加过滤器
     *
     * @param interceptor 过滤器
     */
    public void addInterceptor(@NonNull Interceptor interceptor) {
        checkBaseUrl();
        httpBuilder.addInterceptor(interceptor);
        mRetrofit = retrofitBuilder.client(httpBuilder.build())
                .build();
    }

    public void timeout(long timeout) {
        checkBaseUrl();
        httpBuilder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        mRetrofit = retrofitBuilder.client(httpBuilder.build())
                .build();
    }

    public <S> S create(@NonNull Class<S> serviceClass) {
        checkBaseUrl();
        return mRetrofit.create(serviceClass);
    }

    private void checkBaseUrl() {
        if (baseUrl == null) {
            throw new RuntimeException("must be init api!");
        }
    }

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }
}