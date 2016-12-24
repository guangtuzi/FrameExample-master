package com.otcyan.frameexample_master.api;

import com.otcyan.jnet.APIException;
import com.otcyan.jutil.ToastUtil;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author snamon
 * 订阅Subscriber 统一的处理.
 */

public class CommonSubscribe<T> extends Subscriber<T> {

    private Action1<T> mAction1;

    public CommonSubscribe(Action1<T> action1) {
        this.mAction1 = action1;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof APIException) {
            //什么重新登录 都在些做处理
            ToastUtil.showShort(((APIException) e).errorMsg);
        } else {
            //网络其他的异常
            ToastUtil.showShort(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        if (mAction1 != null)
            mAction1.call(t);
    }

}
