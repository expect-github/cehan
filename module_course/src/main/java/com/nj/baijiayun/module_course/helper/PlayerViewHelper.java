package com.nj.baijiayun.module_course.helper;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengang
 * @date 2019-08-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.helper
 * @describe
 */
public class PlayerViewHelper {
    public static List<View> getNeedHideView(ViewGroup layoutRootView, View noHideChildView) {
        List<View> result = new ArrayList<>();
        if (layoutRootView.getParent() != null) {
            for (int i = 0; i < ((ViewGroup) layoutRootView.getParent()).getChildCount(); i++) {
                if (((ViewGroup) layoutRootView.getParent()).getChildAt(i) != layoutRootView) {
                    result.add(((ViewGroup) layoutRootView.getParent()).getChildAt(i));
                }
            }
        }
        for (int i = 0; i < layoutRootView.getChildCount(); i++) {
            if (layoutRootView.getChildAt(i) != noHideChildView) {
                result.add(layoutRootView.getChildAt(i));
            }

        }
        return result;

    }
}
