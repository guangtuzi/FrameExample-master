package com.otcyan.frameexample_master;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.otcyan.frameexample_master.ui.classify.ClassifyFragment;
import com.otcyan.frameexample_master.ui.news.NewsFragment;
import com.otcyan.frameexample_master.ui.other.OtherFragment;
import com.otcyan.jwidget.BaseActivity;
import com.otcyan.jwidget.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    public TabLayout mTabLayout;
    @BindView(R.id.viewpage)
    public ViewPager mViewPager;

    private int[] icoRes = {R.drawable.selector_tab_new, R.drawable.selector_tab_classify, R.drawable.selector_tab_more};
    private String[] nameRes = {"最新", "分类", "更多"};

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean hasToolBar() {
        return false;
    }

    @Override
    protected void initData() {
        List<BaseFragment> baseFragmentList = new ArrayList<>();
        baseFragmentList.add(new NewsFragment());
        baseFragmentList.add(new ClassifyFragment());
        baseFragmentList.add(new OtherFragment());
        //初始化viewpage
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(this.getSupportFragmentManager(), baseFragmentList);
        mViewPager.setAdapter(mainFragmentAdapter);
        //初始化tablayout
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
            ((ImageView) ButterKnife.findById(view, R.id.imageView)).setImageResource(icoRes[i]);
            ((TextView) ButterKnife.findById(view, R.id.textView)).setText(nameRes[i]);
            if (tab != null)
                tab.setCustomView(view);
        }


        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
    }
}
