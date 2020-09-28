package com.nj.baijiayun.module_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nj.baijiayun.module_common.R;


/*
 * 三角标签
 * 可以设置背景颜色,文字颜色,文字大小,是否是左边的标签,是否是上下的标签
 * 默认左上方的标签
 * */
public class TriangleLabelView extends View {

    private Context mContext;
    private Paint mBgPaint = new Paint();
    private Paint mTvPaint = new Paint();
    int mBgColor = 0xAA000000;
    int mTvColor = 0x00FFFFFF;
    int mTvSize;
    String mTvText;
    boolean mLayoutDirectionIsLeft;
    boolean ismLayoutDirectionIsTop;
    private int mWidth;
    private int mHeight;


    public TriangleLabelView(Context context) {
        this(context, null);
    }

    public TriangleLabelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleLabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTriangleLabelView);
        mBgColor = typedArray.getColor(R.styleable.CommonTriangleLabelView_commonBgColor, mBgColor);
        mTvColor = typedArray.getColor(R.styleable.CommonTriangleLabelView_commonTvColor, mTvColor);
        mTvSize = typedArray.getDimensionPixelSize(R.styleable.CommonTriangleLabelView_commonTvSize, 20);
        mTvText = typedArray.getString(R.styleable.CommonTriangleLabelView_commonTvText);
        mTvText = mTvText == null ? "" : mTvText;
        mLayoutDirectionIsLeft = typedArray.getBoolean(R.styleable.CommonTriangleLabelView_commonLayoutDirectionIsLeft, true);
        ismLayoutDirectionIsTop = typedArray.getBoolean(R.styleable.CommonTriangleLabelView_commonLayoutDirectionIsTop, true);
        typedArray.recycle();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        mTvPaint.setColor(mTvColor);
        mTvPaint.setTextSize(mTvSize);
        mTvPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = widthSize;
        mHeight = heightSize;
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        if (mLayoutDirectionIsLeft && ismLayoutDirectionIsTop) {
//            标签位于左上
            path.lineTo(0, mHeight);
            path.lineTo(mWidth, 0);
        } else if (!mLayoutDirectionIsLeft && ismLayoutDirectionIsTop) {
//            右上
            path.lineTo(mWidth, 0);
            path.lineTo(mWidth, mHeight);
        } else if (mLayoutDirectionIsLeft && !ismLayoutDirectionIsTop) {
//            左下
            path.lineTo(0, mHeight);
            path.lineTo(mWidth, mHeight);
        } else {
//            右下
            path.moveTo(mWidth, 0);
            path.lineTo(mWidth, mHeight);
            path.lineTo(0, mHeight);
        }
        path.close();
        canvas.drawPath(path, mBgPaint);
        path = new Path();
        if (mLayoutDirectionIsLeft && ismLayoutDirectionIsTop) {
            //            标签位于左上
            path.moveTo(0, mHeight);
            path.lineTo(mWidth, 0);
        } else if (!mLayoutDirectionIsLeft && ismLayoutDirectionIsTop) {
            //            右上
            path.moveTo(0, 0);
            path.lineTo(mWidth, mHeight);
        } else if (mLayoutDirectionIsLeft && !ismLayoutDirectionIsTop) {
            //            左下
            path.moveTo(mWidth, mHeight);
            path.lineTo(0, 0);
        } else {
            //            右下
            path.moveTo(mWidth, 0);
            path.lineTo(0, mHeight);
        }
        canvas.drawTextOnPath(mTvText, path, 0, 0, mTvPaint);
    }


    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
    }

    public void setTvColor(int mTvColor) {
        this.mTvColor = mTvColor;
    }

    public void setTvSize(int mTvSize) {
        this.mTvSize = mTvSize;
    }

    public void setTvText(String mTvText) {
        this.mTvText = mTvText;
    }

    public void setLayoutDirectionIsLeft(boolean mLayoutDirectionIsLeft) {
        this.mLayoutDirectionIsLeft = mLayoutDirectionIsLeft;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setIsmLayoutDirectionIsTop(boolean ismLayoutDirectionIsTop) {
        this.ismLayoutDirectionIsTop = ismLayoutDirectionIsTop;
    }
}
