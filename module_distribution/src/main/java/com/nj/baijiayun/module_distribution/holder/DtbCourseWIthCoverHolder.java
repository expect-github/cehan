package com.nj.baijiayun.module_distribution.holder;

import android.view.ViewGroup;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.module_distribution.R;
import com.nj.baijiayun.module_distribution.bean.DtbCourseBean;
import com.nj.baijiayun.module_public.bean.IDistrution;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.textviewtag.TextViewTagHelper;
import com.nj.baijiayun.module_public.holder.PublicCourseHolder;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.holder
 * @describe
 */
@AdapterCreate
public class DtbCourseWIthCoverHolder extends PublicCourseHolder {
    public DtbCourseWIthCoverHolder(ViewGroup parent) {
        super(parent);

    }


    @Override
    public int bindLayout() {
        return R.layout.distribution_item_course_cover;
    }

    @Override
    public void bindData(PublicCourseBean model, int position, BaseRecyclerAdapter adapter) {
        super.bindData(model, position, adapter);
        if(model instanceof IDistrution) {
            HolderHelper.setProfit(((PriceTextView) getView(R.id.tv_other)), (IDistrution) model);
        }
        //原本这块位置显示的是课时跟时间  这块 覆盖之前的逻辑
        setVisible(R.id.public_tv_sign_up_num,true);
        if(model instanceof DtbCourseBean) {
            setText(R.id.public_tv_sign_up_num, MessageFormat.format("共{0}课时 | {1}人已报名", ((DtbCourseBean) model).getPeriodsNum(), model.getSalesNum()));
        }

    }

    @Override
    protected void setCourseTitle(PublicCourseBean model) {
        TextViewTagHelper
                .getInstance()
                .bind()
                .setCourseType(ConstsCouseType.getCourseTypeName(model.getCourseType()))
                .setVip(model.isVipCourse())
                .setCoupon(model.isHasCoupon())
                .setSpell(model.isJoinSpell())
                .show(((CourseTitleView) getView(com.nj.baijiayun.module_public.R.id.tv_course_name)),model.getTitle());
    }
}
