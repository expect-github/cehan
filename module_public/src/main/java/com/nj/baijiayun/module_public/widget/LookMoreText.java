package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_common.helper.AnimationHelper;
import com.nj.baijiayun.module_public.R;

/**
 * @author chengang
 * @date 2019-06-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class LookMoreText extends LinearLayout {

    private TextView mConetntTv;
    private ImageView mImgLookMore;
    private int maxLines = 2;
    private boolean isExpand = false;

    public LookMoreText(Context context) {
        super(context);

    }

    public LookMoreText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LookMoreText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public void setText(String text) {
        mConetntTv.setText(text);
        mConetntTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int lineCount = mConetntTv.getLineCount();
                //2行显示全了
                if (lineCount <= maxLines) {
                    mImgLookMore.setVisibility(View.GONE);
                }else {
                    mConetntTv.setMaxLines(maxLines);
                }
                mConetntTv.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    private void initView(Context context) {

        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.public_layout_look_more_text, this);
        mConetntTv = findViewById(R.id.tv_conetnt);
        mImgLookMore = findViewById(R.id.img_look_more);


        mImgLookMore.setOnClickListener(v -> {
            isExpand = !isExpand;
            if (isExpand) {
                AnimationHelper.rotate180(mImgLookMore);
                mConetntTv.setMaxLines(Integer.MAX_VALUE);
            } else {
                AnimationHelper.rotate180Reverse(mImgLookMore);
                mConetntTv.setMaxLines(maxLines);
            }
        });
    }
}
