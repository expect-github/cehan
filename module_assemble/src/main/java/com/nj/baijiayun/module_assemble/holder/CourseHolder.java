package com.nj.baijiayun.module_assemble.holder;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_assemble.bean.AssembleBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.holder.CourseHolderHelper;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.IconTextSpan;
import com.nj.baijiayun.module_public.widget.TimeRangeAndPeriodsView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.holder
 * @describe
 */
@Deprecated
public class CourseHolder extends BaseMultipleTypeViewHolder<AssembleBean> {
    private final IconTextSpan iconTextSpan;
    private final IconTextSpan iconTextSpan1;
    private int[] tvArrAy = new int[]{R.id.tv_1, R.id.tv_2, R.id.tv_3};
    private int[] ivArrAy = new int[]{R.id.iv_1, R.id.iv_2, R.id.iv_3};


    public CourseHolder(ViewGroup parent) {
        super(parent);
        iconTextSpan = new IconTextSpan(getContext(), "");
        iconTextSpan1 = new IconTextSpan(getContext(), "");
        getConvertView().setOnClickListener(v -> JumpHelper.jumpCourseDetail(getClickModel().getCourseBasisId()));
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.assemble_item_course;
    }

    @Override
    public void bindData(AssembleBean model, int position, BaseRecyclerAdapter adapter) {
        ((TimeRangeAndPeriodsView) getView(com.nj.baijiayun.module_public.R.id.tv_date))
                .setIv((ImageView) getView(com.nj.baijiayun.module_public.R.id.iv_clock))
                .setPeriods(model.getPeriodNumber())
                .setStartPlay(model.getStartPlayDate())
                .setEndPlay(model.getEndPlayDate())
                .show();

        setText(R.id.tv_course_name, model.getCourseName());
        iconTextSpan.setText(MessageFormat.format(getContext().getString(R.string.assemble_fmt_assemble_join_number_tag), model.getUserNum()))
                .setBgColorResId(R.color.assemble_tag_join_bg_color)
                .setTextColorResId(R.color.assemble_tag_join_tv_color).setAttrToPaint();
        iconTextSpan1.setText(ConstsCouseType.getCourseTypeName(model.getCourseType()))
                .setBgColorResId(R.color.assemble_tag_course_type_color)
                .setTextColorResId(R.color.assemble_tag_course_type_tv_color).setAttrToPaint();

        ((CourseTitleView) getView(R.id.tv_course_name))
                .addTag(model.getCourseName(), iconTextSpan, iconTextSpan1);


        AssembleJoinNumberHelper.setCommonInfo(this, model);
        CourseHolderHelper.setHeadInfo(this, model.getTeacherList(), tvArrAy, ivArrAy);
    }


}
