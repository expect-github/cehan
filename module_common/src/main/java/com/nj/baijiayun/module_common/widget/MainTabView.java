package com.nj.baijiayun.module_common.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.adapter.TabFragmentPagerAdapter;
import com.nj.baijiayun.module_common.widget.jptabbar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


public class MainTabView extends LinearLayout {
    private View mView;
    private JPTabBar bottomNavigationBar;
    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private View mLineView;
    //记得跟xmL同步
    private int defaultIconSize = DensityUtil.dip2px(25);

    public MainTabView(Context context) {
        this(context, null);
    }

    public MainTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public JPTabBar getBottomNavigationBar() {
        return bottomNavigationBar;
    }

    public void hideBottom() {
        if (bottomNavigationBar.getVisibility() == GONE) {
            return;
        }
        bottomNavigationBar.setVisibility(GONE);
        mLineView.setVisibility(GONE);

    }

    public void switchBottomVisible(int visible) {
        bottomNavigationBar.setVisibility(visible);
        mLineView.setVisibility(visible);


    }

    public boolean isBottomVisible() {
        return bottomNavigationBar.getVisibility() == VISIBLE;
    }

    public void showBottom() {
        if (bottomNavigationBar.getVisibility() == VISIBLE) {
            return;
        }
        bottomNavigationBar.setVisibility(VISIBLE);
        mLineView.setVisibility(VISIBLE);
    }


    public ViewPager getViewPager() {
        return mViewPager;
    }

    private void initView() {
        setOrientation(VERTICAL);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.common_layout_main_tab_view, this);
        mViewPager = mView.findViewById(R.id.main_vp);
        mLineView = mView.findViewById(R.id.view_line);
        bottomNavigationBar = mView.findViewById(R.id.tabbar);
    }

    /**
     * 设置底部导航将要绑定的fragment
     *
     * @param fragment
     */
    public MainTabView setFragments(List<Fragment> fragment) {
        mFragments = new ArrayList<>();
        mFragments.addAll(fragment);

        return this;
    }

    /***
     * 设置底部导航未选择字体颜色
     * @param iconNoIcon
     */
    public MainTabView setBottomTextNoColor(int iconNoIcon) {
        bottomNavigationBar.setNormalColor(iconNoIcon);
        return this;
    }

    /***
     * 设置底部导航选择字体颜色
     * @param iconNoIcon
     */
    public MainTabView setBottomTextSelectColor(int iconNoIcon) {
        bottomNavigationBar.setSelectedColor(iconNoIcon);
        return this;
    }


    public MainTabView setBottomSelectColor(int color) {
        bottomNavigationBar.setSelectedColor(color);
        return this;
    }

    public MainTabView setBottomNormalColor(int color) {
        bottomNavigationBar.setNormalColor(color);
        return this;
    }

    /***
     * 设置底部导航未选择图标
     * @param iconNoIcon
     */
    public MainTabView setBottomNoIcon(int... iconNoIcon) {
        bottomNavigationBar.setNormalIcons(iconNoIcon);
        return this;
    }


    /**
     * 设置底部导航选择图标
     *
     * @param iconSelectIcon
     */
    public MainTabView setBottomSelectIcon(int... iconSelectIcon) {
        bottomNavigationBar.setSelectedIcons(iconSelectIcon);
        return this;
    }

    /***
     * 设置底部导航未选择图标
     * @param iconNoIcon
     */
    public MainTabView setBottomTextNoIcon(int... iconNoIcon) {
        bottomNavigationBar.setNormalIcons(iconNoIcon);
        return this;
    }

    public MainTabView setIconSize(int size) {
        bottomNavigationBar.setIconSize(size);
        return this;
    }

    /**
     * 设置底部导航选择图标
     *
     * @param iconSelectIcon
     */
    public MainTabView setBottomTextSelectIcon(int... iconSelectIcon) {
        bottomNavigationBar.setSelectedTextIcons(iconSelectIcon);
        return this;
    }

    /**
     * 设置底部导航标题
     *
     * @param titls
     */
    public MainTabView setBottomTitles(String... titls) {
        bottomNavigationBar.setTitles(titls);
        return this;
    }

    private static RequestOptions requestOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false);

    public MainTabView setRemoteUrl(String[] normalUrl, String[] selectedUrl) {

        for (String s : normalUrl) {
            Glide.with(getContext()).load(s).apply(requestOptions).preload(defaultIconSize, defaultIconSize);
        }
        for (String s : selectedUrl) {
            Glide.with(getContext()).load(s).apply(requestOptions).preload(defaultIconSize, defaultIconSize);

        }

        bottomNavigationBar.setSelectedUrl(selectedUrl);
        bottomNavigationBar.setNormalUrl(normalUrl);
        return this;
    }


    /**
     * 绑定数据
     *
     * @param fragmentManager
     * @param position
     */
    public void bindFragment(FragmentManager fragmentManager, int position) {
        bottomNavigationBar.generate();
        mViewPager.removeAllViews();
        mViewPager.setAdapter(new TabFragmentPagerAdapter(fragmentManager, (ArrayList<Fragment>) mFragments));
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        bottomNavigationBar.setContainer(mViewPager);
    }


    /**
     * 是否显示小红点
     *
     * @param position 在第几个选项卡上显示小红点
     * @param text     将要显示的文本
     */
    public void setBadge(int position, String text) {
        bottomNavigationBar.showBadge(position, text);
    }

    /**
     * 隐藏小红点
     *
     * @param position 隐藏第几个小红点
     */
    public void hindBadge(int position) {
        bottomNavigationBar.hideBadge(position);
    }

    /**
     * 设置底部导航的监听事件
     *
     * @param onTabSelectListener
     */
    public void setBottomLister(OnTabSelectListener onTabSelectListener) {
        bottomNavigationBar.setTabListener(onTabSelectListener);
    }

    /**
     * 重新设置某一个底部导航的文字
     *
     * @param position
     * @param txt
     */
    public void setTitle(int position, String txt) {
        bottomNavigationBar.setTitle(position, txt);
    }

    private void setVp(int position) {
        mViewPager.setCurrentItem(position);
    }

    /**
     * 切换fragment，同时携带参数
     *
     * @param position 目标位置
     * @param extra    参数
     */
    public void switchFragment(int position, Bundle extra) {
        if (extra != null) {
            mFragments.get(position).setArguments(extra);
        }
        setVp(position);
    }


    /**
     * 切换fragment，同时携带参数
     *
     * @param position 目标位置
     */
    public void switchFragment(int position) {
        switchFragment(position, null);
    }


}
