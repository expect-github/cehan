package com.nj.baijiayun.module_course.ui.fragment;

import android.os.Bundle;
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
public class CourseCommentFragment extends BaseAppWebViewFragment {


    private String mCourseId;

    public void loadUrlByCourseId(int courseId) {
        this.mCourseId = String.valueOf(courseId);
        loadUrl();
    }

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

    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getConvertView().setBackground(null);
    }
}
