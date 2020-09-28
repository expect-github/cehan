package com.nj.baijiayun.module_common.helper;

import androidx.annotation.ColorRes;

import com.nj.baijiayun.refresh.SmartRefreshLayout;
import com.nj.baijiayun.refresh.api.RefreshFooter;
import com.nj.baijiayun.refresh.api.RefreshHeader;
import com.nj.baijiayun.refresh.smartrv.NxRefreshView;


/**
 * Created by Administrator on 2018/4/2.
 */

public class RefreshHelper {


    /**
     * 设置刷新头部样式
     *
     * @param refreshHeader
     */
    public static void ReshfreHeadStyle(SmartRefreshLayout smartRefreshLayout, RefreshHeader refreshHeader) {
        smartRefreshLayout.setRefreshHeader(refreshHeader);
    }

    /**
     * 设置上啦加载样式
     *
     * @param refreshFooter
     */
    public static void ReshfreFootStyle(SmartRefreshLayout smartRefreshLayout, RefreshFooter refreshFooter) {
        smartRefreshLayout.setRefreshFooter(refreshFooter);
    }

    /**
     * 设置头部和底部 上啦活下来是否偏移
     *
     * @param headIsTran
     * @param footIsTran
     */
    public static void setEndableFootAndHeadTrans(SmartRefreshLayout smartRefreshLayout, boolean headIsTran, boolean footIsTran) {
        smartRefreshLayout.setEnableFooterTranslationContent(footIsTran);
        smartRefreshLayout.setEnableHeaderTranslationContent(headIsTran);
    }

    /**
     * 设置刷新样式主题颜色
     */
    public static void setPrimaryColorsId(SmartRefreshLayout smartRefreshLayout, @ColorRes int... primaryColorId) {
        smartRefreshLayout.setPrimaryColorsId(primaryColorId);
    }

    /**
     * 关闭各种刷新加载，并是否开启加载更多
     *
     * @param loadMoreEnable
     * @param swipeRefreshLayout
     */
    public static void finishLoading(boolean loadMoreEnable, SmartRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.finishRefresh();
        swipeRefreshLayout.finishLoadMore();
        swipeRefreshLayout.setEnableLoadMore(loadMoreEnable);
    } /**
     * 关闭各种刷新加载，并是否开启加载更多
     *
     * @param loadMoreEnable
     * @param nxRefreshView
     */
    public static void finishLoading(boolean loadMoreEnable, NxRefreshView nxRefreshView) {
        nxRefreshView.finishRefresh();
        nxRefreshView.finishLoadMore();
        nxRefreshView.setEnableLoadMore(loadMoreEnable);
    }
}
