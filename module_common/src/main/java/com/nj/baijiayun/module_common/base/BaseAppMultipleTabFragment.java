package com.nj.baijiayun.module_common.base;

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
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public abstract class BaseAppMultipleTabFragment<T extends BasePresenter> extends BaseAppFragment<T> {
    ViewPager mVp;
    public ArrayList<Fragment> fragments;
    private TabFragmentPagerAdapter adapter;
    private MagicIndicator mMagicIndicator;
    private String[] mTitles;

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_fragment_mult_tab;
    }

    @Override
    protected void initView(View mContextView) {
        mVp = mContextView.findViewById(R.id.vp);
        mMagicIndicator = mContextView.findViewById(R.id.magic_indicator);
        fragments = new ArrayList<>();
        setTab();
    }


    public void setTab() {
        initTab(addTabs(), addFragment());
    }

    public void initTab(String[] titles, ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        this.mTitles = titles;
        initVp();
        initIndicator();
        adapter = new TabFragmentPagerAdapter(getChildFragmentManager(), fragments, titles);
        mVp.setAdapter(adapter);
        indicatorBindToVp();

    }

    private void indicatorBindToVp() {
        ViewPagerHelper.bind(mMagicIndicator, mVp);

    }


    public void initIndicator() {
        IndicatorHelper.initDefaultIndicator(getContext(), mMagicIndicator, mVp, mTitles, getUnSelectColor(), getSelectColor(), getIndicatorColor(), isNeedAdjust());
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
        return ContextCompat.getColor(getContext(), R.color.common_indicator_selected);
    }

    public int getUnSelectColor() {
        return ContextCompat.getColor(getContext(), R.color.common_tab_text_unselected);
    }

    public int getSelectColor() {
        return ContextCompat.getColor(getContext(), R.color.common_tab_text_selected);
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