package com.nj.baijiayun.module_common.temple;


import com.nj.baijiayun.module_common.mvp.MultiStateView;

import java.util.List;

/**
 * @project zywx_android
 * @class name：www.baijiayun.module_common.template
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2018/12/18 17:33
 * @change
 * @time
 * @describe
 */
public interface IMultiRecyclerView<T> extends MultiStateView {
    void dataSuccess(List<T> data, boolean isRefresh);

    void loadFinish(boolean loadMoreEnable);
}
