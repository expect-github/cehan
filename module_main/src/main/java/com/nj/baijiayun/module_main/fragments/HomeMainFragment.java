package com.nj.baijiayun.module_main.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiayun.bjyrtcengine.BaseAdapter;
import com.baijiayun.groupclassui.base.CommonAdapter;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.widget.BadgeView;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.helper.RefreshHelper;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.adapter.wx.NewBannerWxHolder;
import com.nj.baijiayun.module_main.bean.NavBean;
import com.nj.baijiayun.module_main.bean.NewBannerBean;
import com.nj.baijiayun.module_main.bean.PublicOpenListWrapperBean;
import com.nj.baijiayun.module_main.bean.WxBannerItemBean;
import com.nj.baijiayun.module_main.bean.wx.ChannelInfoBean;
import com.nj.baijiayun.module_main.helper.BeanSpaceItemDecoration;
import com.nj.baijiayun.module_main.mvp.contract.MainContract;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.RefreshListDataHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.mvp.contract.CourseContract;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.extend.HeaderAndFooterRecyclerViewAdapter;
import com.nj.baijiayun.refresh.smartrv.INxOnRefreshListener;
import com.nj.baijiayun.refresh.smartrv.INxRefreshLayout;
import com.nj.baijiayun.refresh.smartrv.NxRefreshView;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-06-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class HomeMainFragment extends BaseAppFragment<MainContract.Presenter> implements MainContract.View {

    private NxRefreshView mRefreshLayout;
    private BaseMultipleTypeRvAdapter adapter;
    private BadgeView mBadgeView;
    private ImageView mIvMsg;
    @Inject
    public CourseContract.Presenter mCoursePresenter;

    private NewBannerWxHolder bannerWxHolder;
    private View mViewSearch;
    private ImageView mSignIv;
    private View mPersonService;


    @Override
    public void takeView() {
        super.takeView();
        mCoursePresenter.takeView(this);
    }

    @Override
    public void dropView() {
        super.dropView();
        mCoursePresenter.dropView();

    }

    @Override
    protected int bindLayout() {
        return R.layout.main_fragement_home_mulit_status;
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_nxrefresh_layout;
    }

    @Override
    protected void initView(View mContextView) {
        getConvertView().setBackground(null);
        mPersonService = mContextView.findViewById(R.id.view_person_service);
        mRefreshLayout = mContextView.findViewById(R.id.refreshLayout);
        mViewSearch = mContextView.findViewById(R.id.view_search);
        mIvMsg = mContextView.findViewById(R.id.iv_msg);
        mSignIv = mContextView.findViewById(R.id.iv_sign);
        mBadgeView = mContextView.findViewById(R.id.badge_view);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter();
        //将列表适配器套在头部适配器
        adapter = Module_mainAdapterHelper.getDefaultAdapter(getContext());
        headerAndFooterRecyclerViewAdapter.setAdapter(adapter);
        //吧banner放在头部
        bannerWxHolder = new NewBannerWxHolder(mRefreshLayout.getRecyclerView());
        bannerWxHolder.getConvertView().setVisibility(View.GONE);
        headerAndFooterRecyclerViewAdapter.addHeaderView(bannerWxHolder.getConvertView());
        mRefreshLayout.setAdapter(headerAndFooterRecyclerViewAdapter);

        BeanSpaceItemDecoration beanSpaceItemDecoration = new BeanSpaceItemDecoration(adapter);
        mRefreshLayout.getRecyclerView().addItemDecoration(beanSpaceItemDecoration.setHeadViewCount(1));
        RefreshListDataHelper.registerCourseHasBuyChange(this, adapter);
        addSpaceItem(beanSpaceItemDecoration);
//        mRefreshLayout.getRecyclerView().setBackgroundResource(R.color.bjplayer_color_ad_blue);
        mRefreshLayout.setBackgroundResource(R.drawable.common_home_bg);
        ViewHelper.setViewVisible(mPersonService, ConfigManager.getInstance().isShowPersonService());
    }

    private void addSpaceItem(BeanSpaceItemDecoration beanSpaceItemDecoration) {
        BeanSpaceItemDecoration.EdgeBean edgeBean = new BeanSpaceItemDecoration.EdgeBean() {
            @Override
            public void handleItem(int col, int cols, View view, Rect outRect, RecyclerView parent, int pos) {
                super.handleItem(col, cols, view, outRect, parent, pos);
                if (beanSpaceItemDecoration.getItem(pos) instanceof ChannelInfoBean) {
                    if (((ChannelInfoBean) beanSpaceItemDecoration.getItem(pos)).isPublicOpenCourse()) {
                        outRect.top = 0;
                    }
                }
            }
        };
        beanSpaceItemDecoration.addMarginEdge(ChannelInfoBean.class, edgeBean.setPreTopMargin(8));
        beanSpaceItemDecoration.addMarginEdge(PublicOpenListWrapperBean.class, new BeanSpaceItemDecoration.EdgeBean().setPreTopMargin(12).setNextBottomMargin(12));
    }

    @Override
    public void registerListener() {
        mPersonService.setOnClickListener(v -> JumpHelper.jumpPersonService());
        mSignIv.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getSign());
        });
        mViewSearch.setOnClickListener(v -> ARouter.getInstance().build(RouterConstant.PAGE_COURSE_SEARCH).navigation());
        if (multipleStatusView != null) {
            multipleStatusView.setOnRetryClickListener(v -> mPresenter.getList(true));
        }
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setOnRefreshLoadMoreListener(new INxOnRefreshListener() {
            @Override
            public void onRefresh(INxRefreshLayout nxRefreshLayout) {
                mPresenter.getList(false);
            }

            @Override
            public void onLoadMore(INxRefreshLayout nxRefreshLayout) {

            }
        });
        getConvertView().findViewById(R.id.fl_msg).setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getMsg());
        });
        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE, Boolean.class).observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                mPresenter.getList(true);
            }
        });
    }


    @Override
    public void processLogic() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            if (mPresenter != null) {
                mPresenter.getMessageCount();
            }
        }
        //修改的
        mSignIv.setVisibility(View.GONE);
