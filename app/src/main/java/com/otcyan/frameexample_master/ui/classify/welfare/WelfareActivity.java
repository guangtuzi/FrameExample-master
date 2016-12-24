package com.otcyan.frameexample_master.ui.classify.welfare;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.otcyan.frameexample_master.Constant;
import com.otcyan.frameexample_master.R;
import com.otcyan.frameexample_master.bean.Welfare;
import com.otcyan.frameexample_master.data.ClassifyRepository;
import com.otcyan.jutil.ToastUtil;
import com.otcyan.jwidget.BaseActivity;
import com.otcyan.jwidget.LoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * @author snamon
 *         福利界面.
 */

public class WelfareActivity extends BaseActivity {

    @BindView(R.id.rv_welfare)
    public LoadMoreRecyclerView mLoadMoreRecyclerView;
    WelfareAdapter mWelfareAdapter;
    ClassifyRepository mClassifyRepository;
    PublishSubject<String> mLoadMoreSubject = PublishSubject.create();
    int pageIndex = 2;

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
        mClassifyRepository = new ClassifyRepository();
        mLoadMoreSubject.flatMap(s -> mClassifyRepository.acquireWelfare(pageIndex++))
                .onErrorResumeNext(throwable -> {
                    ToastUtil.showShort(throwable.getMessage());
                    return Observable.just(null);
                })
                .filter(welfares -> welfares != null)
                .doOnNext(welfares -> {
                    //没有P层业务  直接从M层获取数据
                    mWelfareAdapter.addWelfareList(welfares);
                    mLoadMoreRecyclerView.notifyMoreFinish(welfares.size()==Constant.COUNT);
                })
                .subscribe();

        //初始化toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mToolbar.setTitleTextColor(getResources().getColor(R.color.text_dark, null));
        } else {
            mToolbar.setTitleTextColor(getResources().getColor(R.color.text_dark));
        }
        mToolbar.setNavigationIcon(R.drawable.selector_toolbar_back);

        mToolbar.setTitle("干货集中营");

        Bundle bundle = getIntent().getExtras();
        ArrayList<Welfare> welfares = bundle.getParcelableArrayList("welfares");
        if (welfares != null) {
            mLoadMoreRecyclerView.setLayoutManager(
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mWelfareAdapter = new WelfareAdapter(this, welfares);
            mLoadMoreRecyclerView.setAdapter(mWelfareAdapter);
            mLoadMoreRecyclerView.setAutoLoadMoreEnable(true);
            mLoadMoreRecyclerView.setLoadMoreListener(() -> {
                //加载更多
                mLoadMoreSubject.onNext("");
            });
        } else {
            ToastUtil.showShort("没有数据.");
        }

    }
}
