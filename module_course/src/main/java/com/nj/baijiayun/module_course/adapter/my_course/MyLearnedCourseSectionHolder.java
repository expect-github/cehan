package com.nj.baijiayun.module_course.adapter.my_course;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.widget.CircleMultipleProgressView;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.outline_holder.SectionHolder;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.BaseCourseStartPlayType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.EmptyType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.FaceType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.LiveType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.StartTypeWrapper;
import com.nj.baijiayun.module_course.bean.wx.SectionBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.processor.Module_courseAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.my_course
 * @describe
 */
@AdapterCreate(group = "learnedDetail")
public class MyLearnedCourseSectionHolder extends SectionHolder {


    public static final int PAY_LOAD_UPDATE_BG = 1;
    public static final int PAY_LOAD_UPDATE_DOWNLOAD = 2;


    public MyLearnedCourseSectionHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.view_download_placeholder, v -> {
            if (getClickModel().isDownloadInProgress()) {
                ToastUtil.shortShow(getContext(), "已加入下载列表");
                return;
            }
            if (getClickModel().isDownloadComplete()) {
                return;
            }
            itemInnerViewClickCallBack(v);
            ToastUtil.shortShow(getContext(), "加入下载");

        });

        getView(R.id.tv_submit).setOnClickListener(v -> JumpHelper.jumpSectionHomeWork(getClickModel().getPeriodId()));
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_learned_detail_section;
    }

    @Override
    public void bindData(SectionBean model, int position, BaseRecyclerAdapter adapter) {
        super.bindData(model, position, adapter);
        setVisible(R.id.pg_learned, CourseHelper.isShowProgress(model.getCourseType()));
        setVisible(R.id.tv_learned, CourseHelper.isShowProgress(model.getCourseType()));
        setProgress(R.id.pg_learned, model.getProgressRate());
        setText(R.id.tv_learned, MessageFormat.format(getContext().getString(R.string.course_format_learned), model.getProgressRate()));
        if (!isOnlyHasSection()) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getConvertView().getLayoutParams();
            layoutParams.topMargin = DensityUtil.dip2px(14);
            if (isLast(position)) {
                layoutParams.bottomMargin = DensityUtil.dip2px(20);
            } else {
                layoutParams.bottomMargin = 0;
            }
            getConvertView().setLayoutParams(layoutParams);
        }
        setBackgroundRes(R.id.ll_root, model.isChecked() ? R.drawable.course_bg_learned_section_selected : R.drawable.course_bg_learned_section_un_selected);
        setStartPlay(model);
        CourseHelper.setHomeWork(model.getHomework(), getView(R.id.view_home_work_parent));
        updateDownloadState(model);
    }

    @Override
    public void bindData(SectionBean model, int position, BaseRecyclerAdapter adapter, List<Object> payloads) {
        if ((int) payloads.get(0) == PAY_LOAD_UPDATE_BG) {
            setBackgroundRes(R.id.ll_root, model.isChecked() ? R.drawable.course_bg_learned_section_selected : R.drawable.course_bg_learned_section_un_selected);
        } else {
            updateDownloadState(model);
        }

    }

    /**
     * 新加的
     * @param model
     */
    private void setStartPlay(SectionBean model) {
        BaseCourseStartPlayType baseCourseStartPlayType;
        if (ConstsCouseType.isLive(model.getCourseType())) {
            baseCourseStartPlayType = new LiveType();
        } else if (ConstsCouseType.isFace(model.getCourseType())) {
            baseCourseStartPlayType = new FaceType();
        } else {
            baseCourseStartPlayType = new EmptyType();
        }
        StartTypeWrapper showStrByType = baseCourseStartPlayType.getShowStrByType(model.getPlayType());
        setVisible(R.id.tv_start_type, !showStrByType.isEmpty());
        setText(R.id.tv_start_type, showStrByType.getShowTxt());
    }


    /**
     * 更新的时候调用
     */
    public void updateDownloadState(SectionBean model) {
        //下载的父亲
        setVisible(R.id.rel_download_parent, model.isCanDownLoad());
        //下载占位点击的
        setVisible(R.id.view_download_placeholder, model.isCanDownLoad());
        //下载的状态view
        setVisible(R.id.iv_download_status, !model.isDownloadInProgress());
        //下载的进度
        setVisible(R.id.prg_download, model.isDownloadInProgress());
        setImageResource(R.id.iv_download_status, model.isDownloadComplete() ? R.drawable.course_ic_learned_download_complete : R.drawable.course_ic_learned_download);
        if (model.isDownloadInProgress()) {
            //100 为外圈的边 始终为100
            ((CircleMultipleProgressView) getView(R.id.prg_download)).setProgressArray(100, model.getDownloadProgress());
        }
    }


    private boolean isLast(int position) {
        int nextIndex = position + 1;
        if (nextIndex >= getAdapter().getItemCount()) {
            return true;
        }
        Object item = getAdapter().getItem(nextIndex);
        return !(item instanceof SectionBean);
    }

    @Override
    public void checkOnlyHasSection() {
        //do nothing

    }

    @Override
    public BaseRecyclerAdapter getDatumAdapter() {
        return Module_courseAdapterHelper.getLearneddetailAdapter(getContext());
    }
}
