package com.nj.baijiayun.module_main.adapter;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author chengang
 * @date 2020/4/11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter
 * @describe
 */
public class TransFormer implements ViewPager.PageTransformer {
    public static float MIN_SCALE = 0.95f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) { // [-1,1]
            if (position < 0) {
                float scaleX = 1 + (1-MIN_SCALE) * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else {
                float scaleX = 1 - (1-MIN_SCALE) * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
        }
    }
}
