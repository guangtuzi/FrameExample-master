package com.otcyan.frameexample_master.api;

import com.otcyan.frameexample_master.Result;
import com.otcyan.frameexample_master.ui.classify.welfare.Welfare;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * .
 */

public interface ApiService {

    @GET("/福利/{count}/{pageIndex}")
    Observable<Result<List<Welfare>>> acquireClassify(@Path("count") int count , @Path("pageIndex") int pageIndex );

}
