package com.otcyan.frameexample_master.data;

import com.otcyan.frameexample_master.api.ApiTool;
import com.otcyan.frameexample_master.bean.Welfare;

import java.util.List;

import rx.Observable;

/**
 * @author snamon
 * 分类模块M层数据仓库 这里控制数据来源(网络remote或者本地local).
 */

public class ClassifyRepository implements ClassifyDataSource{

    @Override
    public Observable<List<Welfare>> acquireWelfare(int pageIndex) {
        return ApiTool.get().acquireWelfare(pageIndex);
    }
}
