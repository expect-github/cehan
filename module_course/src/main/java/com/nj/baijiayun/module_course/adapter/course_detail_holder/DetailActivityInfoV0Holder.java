package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.ViewGroup;

import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.ConstrainsClickHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.widget.CouponDialog;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import java.text.MessageFormat;
import java.util.List;

import androidx.constraintlayout.widget.Group;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
@Deprecated
public class DetailActivityInfoV0Holder extends BaseMultipleTypeViewHolder<PublicCourseDetailBean> {

    private List<PublicCouponBean> mCouponBeans;

    public DetailActivityInfoV0Holder(ViewGroup parent) {
        super(parent);
        ConstrainsClickHelper.setOnClickListener((Group) getView(R.id.group_coupon), getConvertView(), v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            CouponDialog couponDialog = new CouponDialog(getContext(), mCouponBeans);
            couponDialog.show();
        });

        ConstrainsClickHelper.setOnClickListener((Group) getView(R.id.group_vip), getConvertView(), v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getVip());
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_activity;

    }
    public void setInfo(List<PublicCouponBean> couponBeans, PublicCourseDetailBean model) {
        this.mCouponBeans = couponBeans;
        setVisible(R.id.group_vip, model.isVipCourse());
        setVisible(R.id.group_coupon, couponBeans != null && couponBeans.size() > 0);

        setText(R.id.tv_vip_get, getContext().getString(model.isVipUser() ? R.string.public_renew : R.string.public_opening));
        setText(R.id.tv_vip_content, model.isVipUser() ?
                MessageFormat.format("您的会员到期日为：{0}", TimeFormatHelper.getYearMonthAndDayFormatChina(model.getVipUserEnd()))
                : "免费观看");


        if (couponBeans == null || couponBeans.size() <= 0) {
            return;
        }
        setText(R.id.tv_coupon_content, "领取优惠券至多可减");
        ((PriceTextView) getView(R.id.tv_coupon_price)).setPriceWithFmtTxt(String.valueOf(getMaxCoupon(couponBeans)));


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
