package com.nj.baijiayun.module_main.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_course.ui.wx.learnCalendar.LearnCalendarFragment;
import com.nj.baijiayun.module_main.R;

import androidx.annotation.Nullable;

/**
 * @author chengang
 * @date 2020-02-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class HomeCalendarFragment extends LearnCalendarFragment {
    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        ToolBarHelper.setToolBarTextCenter(mContextView.findViewById(R.id.toolbar), mContextView.getContext().getString(R.string.main_learn_calendar));
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getConvertView() != null) {
            getConvertView().setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.main_fragment_with_toolbar;
    }
}
