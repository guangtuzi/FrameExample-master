package com.otcyan.jwidget;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * presenter基本类.
 */
public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected  T mvpView;
    protected CompositeSubscription mCompositeSubscription;

    /**
     * 线程切换.
     *
     */
    protected <S> Observable.Transformer<S, S> applyDefault() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void attachView(T t) {
        this.mvpView =  t;

        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription.clear();
        mvpView = null;
    }
}
