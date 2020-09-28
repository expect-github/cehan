package com.nj.baijiayun.module_main.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-09-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe 这个需要结合画间距的一起用
 */
public class HomeLineItemDecoration extends RecyclerView.ItemDecoration {

    private BaseRecyclerAdapter adapter;
    private final Rect mBounds = new Rect();
    private final Rect mTmpDecorRect = new Rect();
    private Paint mPaint = new Paint();
    private int mLineWidth;
    private int mLineColor;
    private static final int DEFAULT_COLOR = Color.RED;
    private int mPaddindLeft;
    private int mPaddindRight;


    public HomeLineItemDecoration(BaseRecyclerAdapter recyclerAdapter) {
        this.adapter = recyclerAdapter;
        init();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        drawHorizontal(c, parent);
    }


    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        // 重点这里
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            //取出item的间距
            parent.getLayoutManager().calculateItemDecorationsForChild(child, mTmpDecorRect);
            if (noNeedDrawLine(parent, position)) {

                continue;
            }
            //如果当前bean 跟下一个bean是相同的加分割线
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            Logger.d("mTmpDecorRect--->" + mTmpDecorRect.top);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            float y = bottom - mTmpDecorRect.bottom + mTmpDecorRect.bottom / 2 + mLineWidth / 2;
            canvas.drawLine(left + mPaddindLeft, y, right - mPaddindRight, y, mPaint);

        }
        canvas.restore();
    }


    private boolean noNeedDrawLine(RecyclerView parent, int position) {
        return noNeedDrawLineByBean(position) || noNeedDrawLineBySpanSize(parent, position);
    }

    private boolean noNeedDrawLineByBean(int position) {
        Object indexBean = getIndexBean(position);
        Object nextBean = getIndexBean(position + 1);
        if (indexBean == null || nextBean == null) {
            return true;
        }
        return !indexBean.getClass().equals(nextBean.getClass());
    }

    private boolean noNeedDrawLineBySpanSize(RecyclerView parent, int position) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        assert layoutManager != null;
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        return spanSizeLookup.getSpanSize(position) == 1;
    }


    private Object getIndexBean(int position) {
        return adapter.getItem(position - 1);

    }

    public HomeLineItemDecoration setLineWidthDp(float mLineWidth) {
        this.mLineWidth = DensityUtil.dip2px(mLineWidth);
        return this;
    }

    public HomeLineItemDecoration setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        mPaint.setColor(this.mLineColor);
        return this;
    }

    public void setPaddingLeftRightDp(int padding) {
        mPaddindLeft = DensityUtil.dip2px(padding);
    }

    private void init() {
        mLineWidth = DensityUtil.dip2px(0.5f);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mLineColor = DEFAULT_COLOR;
        mPaint.setColor(mLineColor);
        mPaddindLeft = DensityUtil.dip2px(15);
        mPaddindRight = mPaddindLeft;
    }


}
