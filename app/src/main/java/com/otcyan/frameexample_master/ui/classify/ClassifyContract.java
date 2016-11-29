package com.otcyan.frameexample_master.ui.classify;

import com.otcyan.jwidget.IBaseView;

/**
 * 分类的vp关联类.
 */

public interface ClassifyContract {

    interface IClassifyView extends IBaseView{

    }

    interface IClassifyPresenter{

        void acquireWelfare(int pageIndex);

    }

}
