package com.nj.baijiayun.module_public.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_public.R;

/**
 * @author chengang
 * @date 2019-06-18
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class PublicBgWithWhiteBackView extends RelativeLayout {
    private TextView mActivityTitleTv;
    private ImageView mBackImageview;
    private ImageView mImgRight;

    public PublicBgWithWhiteBackView(Context context) {
        this(context, null, 0);

    }

    public PublicBgWithWhiteBackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicBgWithWhiteBackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.public_layout_back_with_bg, this);
        mActivityTitleTv = findViewById(R.id.tv_activity_title);
        mBackImageview = findViewById(R.id.img_back);
        mImgRight = findViewById(R.id.img_right);
        mBackImageview.setOnClickListener(v -> ((Activity) getContext()).finish());
        mImgRight.setVisibility(GONE);
    }
    public void setRightOnClickListener(OnClickListener onClickListener)
    {
        mImgRight.setVisibility(VISIBLE);
        mImgRight.setOnClickListener(onClickListener);
    }

    public void setTitle(String text) {
        mActivityTitleTv.setText(text);
    }

    public TextView getActivityTitleTv() {
        return mActivityTitleTv;
    }

    public ImageView getBackImageview() {
        return mBackImageview;
    }

}
