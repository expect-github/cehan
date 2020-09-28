package com.nj.baijiayun.module_course.adapter;

import android.content.Context;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.bean.PublicCourseServerBean;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

/**
 * @author chengang
 * @date 2020-02-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.holder
 * @describe
 */
public class ServiceAdapter extends BaseRecyclerAdapter<PublicCourseServerBean> {
    public ServiceAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.course_item_service;
    }

    @Override
    protected void bindViewAndData(BaseViewHolder holder, PublicCourseServerBean model, int position) {
        holder.setText(R.id.tv_title, model.getName());
        holder.setText(R.id.tv_content, model.getDescription());
    }
}
