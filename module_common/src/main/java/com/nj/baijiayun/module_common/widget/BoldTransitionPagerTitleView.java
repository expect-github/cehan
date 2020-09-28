package com.nj.baijiayun.module_common.widget;

import android.content.Context;
import android.graphics.Typeface;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * 带颜色渐变和缩放的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class BoldTransitionPagerTitleView extends ColorTransitionPagerTitleView {

    public BoldTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        // 实现颜色渐变
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        // 实现颜色渐变
        setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

    }
}
