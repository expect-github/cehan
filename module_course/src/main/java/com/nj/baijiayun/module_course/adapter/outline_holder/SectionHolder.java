package com.nj.baijiayun.module_course.adapter.outline_holder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.BaseCourseStartPlayType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.EmptyType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.FaceType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.LiveType;
import com.nj.baijiayun.module_course.adapter.outline_holder.start_type.StartTypeWrapper;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.response.DatumRealFileResponse;
import com.nj.baijiayun.module_course.bean.wx.DatumBean;
import com.nj.baijiayun.module_course.bean.wx.DatumRealFileBean;
import com.nj.baijiayun.module_course.bean.wx.SectionBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.WxCourseDetailActivity;
import com.nj.baijiayun.module_course.ui.wx.mylearnddetail.MyLearnedCourseDetailActivity;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.holder.DateUtil;
import com.nj.baijiayun.processor.Module_courseOutlineAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;
import com.nj.baijiayun.refresh.recycleview.helper.ContextGetHelper;

import java.text.MessageFormat;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.outline_holder
 * @describe
 */
@AdapterCreate(group = "outLine")
public class SectionHolder extends BaseMultipleTypeViewHolder<SectionBean> {


    /**
     * 用来取出activity
     */
    public static final int TAG_ACTIVITY = 1001;

    public SectionHolder(ViewGroup parent) {
        super(parent);
        RecyclerView recyclerView = (RecyclerView) getView(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        BaseRecyclerAdapter datumAdapter = getDatumAdapter();
        recyclerView.setAdapter(datumAdapter);
        recyclerView.addItemDecoration(SpaceItemDecoration.create().setSpace(10).setIncludeEdge(false));
        datumAdapter.setOnItemClickListener((holder, position, view, item) -> {
            Activity activityFromView = ContextGetHelper.getActivityFromView(view);
            if (activityFromView instanceof WxCourseDetailActivity) {
                if (((WxCourseDetailActivity) activityFromView).checkNotAllowClickSection(getClickModel())) {
                    return;
                }
            } else if (activityFromView instanceof MyLearnedCourseDetailActivity) {
                if (((MyLearnedCourseDetailActivity) activityFromView).tryCheckClickItemReturnHint(true)) {
                    return;
                }
            }
            WxCourseDetailActivity.adaType = false;
            downloadFileCompleteOpen((DatumBean) item, position);
        });
        setOnClickListener(R.id.iv_download_status, v -> {
            itemInnerViewClickCallBack(v);
        });

    }

    private void downloadFileCompleteOpen(DatumBean item, int position) {
        ToastUtil.shortShow(getContext(), "正在打开...");
        NetMgr.getInstance().getDefaultRetrofit().create(CourseService.class).getDatumFile(item.getDatumId(), position + 1, CourseHelper.getSystemCourseId(getContext()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain((LifecycleOwner) getContext())).subscribe(new BaseSimpleObserver<DatumRealFileResponse>() {
            @Override
            public void onPreRequest() {
            }

            @Override
            public void onSuccess(DatumRealFileResponse datumRealFileResponse) {
                DatumRealFileBean data = datumRealFileResponse.getData();
                if (data.needShowUnOpenToast()) {
                    ToastUtil.shortShow(getContext(), data.getShowMag());
                    return;
                }
                JumpHelper.jumpPreViewFile(data.getFileUrl(), data.getFileName());
            }

            @Override
            public void onFail(Exception e) {
                ToastUtil.shortShow(getContext(), e.getMessage());
            }
        });
    }

    public BaseRecyclerAdapter getDatumAdapter() {
        return new Module_courseOutlineAdapter(getContext());
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_outline_section;
    }

    //面授课 已结束、开课中（蓝色）、未开始
    //回放 黄色
    //直播中 已结束

    @Override
    public void bindData(SectionBean model, int position, BaseRecyclerAdapter adapter) {
//        setVisible(R.id.tv_try_look, model.isCanTrySee());
//        if (position==0){
//            WxCourseDetailActivity.adaType = false;
//        }

        if (WxCourseDetailActivity.adaType) {
            WxCourseDetailActivity.adaNun = WxCourseDetailActivity.adaNun+1;
            setText(R.id.tv_try_look, WxCourseDetailActivity.adaNun+"");
            Log.e("走了测试上",position+"");
        }else {
            setText(R.id.tv_try_look, position+1+ "");
            Log.e("走了测试下",position+1+"");
        }
        setText(R.id.tv_course_name, model.getPeriodsTitle());
        setVisible(R.id.rv, model.getDatum() != null && model.getDatum().size() > 0);
        ((BaseRecyclerAdapter) ((RecyclerView) getView(R.id.rv)).getAdapter()).addAll(model.getDatum(), true);
        setStartPlay(model);
        setVisible(R.id.tv_teacher_and_time, model.hasTeacher());
//        String satTime = DateUtil.getDateToString(Long.valueOf(model.getStartPlay()));
//        String endTime = DateUtil.getDateToString(Long.valueOf(model.getEndPlay()));
        setText(R.id.tv_teacher_and_time,model.getStartPlay()+" ~ "+model.getEndPlay());
//        setText(R.id.tv_teacher_and_time, MessageFormat.format("{0}  {1} - {2}", "", model.getStartPlay(), model.getEndPlay()));
        //判断是不是根就是section
        checkOnlyHasSection();
    }

    public void checkOnlyHasSection() {
        if (isOnlyHasSection()) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getConvertView().getLayoutParams();
            layoutParams.leftMargin = DensityUtil.dip2px(0);
            getConvertView().setLayoutParams(layoutParams);

        }
    }

    protected boolean isOnlyHasSection() {
        return getAdapter().getTag() != null && (Boolean) getAdapter().getTag();
    }

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
//        setVisible(R.id.tv_start_type, !showStrByType.isEmpty());
        TextView tv_start_type_new = (TextView) getView(R.id.tv_start_type_new);
        if (tv_start_type_new!=null){
            setVisible(R.id.tv_start_type_new, !showStrByType.isEmpty());
            setTypeText(R.id.tv_start_type_new, model.getPlayType());
        }
//        setText(R.id.tv_start_type, showStrByType.getShowTxt());
        setTextColor(R.id.tv_start_type, ContextCompat.getColor(getContext(), showStrByType.getShowColor()));
    }

    private void setTypeText(int viewId, int type) {
        TextView tv_name = (TextView) getView(viewId);
        switch (type) {
            case 1:
                tv_name.setText("未开始");
                tv_name.setTextColor(ContextCompat.getColor(getContext(), R.color.course_detail));
                tv_name.setBackgroundResource(R.drawable.cuser_btn_3_bg);
                break;
            case 2:
                tv_name.setText("讲课中");
                tv_name.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tv_name.setBackgroundResource(R.drawable.cuser_btn_2_bg);
                break;
            case 3:
                tv_name.setText("无回放");
                break;
            case 4:
                tv_name.setText("看回放");
                tv_name.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tv_name.setBackgroundResource(R.drawable.cuser_btn_1_bg);
                break;
            default:
                tv_name.setText("未开始");
                tv_name.setTextColor(ContextCompat.getColor(getContext(), R.color.course_detail));
                tv_name.setBackgroundResource(R.drawable.cuser_btn_3_bg);
                break;

        }
    }


}
