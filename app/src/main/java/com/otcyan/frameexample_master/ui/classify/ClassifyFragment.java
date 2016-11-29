package com.otcyan.frameexample_master.ui.classify;


import com.otcyan.frameexample_master.R;
import com.otcyan.frameexample_master.bean.Welfare;
import com.otcyan.frameexample_master.ui.classify.welfare.WelfareActivity;
import com.otcyan.frameexample_master.util.JumpUtil;
import com.otcyan.jlog.JLog;
import com.otcyan.jwidget.BaseFragment;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment implements IClassifyContract.IClassifyView{

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

        viewClick(R.id.apk).subscribe(aVoid -> {mClassifyPresenter.acquireWelfare(1);}) ;

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

    @Override
    public void onDetach() {
        super.onDetach();
        mClassifyPresenter.detachView();
    }

    @Override
    public void success(List<Welfare> welfares) {
        JLog.i("获取福利数据success.");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("welfares" , (ArrayList<Welfare>) welfares);
        JumpUtil.jumpActivity(mActivity , WelfareActivity.class , bundle);
    }
}
