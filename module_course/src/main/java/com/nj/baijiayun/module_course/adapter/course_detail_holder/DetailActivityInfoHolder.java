package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.ServiceAdapter;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.ActivityItemView;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.widget.CouponDialog;
import com.nj.baijiayun.module_public.widget.PublicBottomListDialog;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;
import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailActivityInfoHolder extends BaseMultipleTypeViewHolder<PublicCourseDetailBean> {

    private List<PublicCouponBean> mCouponBeans;
    private PublicCourseDetailBean model;

    public DetailActivityInfoHolder(ViewGroup parent) {
        super(parent);

        getView(R.id.view_coupon).setOnClickListener(v -> {
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
                    .setSpace(30)
                    .setAdapter(new ServiceAdapter(getContext()))
                    .addData(model.getService())
                    .show();
        });

    }

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_activity_v2;

    }

    public void setInfo(List<PublicCouponBean> couponBeans, PublicCourseDetailBean model) {
        if (couponBeans==null||model == null) {
            return;
        }
        this.model = model;
        this.mCouponBeans = couponBeans;
        setVisible(R.id.view_vip, model.isVipCourse());
        setVisible(R.id.view_server, !model.isServiceEmpty());
        setVisible(R.id.view_coupon, couponBeans != null && couponBeans.size() > 0);

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

    @Override
    public void bindData(PublicCourseDetailBean model, int position, BaseRecyclerAdapter adapter) {

    }
}
