package com.nj.baijiayun.module_course.adapter.my_course;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nj.baijiayun.annotations.AdapterCreate;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.wx.MyCourseBean;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.TimeRangeAndPeriodsView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.my_course
 * @describe
 */
@AdapterCreate(group = "myLearned")
public class MyLearnedCourseHolder extends BaseMultipleTypeViewHolder<MyCourseBean> {
    public MyLearnedCourseHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.tv_hide_text, v -> showRecoverDialog(getClickModel().getCourseId()));
        setOnClickListener(R.id.iv_hide, v -> showRecoverDialog(getClickModel().getCourseId()));
    }

    @Override
    public int bindLayout() {
        return R.layout.course_item_my_course;
    }

    @Override
    public void bindData(MyCourseBean model, int position, BaseRecyclerAdapter adapter) {

        setText(R.id.tv_course_name, model.getTitle());
        ((CourseTitleView) getView(R.id.tv_course_name)).showCoupon(false);
        ((TimeRangeAndPeriodsView) getView(com.nj.baijiayun.module_public.R.id.tv_date))
                .setPeriods(model.getSectionNum())
                .setIv((ImageView) getView(com.nj.baijiayun.module_public.R.id.iv_clock))
                .setStartPlay(model.getStartPlayDate())
                .setEndPlay(model.getEndPlayDate())
                .setShowTimeRanger(true)

                .show();
        //没有时间的话，显示
        setVisible(R.id.ll_time_range, ((TimeRangeAndPeriodsView) getView(R.id.tv_date)).getText().toString().length() > 0);

        //进度
        setVisible(R.id.rel_learn_pgr_parent, model.isShowProgress());
        setProgress(R.id.pg_learned, model.getProgressRate());
        setText(R.id.tv_learned, MessageFormat.format(getContext().getString(R.string.course_format_learned), model.getProgressRate()));

        //vip课程
        setVisible(R.id.tv_course_vip, model.isVipCourse());

        //失效
        setVisible(R.id.iv_invalid, model.isInvalid());
        setTextColor(R.id.tv_course_name, ContextCompat.getColor(getContext(), model.isInvalid() ? R.color.public_FF8C8C8C : R.color.public_D9000000));
        //进度颜色
        ((ProgressBar) getView(R.id.pg_learned))
                .setProgressDrawable(getContext()
                        .getResources()
                        .getDrawable(model.isInvalid() ?
                                R.drawable.course_bg_progress_un_enable : R.drawable.course_bg_progress));
        //状态文字颜色
        setTextColor(R.id.tv_state, ContextCompat.getColor(getContext(), model.isInvalid() ? R.color.public_FF595959 : R.color.common_main_color));
        //失效的话就是false unenable状态
        getView(R.id.tv_course_vip).setEnabled(!model.isInvalid());


        //直播
        if (model.getNowCourseNum() > 0) {
            setText(R.id.tv_state, "今日有直播/正在直播");
        } else if (model.getTodayCourseNum() > 0) {
            setText(R.id.tv_state, "今日有课/正在上课");
        } else {
            //默认
            setText(R.id.tv_state, "");
        }
        setVisible(R.id.gp_hide, model.isHide());

    }

    private void showRecoverDialog(int courseId) {
        CommonMDDialog mdDialog = BJYDialogHelper.buildMDDialog(getContext());
        mdDialog
                .hideTitle()
                .setContentTxt(getContext().getString(R.string.course_cancel_hide_course))
                .setNegativeTxt(R.string.cancel)
                .setPositiveTxt(R.string.confirm).setOnPositiveClickListener(() -> {
            submitRecover(courseId);
            mdDialog.dismiss();
        }).show();
    }

    private void submitRecover(int courseId) {

        ((BaseView) getContext()).showLoadV();
        NetMgr.getInstance().getDefaultRetrofit().create(CourseService.class)
                .hideOrRecoverCourse(courseId)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as((LifecycleOwner) getContext()))
                .subscribe(new BaseObserver<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        ((BaseView) getContext()).closeLoadV();
                        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HIDE_RECOVER).postValue(courseId);

                    }

                    @Override
                    public void onFail(Exception e) {
                        ((BaseView) getContext()).closeLoadV();
                        ToastUtil.show(e.getMessage());
                    }
                });

    }
}
