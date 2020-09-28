package com.nj.baijiayun.module_common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.nj.baijiayun.basic.utils.DensityUtil;

/**
 * 常用RecyclerView分割线
 * new CommonLineDividerDecoration(context,VERTICAL)
 * .setLineWidth() //设置分割线宽度
 * .setLineColor() //设置分割线颜色
 * .setLinePaddingPx() //设置分割线的padding，如果方向VERTICAL,则top，bottom不生效，反之一样
 *
 * @author houyi
 * @Date 2018/7/22 11:04
 */
public class CommonLineDividerDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private static final String TAG = "DividerItem";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_COLOR = Color.GRAY;
    /**
     * should reverse the line ,is reverse the padding will be draw and the line will not draw
     */
    private final boolean mReverse;
    private boolean mNeedDrawLast = true;

    public CommonLineDividerDecoration setNeedDrawLast(boolean mNeedDrawLast) {
        this.mNeedDrawLast = mNeedDrawLast;
        return this;
    }

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();
    private Paint mPaint = new Paint();
    private int mLineColor;
    private int mLineWidth = DEFAULT_WIDTH;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context     Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public CommonLineDividerDecoration(Context context, int orientation) {
        this(context, orientation, false);
    }

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context     Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     * @param reverse     should reverse the line ,is reverse the padding will be draw and the line will not draw
     */
    public CommonLineDividerDecoration(Context context, int orientation, boolean reverse) {
        setOrientation(orientation);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DEFAULT_WIDTH);
        this.mReverse = reverse;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    public CommonLineDividerDecoration setLineColor(@ColorInt int lineColor) {
        this.mLineColor = lineColor;
        mPaint.setColor(lineColor);
        return this;
    }

    public CommonLineDividerDecoration setLineWidthPx(int lineWidth) {
        this.mLineWidth = lineWidth;
        mPaint.setStrokeWidth(lineWidth);
        return this;
    }

    public CommonLineDividerDecoration setLineWidthDp(int dp) {
        setLineWidthPx((int) DensityUtil.dp2px(dp));
        return this;
    }

    public CommonLineDividerDecoration setLinePaddingPx(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.mPaddingLeft = paddingLeft;
        this.mPaddingTop = paddingTop;
        this.mPaddingRight = paddingRight;
        this.mPaddingBottom = paddingBottom;
        return this;
    }

    public CommonLineDividerDecoration setLinePaddingDp(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.mPaddingLeft = (int) DensityUtil.dp2px(paddingLeft);
        this.mPaddingTop = (int) DensityUtil.dp2px(paddingTop);
        this.mPaddingRight = (int) DensityUtil.dp2px(paddingRight);
        this.mPaddingBottom = (int) DensityUtil.dp2px(paddingBottom);
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        if (mLineColor == 0) {
            return;
        }
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (!mNeedDrawLast) {
                if (i == childCount - 1) {
                    return;
                }
            }

            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mLineWidth;
            if (!mReverse) {
                canvas.drawLine(left + mPaddingLeft, (top + bottom) / 2, right - mPaddingRight, (top + bottom) / 2, mPaint);
            } else {
                canvas.drawLine(left, (top + bottom) / 2, left + mPaddingLeft, (top + bottom) / 2, mPaint);
                canvas.drawLine(right - mPaddingRight, (top + bottom) / 2, right, (top + bottom) / 2, mPaint);

            }
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        if (mLineColor == 0) {
            return;
        }
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mLineWidth;
            if (!mReverse) {
                canvas.drawLine(left, top + mPaddingTop, right, bottom - mPaddingBottom, mPaint);
            } else {
                canvas.drawLine(left, top, right, top + mPaddingTop, mPaint);
                canvas.drawLine(left, bottom - mPaddingBottom, right, bottom, mPaint);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mLineWidth);
        } else {
            outRect.set(0, 0, mLineWidth, 0);
        }
    }
}
