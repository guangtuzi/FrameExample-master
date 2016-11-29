package com.otcyan.frameexample_master.ui.classify.welfare;

import com.otcyan.frameexample_master.R;
import com.otcyan.frameexample_master.bean.Welfare;
import com.otcyan.jwidget.BaseActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * .
 */

public class WelfareActivity extends BaseActivity {

    @BindView(R.id.welfare_tv)
    public TextView tv;
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
        mToolbar.setTitle("干货集中营");
//        setToolBarTitle("福利");

        Bundle bundle = getIntent().getExtras();
        ArrayList<Welfare> welfares = bundle.getParcelableArrayList("welfares");
        StringBuilder sb = new StringBuilder();
        if(welfares!=null){
            for( Welfare welfare: welfares){
                sb.append("who:").append(welfare.who).append(" -- url : ").append(welfare.url).append("\n");
            }
            tv.setText(sb.toString());
        }else{
            tv.setText("没有数据!");
        }

    }
}
