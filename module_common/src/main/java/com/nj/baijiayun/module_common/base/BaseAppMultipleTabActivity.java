package com.nj.baijiayun.module_common.base;

import android.os.Bundle;
import android.view.View;

import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.adapter.TabFragmentPagerAdapter;
import com.nj.baijiayun.module_common.helper.IndicatorHelper;
import com.nj.baijiayun.module_common.mvp.BasePresenter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * @author chengang
 * @date 2019/4/28
 * @describe 用来快速构建多tab 页面
 */
public abstract class BaseAppMultipleTabActivity<T extends BasePresenter> extends BaseAppActivity<T> {

    ViewPager mVp;
    public ArrayList<Fragment> fragments;
    private TabFragmentPagerAdapter adapter;
    private MagicIndicator mMagicIndicator;
    private View mViewLine;
    private String[] mTitles;


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_activity_mult_tab;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mVp = findViewById(R.id.vp);
        mMagicIndicator = findViewById(R.id.magic_indicator);
        mViewLine = findViewById(R.id.view_line);
        fragments = new ArrayList<>();
        setTab();
    }

    public void needTopLine() {
        mViewLine.setVisibility(View.VISIBLE);
    }

    public void setTab() {
        initTab(addTabs(), addFragment());
    }

    public void initTab(String[] titles, ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        this.mTitles = titles;
        initVp();
        initIndicator();
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mVp.setAdapter(adapter);
        indicatorBindToVp();

    }

    private void indicatorBindToVp() {
        ViewPagerHelper.bind(mMagicIndicator, mVp);

    }


    public void initIndicator() {
        IndicatorHelper.initDefaultIndicator(this, mMagicIndicator, mVp, mTitles, getUnSelectColor(), getSelectColor(), getIndicatorColor(), isNeedAdjust());
    }

    /**
     * 是否需要自适应 ,滚动的时候设置false
     *
     * @return true
     */
    public boolean isNeedAdjust() {
        return true;
    }

    private Integer getIndicatorColor() {
        return ContextCompat.getColor(this, R.color.common_indicator_selected);
    }

    public int getUnSelectColor() {
        return ContextCompat.getColor(this, R.color.common_tab_text_unselected);
    }

    public int getSelectColor() {
        return ContextCompat.getColor(this, R.color.common_tab_text_selected);
    }


    public void initVp() {
    }

    public TabFragmentPagerAdapter getVpAdapter() {
        return adapter;
    }

    public View getTabLayout() {
        return mMagicIndicator;
    }

    public ViewPager getVp() {
        return mVp;
    }

    public abstract String[] addTabs();

    public abstract ArrayList<Fragment> addFragment();
}
