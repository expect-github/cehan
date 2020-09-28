package com.nj.baijiayun.module_course.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.widget.BoldTransitionPagerTitleView;
import com.nj.baijiayun.module_course.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

/**
 * @author chengang
 * @date 2019-07-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.helper
 * @describe
 */
public class CourseIndicatorHelper {

    public static void initIndicator(Context context,
                                     MagicIndicator magicIndicator,
                                     ViewPager viewPager,
                                     String[] titles

    ) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BoldTransitionPagerTitleView colorTransitionPagerTitleView = new BoldTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.common_tab_text_unselected));
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.common_main_color));
                colorTransitionPagerTitleView.setTextSize(16);
                colorTransitionPagerTitleView.setText(titles[index]);
                colorTransitionPagerTitleView.setOnClickListener(view -> viewPager.setCurrentItem(index));
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(DensityUtil.dip2px(10));
                indicator.setRoundRadius(DensityUtil.dip2px(2));
                indicator.setColors((ContextCompat.getColor(context, R.color.common_main_color)));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(context, 30);
            }
        });
    }
}
