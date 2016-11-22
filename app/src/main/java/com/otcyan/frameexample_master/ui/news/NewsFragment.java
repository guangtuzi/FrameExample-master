package com.otcyan.frameexample_master.ui.news;


import com.otcyan.frameexample_master.R;
import com.otcyan.jwidget.BaseFragment;

import android.os.Bundle;
import android.view.View;

public class NewsFragment extends BaseFragment {

    public static NewsFragment get(){
        return new NewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }
}
