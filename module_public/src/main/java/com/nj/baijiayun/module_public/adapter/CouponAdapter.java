package com.nj.baijiayun.module_public.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.helper.PriceHelper;
import com.nj.baijiayun.module_public.widget.PriceTextView;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.nj.baijiayun.refresh.recycleview.BaseViewHolder;

import java.text.MessageFormat;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class CouponAdapter extends BaseRecyclerAdapter<PublicCouponBean> {


    public CouponAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.public_item_coupon;
    }

    @Override
    protected void bindViewAndData(BaseViewHolder holder, PublicCouponBean model, int position) {
        ConstraintLayout mCl = (ConstraintLayout) holder.getView(R.id.cl_root);

        for (int i = 0; i < mCl.getChildCount(); i++) {
            View childAt = mCl.getChildAt(i);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTextColor(ContextCompat.getColor(getContext(), model.isCanGet() ? R.color.public_coupon_get : R.color.public_coupon_un_get));
            }

        }
        holder.setBackgroundRes(R.id.tv_get, model.isCanGet() ? R.drawable.pubile_coupon_sel_bg : R.drawable.pubile_coupon_nor_bg);
        holder.setBackgroundRes(R.id.tv_amount_new, model.isCanGet() ? R.drawable.coupon_bg : R.drawable.coupon_bg_nor);
        holder.setBackgroundRes(R.id.view_dotted, model.isCanGet() ? R.drawable.public_dotted_line_coupon_can_get : R.drawable.public_dotted_line_coupon_not_get);
        holder.setTextColor(R.id.tv_get, model.isCanGet() ? ContextCompat.getColor(getContext(),R.color.white) : ContextCompat.getColor(getContext(),R.color.public_999999));
        ((PriceTextView) holder.getView(R.id.tv_amount)).inListShow()
                .setNeedBoldPrice(true)
                .setPrice(model.getDiscountedPrice());

//        ((PriceTextView) holder.getView(R.id.tv_amount)).setPriceUnitHeightPx(DensityUtil.dip2px(3)).setShowShortPrice(true).setPriceWithFmtTxt(newMoney);
        ((TextView) holder.getView(R.id.tv_amount_limit)).setText(MessageFormat.format("满{0}可用", PriceHelper.getCommonPriceEndOfPointNotZeroNumEnd(model.getFullReduction())));
        holder.setText(R.id.tv_type_limitation, model.getName());

        holder.setText(R.id.tv_date, model.isFixedType() ?
                MessageFormat.format(
                        getContext().getString(R.string.public_format_coupon_fixed_days)
                        , model.getValidDay()
                )
                : MessageFormat.format(
                getContext().getString(R.string.public_format_coupon_date_range)
                , TimeFormatHelper.getYearMonthAndDayByPoint(model.getValidStart())
                , TimeFormatHelper.getYearMonthAndDayByPoint(model.getValidEnd()
                )


        ));
        holder.setTextColor(R.id.tv_date, model.isCanGet() ? ContextCompat.getColor(getContext(),R.color.public_999999) : ContextCompat.getColor(getContext(),R.color.public_999999));
        holder.setText(R.id.tv_get, model.isCanGet() ? "立即领取" : "无法领取");

    }

}
