package com.nj.baijiayun.module_public.temple;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
public abstract class BaseRecycleFragment<T extends BasePresenter> extends BaseAppFragment<T> {

    private RecyclerView mRv;
    private BaseRecyclerAdapter mAdapter;

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_layout_rv;
    }

    @Override
    protected void initView(View mContextView) {
        mRv = mContextView.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = createAdapter();
        mRv.setAdapter(mAdapter);
    }

    public RecyclerView getRv() {
        return mRv;
    }

    public BaseRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    public abstract BaseRecyclerAdapter createAdapter();

    @Override
    public void registerListener() {

    }


}
