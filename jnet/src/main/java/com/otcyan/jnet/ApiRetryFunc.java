package com.otcyan.jnet;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;

/**
 * 网络异常重试.
 */
public class ApiRetryFunc implements Func1<Observable<? extends Throwable>, Observable<?>> {

    /** 重试次数. */
    private static final int RETRY_TIMES = 2;
    /** 重试间隔，单位毫秒. */
    private static final long RETRY_INTERVAL = 500L;

    /** 重试次数. */
    private int mRetryTimes;
    /** 重试间隔，单位毫秒. */
    private long mRetryInterval;

    public ApiRetryFunc() {
        mRetryTimes = RETRY_TIMES;
        mRetryInterval = RETRY_INTERVAL;
    }

    public ApiRetryFunc(int retryTimes, long retryInterval) {
        mRetryTimes = retryTimes;
        mRetryInterval = retryInterval;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {

        return observable.zipWith(Observable.range(1, mRetryTimes), (throwable, integer) -> throwable).flatMap((Func1<Throwable, Observable<?>>) throwable -> {
            if (throwable instanceof ConnectException
                    || throwable instanceof TimeoutException
                    || throwable instanceof SocketTimeoutException) {
                return Observable.timer(mRetryInterval, TimeUnit.MILLISECONDS);
            }
            return Observable.error(throwable);
        });
    }
}