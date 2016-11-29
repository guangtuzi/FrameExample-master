package com.otcyan.frameexample_master.ui.classify;

import com.otcyan.frameexample_master.bean.Welfare;
import com.otcyan.jwidget.IBaseView;

import java.util.List;

/**
 * 分类的vp关联类.
 */

public interface IClassifyContract {

    interface IClassifyView extends IBaseView{

        void showProgressDialog();
        void hideProgressDialog();
        void success(List<Welfare> welfares) ;
    }

    interface IClassifyPresenter{

        void acquireWelfare(int pageIndex);

    }

}
