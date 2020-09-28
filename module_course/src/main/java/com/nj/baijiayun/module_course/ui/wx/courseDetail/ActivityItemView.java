package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.widget.PriceTextView;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author chengang
 * @date 2020-02-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.courseDetail
 * @describe
 */
public class ActivityItemView extends ConstraintLayout {
    private TextView mActTv;
    private PriceTextView mActContentTv;
    private TextView mActGetTv;

    public ActivityItemView(Context context) {
        this(context, null);

    }

    public ActivityItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActivityItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.course_layout_activity_item, this);
        mActTv = inflate.findViewById(R.id.tv_act);
        mActContentTv = inflate.findViewById(R.id.tv_act_content);
        mActGetTv = inflate.findViewById(R.id.tv_act_get);
    }

    public PriceTextView getContentTv() {
        return mActContentTv;
    }

    public void setContent(String text) {
        mActContentTv.setText(text);
    }

    public void setTitle(String title) {
        mActTv.setText(title);
    }

    public void setGetText(String text) {
        mActGetTv.setText(text);
    }


}
