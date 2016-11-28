package com.otcyan.frameexample_master;

import com.otcyan.frameexample_master.api.NetInterceptor;
import com.otcyan.jnet.Api;
import com.otcyan.jnet.ProtocolType;

import android.app.Application;

/**
 * .
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化api
        Api.init("http://gank.io/api/data/" , ProtocolType.JSON);
        Api.get().addInterceptor(new NetInterceptor());
    }
}
