package com.otcyan.jnet;

/**
 * Created by snamon on 2016/8/3.
 * 服务器返回的结果类 ，由调用者具体实现
 */
public abstract  class BaseResult<T> {

    public abstract boolean isSuccess() ;

    public abstract T getData() ;

    public abstract int getCode()  ;

    public abstract  String getErrorDesc() ;

}
