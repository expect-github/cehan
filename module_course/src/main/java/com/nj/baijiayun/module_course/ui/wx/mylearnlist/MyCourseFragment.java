package com.nj.baijiayun.module_course.ui.wx.mylearnlist;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.bean.wx.MyCourseBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.TextSpanHelper;
import com.nj.baijiayun.module_public.temple.BaseRecycleFragment;
import com.nj.baijiayun.processor.Module_courseAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.text.MessageFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.mycourse
 * @describe
 */
public class MyCourseFragment extends BaseRecycleFragment<MyLearnedCourseContract.Presenter> implements MyLearnedCourseContract.View {


    private int courseType;
    private TextView mVipTv;
    private boolean isHide;

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.course_fragment_my_learned_list;
    }

    @Override
    public BaseRecyclerAdapter createAdapter() {

        return Module_courseAdapterHelper.getMylearnedAdapter(getActivity());
    }

    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        if (params == null) {
            return;
        }
        courseType = params.getInt("type", 0);
        isHide = params.getBoolean("isHide", false);
    }


    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        mVipTv = mContextView.findViewById(R.id.tv_vip);
        mVipTv.setVisibility(View.GONE);

        getRv().addItemDecoration(SpaceItemDecoration.create().setSpace(10));

    }


    @Override
    public void registerListener() {
        super.registerListener();
        multipleStatusView.setOnRetryClickListener(v -> mPresenter.getCourseType(courseType,isHide));

        getAdapter().setOnItemClickListener((holder, position, view, item) -> {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            if (((MyCourseBean) item).isInvalid()) {
                if (((MyCourseBean) item).isUserNotBeVipInvalid()) {
                    showNotBeVipUserDialog(((MyCourseBean) item).getCourseId());
                } else if (((MyCourseBean) item).isCourseNotVipInvalid()) {
                    showCourseNotBeVipDialog(((MyCourseBean) item).getCourseId(), ((MyCourseBean) item).getCourseType());
                }
                return;
            }
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_MY_LEARNED_DETAIL)
                    .withInt("courseType", ((MyCourseBean) item).getCourseType())
                    .withInt("courseId", ((MyCourseBean) item).getCourseId())
                    .navigation();
        });
    }

    private void showCourseNotBeVipDialog(int courseId, int courseType) {
        CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog(getActivity());
        commonMDDialog
                .setContentTxt("该课程不是会员课程，您需要购买后观看。详情请咨询客服。")
                .setNegativeTxt("移除课程")
                .setPositiveTxt("购买课程")
                .setOnNegativeClickListener(() -> {
                    commonMDDialog.dismiss();
                    mPresenter.removeCourse(courseId);
                }).setOnPositiveClickListener(() -> {
            commonMDDialog.dismiss();
            JumpHelper.jumpBuyCourse(courseId, courseType);

        }).show();
    }


    private void showNotBeVipUserDialog(int courseId) {
        CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog(getActivity());
        commonMDDialog
                .setContentTxt("您的会员已失效")
                .setNegativeTxt("移除课程")
                .setPositiveTxt("续费会员")
                .setOnNegativeClickListener(() -> {
                    commonMDDialog.dismiss();
                    mPresenter.removeCourse(courseId);

                }).setOnPositiveClickListener(() -> {
            commonMDDialog.dismiss();
            JumpHelper.jumpVip();
        }).show();

    }

    @Override
    public void processLogic() {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getCourseType(courseType,isHide);

    }

    private SpannableString getVipText() {
        long vipEndAt = AccountHelper.getInstance().getInfo().getVipEndAt();
        String tip = MessageFormat.format("课程中有会员课,会员课将于{0}过期。", TimeFormatHelper.getYearMonthAndDayFormatChina(vipEndAt));
        if (remainingTime(vipEndAt) < 3) {
            tip += "去续费";
        }
        return TextSpanHelper.matcherSearchKeyWord(tip, "去续费"
                ,
                new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getVip());
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(ContextCompat.getColor(getContext(), R.color.course_date_color));
                        ds.setUnderlineText(true);


                    }
                }
        );

    }

    private boolean isShowVipHeader(List<MyCourseBean> data) {
        return AccountHelper.getInstance().getInfo().isVip() && (isVipTypeCourse(data) || isContainsVipCourse(data));
    }

    //  vip分类
    private boolean isVipTypeCourse(List<MyCourseBean> data) {
        return ConstsCouseType.isVipCourse(courseType) && data != null && data.size() > 0;
    }

    private boolean isContainsVipCourse(List<MyCourseBean> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isVipCourse()) {
                return true;
            }
        }
        return false;
    }


    private int remainingTime(long vipEndat) {
        return (int) Math.ceil((vipEndat - System.currentTimeMillis() / 1000) * 1.0 / (60 * 60 * 24));
    }


    @Override
    public void setTabs(List<MyCourseBean> data) {
        if (mVipTv == null || data == null) {
            return;
        }
        mVipTv.setVisibility(isShowVipHeader(data) ? View.VISIBLE : View.GONE);
        mVipTv.setMovementMethod(LinkMovementMethod.getInstance());
        mVipTv.setText(getVipText());
        getAdapter().addAll(data, true);
    }

    @Override
    public void removeCourseSuccess(int courseId) {
        if (getAdapter() == null) {
            return;
        }
        for (int i = 0; i < getAdapter().getItemCount(); i++) {
            MyCourseBean myCourseBean = (MyCourseBean) getAdapter().getItem(i);
            if (myCourseBean.getCourseId() == courseId) {
                getAdapter().removeItem(i);
                break;
            }
        }
    }
}
