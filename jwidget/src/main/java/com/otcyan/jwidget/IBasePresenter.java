package com.otcyan.jwidget;

/**
 * @author snamon
 */
public interface IBasePresenter<T> {

    void attachView(T t); //绑定的View
    void detachView() ; //解绑view 释放一些资源

}
