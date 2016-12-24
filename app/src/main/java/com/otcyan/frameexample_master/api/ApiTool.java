package com.otcyan.frameexample_master.api;

import com.otcyan.frameexample_master.Constant;
import com.otcyan.frameexample_master.bean.Welfare;
import com.otcyan.jnet.Api;
import com.otcyan.jnet.BaseApi;

import java.util.List;

import rx.Observable;

/**
 * .
 */

public class ApiTool extends BaseApi{


    public static ApiTool get(){
        return  SingletonHolder.INSTANCE;
    }

    private ApiTool(){}

    public Observable<List<Welfare>> acquireWelfare(int pageIndex){
        return flatResponse(Api.get().create(ApiService.class).acquireClassify(Constant.COUNT , pageIndex))
                .compose(this.applyDefault());
    }


    private static class SingletonHolder {
        private static final ApiTool INSTANCE = new ApiTool();
    }

}
