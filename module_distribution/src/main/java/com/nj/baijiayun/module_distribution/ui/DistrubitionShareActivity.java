package com.nj.baijiayun.module_distribution.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_distribution.R;
import com.nj.baijiayun.module_public.widget.PriceTextView;

import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.ui
 * @describe
 */
public class DistrubitionShareActivity extends BaseAppActivity {
    private PriceTextView mShareGetPrcieTv;
    private ImageView mCoverIv;

    @Override
    public boolean needAutoInject() {
        return false;

    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.distribution_activity_share;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

//
        mShareGetPrcieTv = findViewById(R.id.tv_share_get_prcie);
        mCoverIv = findViewById(R.id.iv_cover);
        String couponPrice = "";
        mShareGetPrcieTv.setPriceColor(
                ContextCompat.getColor(this, R.color.common_main_color))
                .setDefaultPrice("领取优惠券至多可减" + couponPrice, couponPrice);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

        Glide.with(this).load("").into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                /*  w/h= sw/sh sh=sw*h/w*/
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mCoverIv.getLayoutParams();
                layoutParams.height = resource.getIntrinsicHeight() * (DensityUtil.getScreenWidth() - DensityUtil.dip2px(23 * 2)) / resource.getIntrinsicWidth();
                mCoverIv.setLayoutParams(layoutParams);
                mCoverIv.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {

            }
        });

    }
}
