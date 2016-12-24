package com.otcyan.frameexample_master.ui.classify.welfare;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.otcyan.frameexample_master.R;
import com.otcyan.frameexample_master.bean.Welfare;
import com.otcyan.jutil.ToastUtil;
import com.otcyan.jwidget.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author snamon
 * 福利界面.
 */

public class WelfareActivity extends BaseActivity {

    @BindView(R.id.rv_welfare)
    public RecyclerView mRecyclerView;
    WelfareAdapter mWelfareAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_welfare;
    }

    @Override
    protected boolean hasToolBar() {
        return true;
    }

    @Override
    protected void initData() {
        //初始化toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setTitleTextColor(getResources().getColor(R.color.text_dark , null));
        }else{
            mToolbar.setTitleTextColor(getResources().getColor(R.color.text_dark));
        }
    mToolbar.setNavigationIcon(R.drawable.selector_toolbar_back);

    mToolbar.setTitle("干货集中营");

    Bundle bundle = getIntent().getExtras();
    ArrayList<Welfare> welfares = bundle.getParcelableArrayList("welfares");
    if(welfares!=null){
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));
        mWelfareAdapter = new WelfareAdapter(this , welfares);
        mRecyclerView.setAdapter(mWelfareAdapter);

    }else{
        ToastUtil.showShort("没有数据.");
    }

}
}
