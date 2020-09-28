package com.nj.baijiayun.module_public.helper;

import android.view.View;

/**
 * @author chengang
 * @date 2020-02-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class ViewHelper {

    public static void setViewVisible(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public static void setViewVisibleInVisivle(View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
