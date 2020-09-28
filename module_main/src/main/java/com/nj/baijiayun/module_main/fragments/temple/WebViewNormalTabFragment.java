package com.nj.baijiayun.module_main.fragments.temple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_main.MainActivity;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.temple.BaseAppWebViewFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * @author chengang
 * @date 2020-02-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments.temple
 * @describe
 */
public class WebViewNormalTabFragment extends BaseAppWebViewFragment {

    private String title;
    private boolean needLazyLoad;
    private TextView mTvTitle;
    private Toolbar mToolBar;
    private View mViewLine;
    private int toolBarHeight;
    private View mBackView;


    @Override
    public void initParms(Bundle params) {
        Logger.d("initParms url" + params.toString());
        super.initParms(params);
        title = HomeTabPageHelper.getTitle(params);
        needLazyLoad = HomeTabPageHelper.needLazy(params);
    }

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        mToolBar = mContextView.findViewById(R.id.toolbar);
        mTvTitle = ToolBarHelper.setToolBarTextCenter(mToolBar, title);
        mViewLine = mContextView.findViewById(R.id.view_line);
        mBackView = ToolBarHelper.addBack(getAppToolBar(), v -> {
            if (getWebView() != null && getWebView().canGoBack()) {
                getWebView().goBack();
            }
        });
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) mBackView.getLayoutParams();
        layoutParams.leftMargin=DensityUtil.dip2px(15);
        mBackView.setLayoutParams(layoutParams);
        getAppToolBar().setNavigationIcon(((MainActivity) getActivity()).getToolBar().getNavigationIcon());
        getBackView().setVisibility(View.INVISIBLE);
        setPageTitle(title);
        getAppToolBar().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getAppToolBar().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                toolBarHeight = getAppToolBar().getHeight();
            }
        });
        if (!needLazyLoad) {
            loadUrl();
        }
        setToolBarInPage(true);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        //默认在lazy加载url 去掉这个逻辑
        if (needLazyLoad) {
            super.onLazyInitView(savedInstanceState);
        }
    }

    @Override
    public void registerListener() {
        super.registerListener();
        setTitleCallBack(this::setPageTitle);
    }


    @Override
    protected int bindLayout() {
        return R.layout.main_fragment_with_toolbar;
    }

    private void setPageTitle(String title) {
        Logger.d("setPageTitle" + title);
        if (TextUtils.isEmpty(title)) {
            return;
        }
        mTvTitle.setText(title);
    }

    private View getBackView() {
        return mBackView;
    }

    boolean isMainPage(String url) {
        return getFirstLoadUrl().equals(url);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (getWebView() != null && getWebView().canGoBack()) {
            //只要不是答题的页面 可以带返回
            if (!isInQuestionPage(getWebView().getUrl())) {
                getWebView().goBack();
            }
            return false;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void urlChange(String url) {
        super.urlChange(url);
        Logger.d("urlChange:" + url + "---" + getFirstLoadUrl() + "--cangoBack" + getWebView().canGoBack());
        ViewHelper.setViewVisible(getBackView(), !isMainPage(url));
//        ViewHelper.setViewVisible(getToolBar(), !isInQuestionPage(url));
        animateOpen(getAppToolBar(), toolBarHeight, !isInQuestionPage(url));
        mViewLine.setVisibility(getAppToolBar().getVisibility());

    }

    @Override
    protected Toolbar getAppToolBar() {
        return mToolBar;
    }

    private boolean isInQuestionPage(String url) {
        return URLCacheManager.getInstance().isUrlInPathList(url, FILTER_URL_NO_ACTION_BAR);
    }


    private void animateOpen(View v, int height, boolean show) {

        if (show == (v.getVisibility() == View.VISIBLE)) {
            return;
        }
        ViewHelper.setViewVisible(v, true);
        int start = show ? 0 : height;
        int end = show ? height : 0;
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(arg0 -> {
            int value = (int) arg0.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            layoutParams.height = value;
            v.setLayoutParams(layoutParams);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewHelper.setViewVisible(v, show);
                mViewLine.setVisibility(getAppToolBar().getVisibility());

            }
        });
        animator.setDuration(250);
        animator.start();

    }

    protected void setToolBarInPage(boolean isMain) {
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) mTvTitle.getLayoutParams();

        if (isMain) {
            layoutParams.gravity = Gravity.START;
            mTvTitle.setPadding(DensityUtil.dip2px(18), 0, 0, 0);
            mTvTitle.setTextSize(21);
            mTvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        } else {
            layoutParams.gravity = Gravity.CENTER;
            mTvTitle.setPadding(0, 0, 0, 0);
            mTvTitle.setTextSize(17);
            mTvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//加粗

        }
        mTvTitle.setLayoutParams(layoutParams);
    }

}
