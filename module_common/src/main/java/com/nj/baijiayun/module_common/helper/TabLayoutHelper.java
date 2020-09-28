package com.nj.baijiayun.module_common.helper;

import com.google.android.material.tabs.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * @author chengang
 * @date 2019/4/28
 * @describe
 */
public class TabLayoutHelper {

    //    设置指示器
    public static void setIndicatorWithEqualsText(final TabLayout tabLayout, final int width) {
        tabLayout.setVisibility(View.GONE);
        tabLayout.post(() -> {
            try {
                Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                mTabStripField.setAccessible(true);
                LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
                int stepWidth = width / mTabStrip.getChildCount();
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);

                    Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);

                    TextView mTextView = (TextView) mTextViewField.get(tabView);

                    tabView.setPadding(0, 0, 0, 0);

                    int width1 = 0;
                    width1 = mTextView.getWidth();
                    if (width1 == 0) {
                        mTextView.measure(0, 0);
                        width1 = mTextView.getMeasuredWidth();
                    }

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width1;
                    params.leftMargin = (stepWidth - width1) / 2;
                    params.rightMargin = params.leftMargin;

                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }

                tabLayout.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                tabLayout.setVisibility(View.VISIBLE);

            }
        });
    }

}
