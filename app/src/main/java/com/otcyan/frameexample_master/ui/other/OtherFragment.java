package com.otcyan.frameexample_master.ui.other;


import com.otcyan.frameexample_master.R;
import com.otcyan.jwidget.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends BaseFragment {

    public static OtherFragment get(){
        return new OtherFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
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
