package com.nj.baijiayun.module_common.mvp;

/**
 * Created by desin on 2017/2/23.
 */

public interface MultiStateView extends BaseView {
    void showLoadView();
    void showNoNetWorkView();
    void showNoDataView();
    void showErrorDataView();
    void showContentView();
    void showNoLogin();
}
