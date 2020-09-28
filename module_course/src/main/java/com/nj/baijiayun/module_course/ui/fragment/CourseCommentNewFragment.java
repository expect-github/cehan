package com.nj.baijiayun.module_course.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.temple.BaseAppWebViewFragment;

import java.util.Map;

import androidx.annotation.Nullable;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.fragment
 * @describe
 */
public class CourseCommentNewFragment extends BaseAppWebViewFragment {


    public String mCourseId;

    @Override
    public void loadUrl() {
        if (mCourseId == null) {
            return;
        }
        super.loadUrl();
    }


    @Override
    public String configUrl() {
        return ConstsH5Url.getComment();
    }

    @Override
    public Map<String, String> configParams(Map<String, String> map) {
        map.put("id", mCourseId);
        return super.configParams(map);
    }

    @Override
    public void registerListener() {
        super.registerListener();
        LiveDataBus.get().with(LiveDataBusEvent.COURSE_COMMENT_SUCCESS, Integer.class).observe(this, courseId -> {
            if (courseId != null && String.valueOf(courseId).equals(mCourseId)) {
                loadUrl();
            }
        });

    }

    public static CourseCommentNewFragment getIntet(Integer id) {
        Log.e("评价id:",id+"");
        CourseCommentNewFragment courseDetailFragment = new CourseCommentNewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        courseDetailFragment.setArguments(bundle);
        return courseDetailFragment;
    }

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        Bundle bundle = getArguments();
        int numm = bundle.getInt("id");
        this.mCourseId = String.valueOf(numm);
//        loadUrl();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getConvertView().setBackground(null);
    }
}
