package com.nj.baijiayun.module_course.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.bean.wx.CalendarCourseBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.text.MessageFormat;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-08-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter
 * @describe
 */
public class LearnCalendarAdapter extends BaseRecyclerAdapter<CalendarCourseBean> {
    public LearnCalendarAdapter(Context context) {
        super(context);

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, viewType);
        baseViewHolder.getView(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpHelper.jumpSectionHomeWork(getAllItems()
                        .get(baseViewHolder.getAdapterPositionExcludeHeadViewCount()).getPeriodsId());

            }
        });
        return baseViewHolder;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.course_item_calendar_course;
    }

    @Override
    protected void bindViewAndData(BaseViewHolder holder, CalendarCourseBean model, int position) {

        //课程
        holder.setText(R.id.tv_section_title, model.getTitle());
        holder.setVisible(R.id.tv_section_title, model.getTitle() != null && model.getTitle().length() > 0);
        holder.setText(R.id.tv_course_title, ConstsCouseType.isOto(model.getCourseType()) ? model.getOtoDesc() : model.getCourseTitle());

        holder.setText(R.id.tv_time_range, model.getLiveTime());
        holder.setText(R.id.tv_live_status, MessageFormat.format("[{0}]", getLiveStatus(model)));
        holder.setTextColor(R.id.tv_live_status, ContextCompat.getColor(getContext(), model.isLiveStatus() ? R.color.common_main_color : R.color.public_FF8C8C8C));

        holder.setText(R.id.tv_course_type, ConstsCouseType.getCourseTypeName(model.getCourseType()));

        CourseHelper.setHomeWork(model.getHomework(), holder.getView(R.id.view_home_work_parent));
    }


    //0:未开始 1：正在直播 2：直播结束
    private String getLiveStatus(CalendarCourseBean model) {
        int liveStatus = model.getLiveStatus();
        String result = "未开始";
        if (liveStatus == 1) {
            result = ConstsCouseType.isFace(model.getCourseType()) ? "进行中" : "正在直播";
        } else if (liveStatus == 2) {

            result = ConstsCouseType.isFace(model.getCourseType()) ? "已结束" : model.isHavePlayBack() ? "回放" : "暂无回放";

        }
        return result;
    }
}
