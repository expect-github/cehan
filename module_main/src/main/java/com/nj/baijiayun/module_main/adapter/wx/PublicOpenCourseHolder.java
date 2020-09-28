package com.nj.baijiayun.module_main.adapter.wx;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.imageloader.transform.RoundedCornersTransformation;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.bean.PublicOpenCourseBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.holder.PublicCourseHolder;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx
 * @describe
 */
@AdapterCreate(group = "open_course")
public class PublicOpenCourseHolder extends BaseMultipleTypeViewHolder<PublicOpenCourseBean> {
    public PublicOpenCourseHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v ->
                PublicCourseHolder.playPublicOpenCourse(getContext(), getClickModel().getPlayId(), ConstsCouseType.getCourseTypePublic()));
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.main_item_public_open_course_v3;
    }

    @Override
    public void bindData(PublicOpenCourseBean model, int position, BaseRecyclerAdapter adapter) {
        ImageLoader.with(getContext()).load(model.getImg()).rectRoundCorner(5).setCornerType(RoundedCornersTransformation.CornerType.RIGHT).into((ImageView) getView(R.id.iv_cover));
        setText(R.id.tv_title, model.getTitle());
        setText(R.id.tv_number, MessageFormat.format("{0}人已看", String.valueOf(model.getNumber())));
        setText(R.id.tv_teacher, MessageFormat.format("{0}", model.getTeacher()));
        String fmt = "MM.dd HH:mm";
        if (model.isLiveUnStart()) {
            setText(R.id.tv_time, TimeFormatHelper.getDateByFormat(model.getStartPlayDate(), fmt) + "开始直播");
            setText(R.id.tv_live_status, "未开始");
            setBackgroundRes(R.id.view_live_bg, R.drawable.main_bg_live_status_un_start);
            setImageResource(R.id.iv_live_icon, R.drawable.main_ic_live_un_start);

        } else if (model.isLiveEnd()) {
            setText(R.id.tv_time, TimeFormatHelper.getDateByFormat(model.getEndPlayDate(), fmt) + "结束直播");
            setText(R.id.tv_live_status, "已结束");
            setBackgroundRes(R.id.view_live_bg, R.drawable.main_bg_live_status_end);
            setImageResource(R.id.iv_live_icon, R.drawable.main_ic_live_end);

        } else if (model.isLiveInProgress()) {
            setText(R.id.tv_time, TimeFormatHelper.getDateByFormat(model.getStartPlayDate(), fmt) + "开始直播");
            setText(R.id.tv_live_status, "直播中");
            setBackgroundRes(R.id.view_live_bg, R.drawable.main_bg_live_status_loading);
            setImageResource(R.id.iv_live_icon, R.drawable.main_ic_live_inprogress);

        }


    }
}
