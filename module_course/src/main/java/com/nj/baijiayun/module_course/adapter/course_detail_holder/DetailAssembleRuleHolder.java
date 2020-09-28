package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

/**
 * @author chengang
 * @date 2019-11-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailAssembleRuleHolder extends BaseMultipleTypeViewHolder {

    public DetailAssembleRuleHolder(ViewGroup parent) {
        super(parent);
        getView(R.id.iv_assemble_play).setOnClickListener(v -> {
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getAssembleRule());
        });

    }

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_assemble_play;
    }

    @Override
    public void bindData(Object model, int position, BaseRecyclerAdapter adapter) {

    }

    public void bindData() {

    }
}
