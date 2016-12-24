package com.otcyan.frameexample_master;

import com.google.gson.annotations.SerializedName;

import com.otcyan.jnet.BaseResult;


/**
 *@author snamon
 *后台返回的数据格式.
 */

public class Result<T> extends BaseResult<T>{

    public boolean error;
    @SerializedName("results")
    public T data;

    @Override
    public boolean isSuccess() {
        return !error;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public int getCode() {
        return error ? -1 : 0 ;
    }

    @Override
    public String getErrorDesc() {
        return "";
    }
}
