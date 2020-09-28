package com.nj.baijiayun.module_common.temple;

import com.nj.baijiayun.module_common.mvp.MultiStateView;

import java.util.List;

public interface SearchView<T> extends MultiStateView {
    void dataSuccess(List<T> data, boolean isRefresh);

    void dataSuccess2(List<T> data);

    void loadFinish(boolean loadMoreEnable);

    void saveSearchString(String title);
}
