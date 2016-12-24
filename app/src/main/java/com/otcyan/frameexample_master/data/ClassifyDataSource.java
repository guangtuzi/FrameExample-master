package com.otcyan.frameexample_master.data;

import com.otcyan.frameexample_master.bean.Welfare;

import java.util.List;

import rx.Observable;

/**
 * @author snamon
 * 分类模块M层接口.
 */

public interface ClassifyDataSource {

    /**
     * 请求获取福利数据
     * @param pageIndex 页码数
     */
    Observable<List<Welfare>>  acquireWelfare(int pageIndex);

}
