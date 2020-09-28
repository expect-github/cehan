package com.nj.baijiayun.module_common.helper;

import android.content.Context;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.widget.BoldTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


/**
 * @author chengang
 * @date 2019-07-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.helper
 * @describe
 */
public class IndicatorHelper {


    public static void initDefaultIndicator(Context context,
                                            MagicIndicator magicIndicator,
                                            ViewPager viewPager,
                                            String[] titles,
                                            int tabUnSelectColor,
                                            int tabSelectColor,
                                            int indicatorSelectColor,
                                            boolean isAdjust
    ) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdjustMode(isAdjust);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                return getPagerTitleView(context, index, tabUnSelectColor, tabSelectColor, titles[index], viewPager);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return getLinePagerIndicator(context, indicatorSelectColor);
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }


    private static LinePagerIndicator getLinePagerIndicator(Context context, int indicatorSelectColor) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineWidth(DensityUtil.dip2px(20));
        indicator.setRoundRadius(DensityUtil.dip2px(2));
        indicator.setColors(ContextCompat.getColor(context, R.color.design_indicator_start_color), ContextCompat.getColor(context, R.color.design_end_color));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        return indicator;
    }

    private static ColorTransitionPagerTitleView getPagerTitleView(Context context, int index, int tabUnSelectColor, int tabSelectColor, String title, ViewPager viewPager) {
        int unSelectSize = (int) context.getResources().getDimension(R.dimen.design_indicator_text_un_selected);
        int selectSize = (int) context.getResources().getDimension(R.dimen.design_indicator_text_selected);
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context) {
            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                super.onLeave(index, totalCount, leavePercent, leftToRight);
                setTextSize(TypedValue.COMPLEX_UNIT_PX, unSelectSize);


            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                super.onEnter(index, totalCount, enterPercent, leftToRight);
                setTextSize(TypedValue.COMPLEX_UNIT_PX, selectSize);


            }
        };
        colorTransitionPagerTitleView.setNormalColor(tabUnSelectColor);
        colorTransitionPagerTitleView.setSelectedColor(tabSelectColor);
        colorTransitionPagerTitleView.setText(title);
        colorTransitionPagerTitleView.setSingleLine(false);
        colorTransitionPagerTitleView.setOnClickListener(view -> viewPager.setCurrentItem(index));
        return colorTransitionPagerTitleView;
    }


    private static BoldTransitionPagerTitleView getBoldTransitionPagerTitleView(Context context, int index, int tabUnSelectColor, int tabSelectColor, String title, ViewPager viewPager) {
        BoldTransitionPagerTitleView colorTransitionPagerTitleView = new BoldTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(tabUnSelectColor);
        colorTransitionPagerTitleView.setSelectedColor(tabSelectColor);
        colorTransitionPagerTitleView.setTextSize(16);
        colorTransitionPagerTitleView.setText(title);
        colorTransitionPagerTitleView.setOnClickListener(view -> viewPager.setCurrentItem(index));
        return colorTransitionPagerTitleView;
    }


}
