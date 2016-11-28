package com.otcyan.frameexample_master.ui.classify;

import com.otcyan.frameexample_master.api.ApiTool;
import com.otcyan.frameexample_master.api.CommonSubscribe;
import com.otcyan.jwidget.BasePresenter;

/**
 * .
 */

public class ClassifyPresenter extends BasePresenter<IClassifyContract.IClassifyView> implements IClassifyContract.IClassifyPresenter {

    @Override
    public void acquireWelfare(int pageIndex) {

        mCompositeSubscription.add(ApiTool.get().acquireWelfare(pageIndex)
                .doOnRequest(aLong -> mvpView.showProgressDialog())
                .doOnTerminate(() -> mvpView.hideProgressDialog())
                .subscribe(new CommonSubscribe<>(welfares -> mvpView.success(welfares))));


    }
}