//        ViewHelper.setViewVisible(mSignIv, ConfigManager.getInstance().getAppConfig().needShowIntegralModule());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter != null) {
                mPresenter.getMessageCount();
            }
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getList(true);
    }

    @Override
    public void dataSuccess(List data, boolean isRefresh) {
        adapter.addAll(data, isRefresh);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setEnableRefresh(true);


    }

    @Override
    public void loadFinish(boolean loadMoreEnable) {
        RefreshHelper.finishLoading(loadMoreEnable, mRefreshLayout);
    }


    @Override
    public void setNewBannerData(List<NewBannerBean> bannerData) {
        WxBannerItemBean wxBannerItemBean = new WxBannerItemBean();
        wxBannerItemBean.setData(bannerData);
        bannerWxHolder.bindData(wxBannerItemBean, 0, null);
        bannerWxHolder.getConvertView().setVisibility(View.VISIBLE);

    }

    @Override
    public void setListData(List<Object> bannerData) {
        adapter.addAll(bannerData, true);
        RefreshHelper.finishLoading(false, mRefreshLayout);

    }

    @Override
    public void setShowMsgPoint(boolean needShow) {
        mBadgeView.showRedPoint(needShow);
    }

    @Override
    public void setNavData(List<NavBean> navData) {
        bannerWxHolder.getConvertView().setVisibility(View.VISIBLE);
        bannerWxHolder.setEntranceNavData(navData);

    }

    @Override
    public void setShowUnreadCount(int count) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBadgeView.getLayoutParams();
        layoutParams.leftMargin = -DensityUtil.dip2px(count > 9 ? 12 : 7);
        mBadgeView.setLayoutParams(layoutParams);
        mBadgeView.setBadgeCount(count);
    }

}
