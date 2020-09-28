package com.nj.baijiayun.module_public.p_set.ui;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.temple.AbstractListPresenter;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.p_set.api.SetService;
import com.nj.baijiayun.module_public.p_set.bean.SupervisionBean;
import com.nj.baijiayun.module_public.p_set.bean.response.SupervisionListRes;
import com.nj.baijiayun.module_public.temple.BaseSimpleListFragment;
import com.nj.baijiayun.module_public.widget.UserItemView;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2020-03-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_set.ui
 * @describe
 */
public class SupervisionFragment extends BaseSimpleListFragment {
    @Override
    protected AbstractListPresenter createPresenter() {
        return new AbstractListPresenter<SupervisionListRes>() {
            @Override
            public List resultCovertToList(SupervisionListRes result) {
                return result.getData();
            }

            @Override
            public Observable<SupervisionListRes> getListObservable(int page) {
                return NetMgr.getInstance().getDefaultRetrofit().create(SetService.class).getSupervisionList();
            }


        };
    }

    @Override
    public BaseRecyclerAdapter createRecyclerAdapter() {
        return new BaseRecyclerAdapter<SupervisionBean>(getContext()) {
            @Override
            protected int attachLayoutRes() {
                return R.layout.p_set_item_supervision;
            }

            @Override
            protected void bindViewAndData(BaseViewHolder holder, SupervisionBean supervisionBean, int position) {
                ((UserItemView) holder.getView(R.id.item_view)).setTitleStr(supervisionBean.getDevice());
            }

        };
    }

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        getNxRefreshView().setEnableLoadMore(false);
        getNxRefreshView().setEnableRefresh(false);
        mContextView.setBackgroundColor(Color.WHITE);

        //重写initView 覆盖之前的adapter
        HeaderAndFooterRecyclerViewAdapter adapter = new HeaderAndFooterRecyclerViewAdapter(getAdapter());
        TextView headView = new TextView(getContext());
        headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headView.setPadding(DensityUtil.dip2px(15), DensityUtil.dip2px(19), DensityUtil.dip2px(15), DensityUtil.dip2px(13));
        headView.setText(R.string.public_supervision);
        adapter.addHeaderView(headView);
        getNxRefreshView().setAdapter(adapter);

    }

    @Override
    protected void onPageItemClick(BaseViewHolder holder, int position, View view, Object item) {
        ARouter.getInstance()
                .build(RouterConstant.PAGE_WEB_VIEW)
                .withString("data", ((SupervisionBean) item).getSupervision())
                .withString("title", ((SupervisionBean) item).getDevice())
                .navigation();
    }

    @Override
    public void dataSuccess(List data, boolean isRefresh) {
        super.dataSuccess(data, isRefresh);
        getNxRefreshView().setEnableLoadMore(false);
        getNxRefreshView().setEnableRefresh(false);
    }

    @Override
    public void showNoDataView() {
//        super.showNoDataView();
        showContentView();
    }
}
