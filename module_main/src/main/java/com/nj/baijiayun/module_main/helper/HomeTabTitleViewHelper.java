package com.nj.baijiayun.module_main.helper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nj.baijiayun.basic.widget.MultipleStatusView;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_main.R;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author chengang
 * @date 2020-02-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.helper
 * @describe
 */
public class HomeTabTitleViewHelper {

    public static BaseAppFragment create(Class cls) {
        BaseAppFragment fragment = (BaseAppFragment) FragmentCreateHelper.newInstance(null, cls);
        fragment.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public void onAny() {
                Logger.d("HomeTabTitleViewHelper  " + fragment.getLifecycle().getCurrentState());
                tryAddTitleView(fragment.getConvertView(), "9999");

            }
        });
        return fragment;
    }

    public static void tryAddTitleView(View view, String title) {
        if (view == null) {
            return;
        }
        if (view instanceof MultipleStatusView) {
            if (((MultipleStatusView) view).getChildAt(1) instanceof Toolbar) {
                return;
            }
            androidx.appcompat.widget.Toolbar toolbar = new Toolbar(view.getContext());
            View inflate = LayoutInflater.from(view.getContext()).inflate(R.layout.main_layout_status_bar, (ViewGroup) view, false);
            ((MultipleStatusView) view).addView(inflate,0);
            toolbar.setBackgroundColor(Color.WHITE);
            ToolBarHelper.setToolBarTextCenter(toolbar, title);
            ((MultipleStatusView) view).addView(toolbar, 1);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
            toolbar.setLayoutParams(layoutParams);

        }
    }

}
