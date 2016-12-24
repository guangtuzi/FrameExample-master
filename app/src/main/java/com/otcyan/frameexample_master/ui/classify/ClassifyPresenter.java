package com.otcyan.frameexample_master.ui.classify;

import com.otcyan.frameexample_master.api.CommonSubscribe;
import com.otcyan.frameexample_master.data.ClassifyRepository;
import com.otcyan.jutil.ToastUtil;
import com.otcyan.jwidget.BasePresenter;

import rx.Observable;
import rx.Subscription;

/**
 * @author snamon
 *         分类模块P层.
 */

public class ClassifyPresenter extends BasePresenter<IClassifyContract.IClassifyView> implements
        IClassifyContract.IClassifyPresenter {

    private ClassifyRepository mClassifyRepository;

    public ClassifyPresenter() {
        mClassifyRepository = new ClassifyRepository();
    }

    @Override
    public void acquireWelfare(int pageIndex) {
        bindView.showProgressDialog();
        Subscription subscription = mClassifyRepository.acquireWelfare(pageIndex)
                .onErrorResumeNext(throwable -> {
                    // TODO: 2016/12/24 错误统一处理
                    ToastUtil.showShort(throwable.getMessage());
                    return Observable.just(null);
                })
                .filter(welfares -> welfares != null)
                .doOnTerminate(() -> bindView.hideProgressDialog())
                .subscribe(new CommonSubscribe<>(welfares -> bindView.success(welfares)));
        mCompositeSubscription.add(subscription);

    }
}
