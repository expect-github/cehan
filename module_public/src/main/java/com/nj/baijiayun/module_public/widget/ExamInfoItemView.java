package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_public.R;


/**
 * @author chengang
 * @date 2019-06-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_exam.widget
 * @describe
 */
public class ExamInfoItemView extends LinearLayout {
    private TextView mTopTv;
    private TextView mBottomTv;
    private TextView mLineView;

    public ExamInfoItemView(Context context) {
        super(context);

    }

    public ExamInfoItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ExamInfoItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.public_layout_info_item, this);
        mTopTv = inflate.findViewById(R.id.tv_top);
        mBottomTv = inflate.findViewById(R.id.tv_bottom);
        mLineView = inflate.findViewById(R.id.view_line);
    }

    public ExamInfoItemView setTopText(String text) {
        mTopTv.setText(text);
        return this;
    }

    public ExamInfoItemView setTopText(String text, String text2) {
        SpannableString ssb = new SpannableString(text + text2);
        ssb.setSpan(new AbsoluteSizeSpan(12, true), text.length(), (text + text2).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        mTopTv.setText(ssb);
        mLineView.setText(ssb);

        return this;
    }

    public ExamInfoItemView setTopUnline() { //下划线
        mLineView.setVisibility(VISIBLE);
        setEnabled(true);
        return this;
    }

    public ExamInfoItemView setBottomText(String text) {
        mBottomTv.setText(text);
        return this;
    }


}
