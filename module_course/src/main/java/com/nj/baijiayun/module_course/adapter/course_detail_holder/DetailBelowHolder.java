package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author Kai
 * @date 2020/8/11
 * @time 11:40
 */
public class DetailBelowHolder extends BaseMultipleTypeViewHolder<PublicCourseDetailBean> {
    public DetailBelowHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void bindData(PublicCourseDetailBean model, int position, BaseRecyclerAdapter adapter) {

    }
}
