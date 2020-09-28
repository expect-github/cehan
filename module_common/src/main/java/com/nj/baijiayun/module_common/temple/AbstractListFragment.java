package com.nj.baijiayun.module_common.temple;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.helper.RefreshHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.refresh.smartrv.INxRefreshLayout;
import com.nj.baijiayun.refresh.smartrv.NxRefreshView;

import java.util.List;

/**
 * @author chengang
 * @date 2019-06-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.temple
 * @describe
 */
public abstract class AbstractListFragment<T extends AbstractListPresenter> extends BaseAppFragment<T> implements IMultiRecyclerView {
    NxRefreshView nxRefreshView;
    protected BaseRecyclerAdapter mAdapter;


    @Override
    protected void initView(View mContextView) {
        nxRefreshView = mContextView.findViewById(R.id.refreshLayout);
        mAdapter = createRecyclerAdapter();
        nxRefreshView.setAdapter(mAdapter);
    }

    public NxRefreshView getNxRefreshView() {
        return nxRefreshView;
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_nxrefresh_layout;
    }

    /**
     * create Adapter
     *
     * @return BaseMultipleTypeRvAdapter
     */
    public abstract BaseRecyclerAdapter createRecyclerAdapter();

    public BaseRecyclerAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void dataSuccess(List data, boolean isRefresh) {
        if (multipleStatusView != null) {
            multipleStatusView.showContent();
        }
        mAdapter.addAll(data, isRefresh);
        nxRefreshView.setEnableLoadMore(true);
        nxRefreshView.setEnableRefresh(true);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadListData();

    }

    @Override
    public void registerListener() {
        if (multipleStatusView != null) {
            multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadListData();
                }
            });
        }
        if(nxRefreshView!=null) {
            nxRefreshView.setOnRefreshLoadMoreListener(new INxOnRefreshListener() {
                @Override
                public void onRefresh(INxRefreshLayout nxRefreshLayout) {
                    mPresenter.getList(true);
                }

                @Override
                public void onLoadMore(INxRefreshLayout nxRefreshLayout) {
                    mPresenter.getList(false);
                }
            });
        }
        if(mAdapter!=null) {
            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseViewHolder holder, int position, View view, Object item) {
                    onPageItemClick(holder, position, view, item);
                }
            });
        }
    }

    protected abstract void onPageItemClick(BaseViewHolder holder, int position, View view, Object item);

    public void refreshData()
    {
        if(mPresenter!=null) {
            mPresenter.getList(true);
        }
    }

    /**
     * 重新加载页面数据
     */
    public void loadListData() {
        mPresenter.getList();
    }

    @Override
    public void loadFinish(boolean loadMoreEnable) {
        RefreshHelper.finishLoading(loadMoreEnable, nxRefreshView);
    }


}
