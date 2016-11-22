package com.otcyan.frameexample_master.ui.classify;


import com.otcyan.frameexample_master.R;
import com.otcyan.jwidget.BaseFragment;

import android.os.Bundle;
import android.view.View;

public class ClassifyFragment extends BaseFragment implements ClassifyContract.IClassifyView{

    ClassifyPresenter mClassifyPresenter;

    public static ClassifyFragment get(){
        return new ClassifyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        mClassifyPresenter = new ClassifyPresenter();
        mClassifyPresenter.attachView(this);
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }
}
