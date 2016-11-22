package com.otcyan.jwidget;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxRadioGroup;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by 01096612 on 2016/6/24.
 * 2、统一进行滑动返回的处理  如果Activity能滑动返回 一定要设置主题为透明 styles/BlankTheme
 * 3、统一对ToolBar进行处理
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected boolean isCanSwipeBack = false; //默认是不能滑动返回 如果设置返回的时候 在manifest主题一定要设置为styles/BankTheme 代码中可能失效
    private SwipeBackLayout mSwipeBackLayout;
    protected boolean swipeEnabled = true; //是否禁止滑动
    protected boolean swipeAnyWhere = true; //是否能够在任何位置滑动

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;
    private Dialog loadingDialog;

    protected abstract int getLayout();

    protected abstract boolean hasToolBar();

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        ButterKnife.bind(this);

        // TODO: 2016/11/5 这里要考虑状态栏在不同fragment的颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        if (isCanSwipeBack) {
            //初始化滑动返回
            mSwipeBackLayout = new SwipeBackLayout(this);
            mSwipeBackLayout.setSwipeAnyWhere(swipeAnyWhere);
            mSwipeBackLayout.setSwipeEnabled(swipeEnabled);
        }
        if (hasToolBar())
            initToolBar();
        initData();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isCanSwipeBack)
            mSwipeBackLayout.replaceLayer(this); //在这个activity里加入自己的swipeBackLayout层
    }

    @Override
    public void finish() {
        if (isCanSwipeBack) {
            if (mSwipeBackLayout.getswipeFinished()) {
                super.finish();
                overridePendingTransition(0, 0);
            } else {
                mSwipeBackLayout.cancelPotentialAnimation();
                super.finish();
                overridePendingTransition(0, R.anim.slide_right_out);
            }
        } else {
            super.finish();
        }
    }

    /**
     * 每一个设置了Toolbar的Activity，设置其返回键功能正常
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                int fragment_size = getSupportFragmentManager().getBackStackEntryCount();
                if (0 < fragment_size) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示重新登录对话框
     */
    protected void showReLoginDialog() {

    }

    public void showProgressDialog() {
        if (null == loadingDialog) {
            loadingDialog = new Dialog(this, R.style.dl_base_loading);
            loadingDialog.setContentView(R.layout.view_loading);
        }
        if (loadingDialog.isShowing()) return;
        if (hasWindowFocus()) loadingDialog.show();
    }

    public void hideProgressDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) loadingDialog.dismiss();
    }


    private void initToolBar() {
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mToolbarTitle = ButterKnife.findById(this, R.id.toolbar_base_title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setTitle(getTitle());
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 设置Actionbar左边标题
     */
    protected void setToolBarLeftTitle(String title) {
        if (mToolbar != null)
            mToolbar.setTitle(title);
    }

    /**
     * 设置Actionbar中间标题
     */
    protected void setToolBarTitle(String title) {
        if (mToolbarTitle != null)
            mToolbarTitle.setText(title);
    }

    /**
     * 隐藏Actionbar返回按钮
     */
    public void hideBackBtn() {
        if (mToolbar != null)
            mToolbar.setNavigationIcon(null);
    }

    /**
     * 通过rxbinding进行click绑定
     */
    public Observable<Void> viewClick(int resId) {
        return RxView.clicks(ButterKnife.findById(this, resId)).throttleFirst(500, TimeUnit.MILLISECONDS).compose(bindToLifecycle());
    }

    /**
     * 通过rxbinding进行RadioGroup绑定
     *
     * @param resId RadioGroup资源id
     */
    public Observable<Integer> viewCheckedChanges(int resId) {
        return RxRadioGroup.checkedChanges(ButterKnife.findById(this, resId)).throttleFirst(100, TimeUnit.MILLISECONDS).compose(this.<Integer>bindToLifecycle());
    }
}
