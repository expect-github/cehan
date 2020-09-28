package com.nj.baijiayun.module_public.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.widget.dialog.IosLoadingDialog;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.helper.textviewtag.TextViewTagHelper;
import com.nj.baijiayun.module_public.helper.videoplay.VideoPlayHelper;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;
import com.nj.baijiayun.module_public.widget.CourseTitleView;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.module_public.widget.TimeRangeAndPeriodsView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-07-18
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.adapter.wx_layout
 * @describe
 */
@Deprecated
public class PublicCourseNoCoverHolder extends BaseMultipleTypeViewHolder<PublicCourseBean> {


    public PublicCourseNoCoverHolder(ViewGroup parent) {
        super(parent);
        getConvertView().setOnClickListener(v -> {

            if (ConstsCouseType.isPublic(getClickModel().getCourseType())) {
                playPublicOpenCourse(getContext(), getClickModel().getPlayId(), getClickModel().getCourseType());
                return;
            }
            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("courseId", getClickModel().getId()).navigation();

        });


    }

    public static void playPublicOpenCourse(Context context, int courseId, int courseType) {

        final IosLoadingDialog iosLoadingDialog = BJYDialogHelper.buildLoadingDialog(context).setLoadingTxt("");
        iosLoadingDialog.show();
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getBjyVideoTokenPublicCourse("", String.valueOf(courseId))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .as(RxLife.asOnMain((LifecycleOwner) context))
                .subscribe(new BaseSimpleObserver<BjyTokensRes>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BjyTokensRes baseResponse) {
                        iosLoadingDialog.dismiss();
                        VideoPlayHelper.playVideo(baseResponse.getData(), courseType);

                    }

                    @Override
                    public void onFail(Exception e) {
                        iosLoadingDialog.dismiss();
                        ToastUtil.shortShow(context, e.getMessage());

                    }
                });
    }

    @Override
    public boolean isNeedClickRootItemViewInHolder() {
        return true;
    }

    @Override
    public int bindLayout() {
        return R.layout.public_item_course_v3;
    }

    private int[] tvArrAy = new int[]{R.id.tv_1, R.id.tv_2, R.id.tv_3};
    private int[] ivArrAy = new int[]{R.id.iv_1, R.id.iv_2, R.id.iv_3};

    @Override
    public void bindData(PublicCourseBean model, int position, BaseRecyclerAdapter adapter) {

//        setText(R.id.tv_course_name, model.getTitle());

        ((PriceTextView) getView(R.id.tv_price_discount)).setPriceWithFree(model.getPrice());
        ((PriceTextView) getView(R.id.tv_price_unline)).needMidLine().setPrice(model.getUnderlinedPrice());
        setVisible(R.id.tv_price_unline, PriceHelper.parsePriceStr(model.getUnderlinedPrice()) != 0);
        //vip课程
//        setVisible(R.id.tv_course_vip, false);
        setVisible(R.id.iv_sign_up, model.isHasBuy());
        ((TimeRangeAndPeriodsView) getView(R.id.tv_date))
                .setIv((ImageView) getView(R.id.iv_clock))
                .setPeriods(model.getTotalPeriods())
                .setStartPlay(model.getStartPlayDate())
                .setEndPlay(model.getEndPlayDate())
                .setShowTimeRanger(!model.isHasBuy())
                .show();

        setText(R.id.public_tv_sign_up_num, model.getSalesNum() + "人已报名");
        CourseHolderHelper.setHeadInfo(this, model.getTeacherList(), tvArrAy, ivArrAy);
        setCourseTitle(model);

    }

    protected void setCourseTitle(PublicCourseBean model) {
        TextViewTagHelper
                .getInstance()
                .bind()
                .setVip(model.isVipCourse())
                .setCoupon(model.isHasCoupon())
                .setSpell(model.isJoinSpell())
                .show(((CourseTitleView) getView(R.id.tv_course_name)),model.getTitle());
    }


}
