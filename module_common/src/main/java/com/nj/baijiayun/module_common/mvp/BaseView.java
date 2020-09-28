package com.nj.baijiayun.module_common.mvp;

import androidx.annotation.StringRes;

/**
 * Created by desin on 2017/1/12.
 */

public interface BaseView {
    void showToastMsg(String msg);
    void showToastMsg(@StringRes int strIds);
    void showLoadV(String msg);
    void showLoadV();
    void closeLoadV();
}
