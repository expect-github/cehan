package com.nj.baijiayun.module_common.demo;

import android.view.View;

import com.nj.baijiayun.module_common.base.BaseListEmptyContract;
import com.nj.baijiayun.module_common.temple.AbstractListFragment;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import javax.inject.Inject;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.demo
 * @describe
 */
public class DemoListFragment extends AbstractListFragment<BaseListEmptyContract.Presenter> {

    @Inject
    public DemoListFragment() {
    }
    @Override
    public BaseRecyclerAdapter createRecyclerAdapter() {
        return null;
    }

    @Override
    protected void onPageItemClick(BaseViewHolder holder, int position, View view, Object item) {

    }

    @Override
    public void processLogic() {

    }
}
