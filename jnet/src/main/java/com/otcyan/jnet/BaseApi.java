package com.otcyan.jnet;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 对前端api进行统一封装，由每一个具体实现者继承.
 */
public class BaseApi {

    /**
     * 包含重试、异常处理、线程切换.
     *
     * @param <T> 接口的ResponseBody类型
     */
    protected <T> Observable.Transformer<T, T> applyDefault() {
        return tObservable -> tObservable.retryWhen(new ApiRetryFunc(), Schedulers.immediate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 包含重试、异常处理，不切换线程.
     *
     * @param <T> 接口的ResponseBody类型
     */
    protected <T> Observable.Transformer<T, T> applyWithoutSchedule() {
        return tObservable -> tObservable.retryWhen(new ApiRetryFunc(), Schedulers.immediate());
    }

    /**
     * 对后台返回的数据进行分隔
     *
     * @param resultObservable 服务器返回的Observale
     * @param <T>              类型
     */
    @SuppressWarnings("unchecked")
    protected <T> Observable<T> flatResponse(Observable<? extends BaseResult<T>> resultObservable) {

        return resultObservable.filter(result -> result != null).flatMap(resultBase -> {
            if (resultBase.isSuccess()) {
                T data = resultBase.getData();
                if (data != null) {
                    return Observable.just(data);
                } else {
                    return Observable.empty();//只会调用onComplete
                }
            } else {
                return Observable.error(new APIException(resultBase.getCode(), resultBase.getErrorDesc()));
            }
        });
    }

}
