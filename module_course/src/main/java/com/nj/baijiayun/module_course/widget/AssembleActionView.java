package com.nj.baijiayun.module_course.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.widget.PriceTextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.widget
 * @describe
 */
public class AssembleActionView extends ConstraintLayout {

    private View mViewLeftBg;
    private View mViewRightBg;
    private PriceTextView mBuySinglePriceTv;
    private PriceTextView mBuyTogetherPriceTv;
    private TextView mBuySingleTextTv;
    private TextView mBuyTogetherTextTv;

    public AssembleActionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.course_layout_assemble_action, this);
        mViewLeftBg = inflate.findViewById(R.id.view_left_bg);
        mViewRightBg = inflate.findViewById(R.id.view_right_bg);
        mBuySinglePriceTv = inflate.findViewById(R.id.tv_buy_single_price);
        mBuyTogetherPriceTv = inflate.findViewById(R.id.tv_buy_together_price);
        mBuySingleTextTv = inflate.findViewById(R.id.tv_buy_single_text);
        mBuyTogetherTextTv = inflate.findViewById(R.id.tv_buy_together_text);

    }

    public void setOnLeftClickListener(OnClickListener leftOnClickListener) {
        mViewLeftBg.setOnClickListener(leftOnClickListener);
    }

    public void setOnRightClickListener(OnClickListener leftOnClickListener) {
        mViewRightBg.setOnClickListener(leftOnClickListener);
    }


    public void setLeftEnable(boolean enable) {
        mViewLeftBg.setEnabled(enable);
        mBuySinglePriceTv.setEnabled(enable);
        mBuySingleTextTv.setEnabled(enable);
    }

    public void setRightEnable(boolean enable) {
        mViewRightBg.setEnabled(enable);
        mBuyTogetherPriceTv.setEnabled(enable);
        mBuyTogetherTextTv.setEnabled(enable);

    }


    public void setLeftText(String str) {
        mBuySingleTextTv.setText(str);
    }

    public void setRightText(String str) {
        mBuyTogetherTextTv.setText(str);
    }


    public void setLeftPrice(String price) {
        mBuySinglePriceTv.inListShow()
                .setNeedBoldPrice(true).setPrice(price);
    }

    public void setRightPrice(String price) {
        mBuyTogetherPriceTv.inListShow()
                .setNeedBoldPrice(true).setPrice(price);

    }


}
