package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

/**
 * Created by Administrator on 2020/4/21 0021.
 */

public class DetailsScrollView extends NestedScrollView {
    private OnScrollChangedListener mOnScrollChangedListener;
    public DetailsScrollView(Context context) {
        super(context);
    }

    public DetailsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailsScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public interface ScrollViewChange {
        void onScrollViewChange(int l, int t, int oldl, int oldt);
    }


    public ScrollViewChange onScrollViewChange;

    public void setOnScrollViewChange(ScrollViewChange onScrollViewChange) {
        this.onScrollViewChange = onScrollViewChange;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollViewChange != null) {
            onScrollViewChange.onScrollViewChange(l, t, oldl, oldt);
        }
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(NestedScrollView who, int l, int t, int oldl, int oldt);
    }


}
