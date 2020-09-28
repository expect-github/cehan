package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.widget.XChronometer;
import com.nj.baijiayun.basic.widget.countdown.AbstractNewCountDownTimer;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.ServiceAdapter;
import com.nj.baijiayun.module_course.bean.wx.AssembleCourseBean;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.ActivityItemView;
import com.nj.baijiayun.module_public.adapter.CommonAdapter;
import com.nj.baijiayun.module_public.adapter.SimpleTeacherInfoAdapter;
import com.nj.baijiayun.module_public.adapter.ViewHolder;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.helper.PublicFormatHelper;
import com.nj.baijiayun.module_public.helper.TimeManager;
import com.nj.baijiayun.module_public.holder.CheckUtil;
import com.nj.baijiayun.module_public.holder.DateUtil;
import com.nj.baijiayun.module_public.manager.LifecycleManager;
import com.nj.baijiayun.module_public.widget.CouponDialog;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.module_public.widget.PublicBottomListDialog;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.text.MessageFormat;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailBaseInfoHolder extends BaseMultipleTypeViewHolder<PublicCourseDetailBean> {


    private boolean isCollect;
    private ClickCallBack clickCallBack;
    private List<PublicCouponBean> mCouponBeans;
    private PublicCourseDetailBean model;

    public DetailBaseInfoHolder(ViewGroup parent) {
        super(parent);
        setOnClickListener(R.id.iv_collect, v -> {

            if (JumpHelper.checkLogin()) {
                return;
            }
            isCollect = !isCollect;
            if (clickCallBack != null) {
                clickCallBack.collect(isCollect);
            }

        });

        getView(R.id.view_coupon).setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            CouponDialog couponDialog = new CouponDialog(getContext(), mCouponBeans);
            couponDialog.show();
        });

        getView(R.id.view_coupon_line).setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            CouponDialog couponDialog = new CouponDialog(getContext(), mCouponBeans);
            couponDialog.show();
        });

        getView(R.id.view_vip).setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getVip());
        });


        getView(R.id.view_server).setOnClickListener(v -> {
            if (model == null) {
                return;
            }
            new PublicBottomListDialog(getContext())
                    .setTitle("课程服务")
                    .setSpace(25)
                    .setAdapter(new ServiceAdapter(getContext()))
                    .addData(model.getService())
                    .show();
        });


    }


    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_head_cover;
    }


    public void updateSignUpAndLimitNumber(PublicCourseDetailBean model) {


        String satTime = DateUtil.getDateToString(model.getStartPlayDate());
        String endTime = DateUtil.getDateToString(model.getEndPlayDate());
        String conTime = "    共" + model.getTotalPeriods() + "个课时";

        setText(R.id.tv_periods, satTime + "-" + endTime + conTime);
        setText(R.id.tv_num_pre, model.getSalesNum() + "人已报名");
//        setText(R.id.tv_periods, MessageFormat.format("共{0}课时 | {1}人已报名", model.getTotalPeriods(), model.getSalesNum()));
        setText(R.id.tv_sales_limit, MessageFormat.format("报名上限：{0}人 | 剩余名额：{1}", model.getStoreNum(), model.getStock()));
    }

    @Override
    public void bindData(PublicCourseDetailBean model, int position, BaseRecyclerAdapter adapter) {
        initUiVisible();
        if (ConstsCouseType.isLive(model.getCourseType())) {
            setVisible(R.id.tv_periods, true);
            setVisible(R.id.tv_time_range, false);
        } else if (ConstsCouseType.isSystem(model.getCourseType())) {
            setVisible(R.id.tv_periods, true);
            setVisible(R.id.tv_time_range, false);
        } else if (ConstsCouseType.isFace(model.getCourseType())) {
            //显示库存
            setVisible(R.id.tv_sales_limit, model.isShowStore());
            setVisible(R.id.tv_sign_up_end, true);
            setVisible(R.id.tv_time_range, false);
            setVisible(R.id.tv_address, true);
        } else {
            setVisible(R.id.tv_periods, true);
        }
        this.isCollect = model.isCollect();
        setText(R.id.tv_course_title, model.getTitle());
        ((PriceTextView) getView(R.id.tv_price_discount)).inListShow()
                .setNeedBoldPrice(true)
                .setPrice(model.getPrice());
        ((PriceTextView) getView(R.id.tv_price_unline)).needMidLine().inListShow().setNeedBoldPrice(true).setPriceWithFmtTxt(model.getUnderlinedPrice());
        //划线价格
//        setVisible(R.id.tv_price_unline, PriceHelper.parsePriceStr(model.getUnderlinedPrice()) != 0);
        setVisible(R.id.tv_price_unline, false);
        setImageResource(R.id.iv_collect, model.isCollect() ? R.drawable.public_ic_collected : R.drawable.public_ic_un_collect);
        updateSignUpAndLimitNumber(model);
        //面授课才有的
        setText(R.id.tv_sign_up_end, MessageFormat.format("报名截止时间：{0} ", TimeFormatHelper.getYearMonthDayHourMinSplitByPoint(model.getFaceCourseSignUpEndTime())));
        setText(R.id.tv_time_range, MessageFormat.format("开课时间：{0}", PublicFormatHelper.getTimeByTimeRange(model.getStartPlayDate(), model.getEndPlayDate())));
        setText(R.id.tv_address, MessageFormat.format("开课地点：{0}", model.getAddress()));
        //拼团
        setVisible(R.id.ll_price, true);
        setVisible(R.id.cl_assemble_info, false);
//        ImageLoader.with(getContext()).load(model.getCover()).into((ImageView) getView(R.id.iv_cover));
        //科目
        if (getView(R.id.tv_course_jub) != null) {
            setSubiect(R.id.tv_course_jub, model.getSubject_name());
        }
        if (CheckUtil.isListNotEmpty(model.getTeacherList())) {
            RecyclerView rv = (RecyclerView) getView(R.id.tv_detail_rv);
            rv.setNestedScrollingEnabled(false);
            rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            rv.addItemDecoration(SpaceItemDecoration.create().setSpace(15).setIncludeEdge(false).setLayoutManagerType(SpaceItemDecoration.LINEARLAYOUT));
            SimpleTeacherInfoAdapter simpleTeacherInfoAdapter = new SimpleTeacherInfoAdapter(getContext());
            simpleTeacherInfoAdapter.setOnItemClickListener((holder, position1, view, item) -> JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("{0}?id={1}&back=1", ConstsH5Url.getTeacher(), String.valueOf(item.getTeacherId()))));
            rv.setAdapter(simpleTeacherInfoAdapter);
            simpleTeacherInfoAdapter.addAll(model.getTeacherList());

        }

    }

    /**
     * 新增的 2020/8/10
     * @param couponBeans
     * @param model
     * @param type
     */
    public void setInfo(List<PublicCouponBean> couponBeans, PublicCourseDetailBean model, boolean type) {
        if (couponBeans == null || model == null) {
            return;
        }

        if (type) {
            this.model = model;
            this.mCouponBeans = couponBeans;
            setVisible(R.id.view_vip, model.isVipCourse());
            setVisible(R.id.view_coupon_line, couponBeans != null && couponBeans.size() > 0);
            setVisible(R.id.view_server, !model.isServiceEmpty());
            setVisible(R.id.view_coupon, false);
            ((ActivityItemView) getView(R.id.view_coupon)).setGetText("领取");
            ((ActivityItemView) getView(R.id.view_server)).setGetText("详情");

            if (!model.isServiceEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < model.getService().size(); i++) {
                    if (i > 0) {
                        stringBuilder.append("/");
                    }
                    stringBuilder.append(model.getService().get(i).getName());
                }
                ((ActivityItemView) getView(R.id.view_server)).setContent(stringBuilder.toString());
            }
            ((ActivityItemView) getView(R.id.view_coupon)).setTitle("优惠：");
            ((ActivityItemView) getView(R.id.view_vip)).setTitle("会员：");
            ((ActivityItemView) getView(R.id.view_server)).setTitle("服务：");

            ((ActivityItemView) getView(R.id.view_vip)).setGetText(getContext().getString(model.isVipUser() ? R.string.public_renew : R.string.public_opening));
            ((ActivityItemView) getView(R.id.view_vip)).setContent(model.isVipUser() ?
                    MessageFormat.format("您的会员到期日为：{0}", TimeFormatHelper.getYearMonthAndDayFormatChina(model.getVipUserEnd()))
                    : "免费观看");


            if (couponBeans == null || couponBeans.size() <= 0) {
                return;
            }

            String couponPrice = PriceHelper.getCommonPrice(String.valueOf(getMaxCoupon(couponBeans)));
            setText(R.id.view_coupon_text,"¥"+couponPrice);
//        TextSpanHelper.matcherSearchKeyWord(
//                ContextCompat.getColor(getContext(), R.color.public_orange)
//                , "领取优惠券至多可减" + couponPrice
//                , couponPrice
            ((ActivityItemView) getView(R.id.view_coupon)).getContentTv().setPriceColor(
                    ContextCompat.getColor(getContext(), R.color.public_orange))
                    .setDefaultPrice("领取优惠券至多可减" + couponPrice, couponPrice);
//        setText(R.id.tv_coupon_content, "领取优惠券至多可减");
//        ((PriceTextView) getView(R.id.tv_coupon_price)).setPriceWithFmtTxt();

            setActivityItemViewMargin();

        } else {
            setVisible(R.id.view_vip, false);
            setVisible(R.id.view_server, false);
            setVisible(R.id.view_coupon, false);
            setVisible(R.id.view_coupon_line, false);
        }



    }

    private void setActivityItemViewMargin() {
        //获取父亲
        LinearLayout ll = (LinearLayout) getView(R.id.view_coupon).getParent();
        for (int i = 1; i < ll.getChildCount(); i++) {
            int j = i - 1;
            while (j > -1) {
                //如果找到一个前面的 显示了
                if (ll.getChildAt(j).getVisibility() == View.VISIBLE) {
                    LinearLayout.LayoutParams layoutParams = ((LinearLayout.LayoutParams) ll.getChildAt(i).getLayoutParams());
                    layoutParams.topMargin = DensityUtil.dip2px(10);
                    ll.getChildAt(i).setLayoutParams(layoutParams);
                    break;
                }
                j--;
            }
        }
    }

    private long getMaxCoupon(List<PublicCouponBean> couponBeans) {
        long max = couponBeans.get(0).getDiscountedPrice();
        for (int i = 1; i < couponBeans.size(); i++) {
            long l = couponBeans.get(i).getDiscountedPrice();
            if (l > max) {
                max = l;
            }
        }
        return max;
    }


    private void initUiVisible() {
        setVisible(R.id.tv_periods, false);
        setVisible(R.id.tv_sales_limit, false);
        setVisible(R.id.tv_sign_up_end, false);
        setVisible(R.id.tv_time_range, false);
        setVisible(R.id.tv_address, false);
    }

    /**
     * 科目显示
     *
     * @param viewId
     * @param type
     */
    private void setSubiect(int viewId, String type) {
        TextView tv_subject = (TextView) getView(viewId);
        tv_subject.setText(type);
        if (CheckUtil.isEmpty(type)) {
            tv_subject.setVisibility(View.GONE);
        } else {
            tv_subject.setVisibility(View.VISIBLE);
        }
        switch (type) {
            case "语文":
                tv_subject.setBackgroundResource(com.nj.baijiayun.module_public.R.drawable.public_bg_course_type_ke_v1);
                break;

            case "数学":
                tv_subject.setBackgroundResource(com.nj.baijiayun.module_public.R.drawable.public_bg_course_type_ke_v2);
                break;
            case "英语":
                tv_subject.setBackgroundResource(com.nj.baijiayun.module_public.R.drawable.public_bg_course_type_ke_v3);
                break;
            case "家庭教育":
                tv_subject.setBackgroundResource(com.nj.baijiayun.module_public.R.drawable.public_bg_course_type_ke_v5);
                break;
            case "多科":
                tv_subject.setBackgroundResource(com.nj.baijiayun.module_public.R.drawable.public_bg_course_type_ke_v4);
                break;
            default:
                tv_subject.setBackgroundResource(com.nj.baijiayun.module_public.R.drawable.public_bg_course_type_ke_v6);
                break;
        }
    }

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void collect(boolean isCollect);
    }

    private AbstractNewCountDownTimer abstractCountDownManager;
    private static final int ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;

    public void setAssembleInfo(AssembleCourseBean assembleInfo) {
        setVisible(R.id.ll_price, false);
        setVisible(R.id.cl_assemble_info, true);

        setText(R.id.tv_assemble_join_number, "已拼" + assembleInfo.getJoinNum());
        ((PriceTextView) getView(R.id.tv_assemble_price))
                .setDefaultTextColorWhite()
                .setDefaultPrice(assembleInfo.getSpellPrice());
        ((PriceTextView) getView(R.id.tv_assemble_under_line_price))
                .setDefaultTextColorWhite()
                .needMidLine()
                .setDefaultPrice(assembleInfo.getPrice());

        setText(R.id.tv_assemble_number, assembleInfo.getUserNum() + "人团");
        long time = assembleInfo.getEndTime() * 1000 - TimeManager.getInstance().getServiceTime();
        if (abstractCountDownManager == null) {
            abstractCountDownManager = new AbstractNewCountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int day = (int) (millisUntilFinished / ONE_DAY_MILLIS);

                    String hourMinSec = XChronometer.formatDuration(millisUntilFinished % ONE_DAY_MILLIS);
                    setText(R.id.tv_assemble_left_time, MessageFormat.format("{0}{1}", day > 0 ? day + "天" : "", hourMinSec));

                }

                @Override
                public void onFinish() {
                    setText(R.id.tv_assemble_left_time, "已结束");
                }

            };
            abstractCountDownManager.start();
        } else {
            abstractCountDownManager.reStart(time, 1000);
        }

        LifecycleManager.create((LifecycleOwner) getContext())
                .addLifecycleCallBack(new LifecycleManager.BaseObserver() {
                    @Override
                    public void onDestory() {
                        super.onDestory();
                        if (abstractCountDownManager != null) {
                            abstractCountDownManager.cancel();
                        }
                    }
                });

    }


}
