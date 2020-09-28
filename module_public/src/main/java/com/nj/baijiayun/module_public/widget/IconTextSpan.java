package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.util.TypedValue;

import com.nj.baijiayun.module_public.R;

import androidx.annotation.NonNull;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class IconTextSpan extends ReplacementSpan {
    private Context mContext;
    private int mBgColorResId; //Icon背景颜色
    private String mText;  //Icon内文字
    private float mBgHeight;  //Icon背景高度
    private float mBgWidth;  //Icon背景宽度
    private float mRadius;  //Icon圆角半径
    private float mRightMargin; //右边距
    private float mTextSize; //文字大小
    private int mTextColorResId; //文字颜色

    private Paint mBgPaint; //icon背景画笔
    private TextPaint mTextPaint; //icon文字画笔


    public IconTextSpan(Context context, String text) {
        //初始化背景画笔
        mBgPaint = new Paint();

        //初始化文字画笔
        mTextPaint = new TextPaint();
        //初始化默认数值
        initDefaultValue(context, text);


    }

    public IconTextSpan setText(String mText) {
        this.mText = mText;
        setAttrToPaint();
        return this;
    }

    public IconTextSpan setBgColorResId(int bgColorResId) {
        this.mBgColorResId = bgColorResId;
        return this;
    }

    public IconTextSpan setTextColorResId(int mTextColorResId) {
        this.mTextColorResId = mTextColorResId;
        return this;
    }

    public IconTextSpan setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        return this;
    }


    public IconTextSpan setAttrToPaint() {

        //初始化画笔
        initPaint();
        //计算背景的宽度
        this.mBgWidth = caculateBgWidth(this.mText);
        return this;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mBgPaint.setColor(mContext.getResources().getColor(mBgColorResId));
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);

        mTextPaint.setColor(mContext.getResources().getColor(mTextColorResId));
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 初始化默认数值
     *
     * @param context
     */
    private void initDefaultValue(Context context, String text) {
        this.mContext = context.getApplicationContext();
        this.mBgColorResId = Color.BLUE;
        this.mText = text;
        this.mBgHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, mContext.getResources().getDisplayMetrics());
        this.mRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, mContext.getResources().getDisplayMetrics());
        this.mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics());
        this.mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, mContext.getResources().getDisplayMetrics());
        this.mTextColorResId = R.color.white;
    }

    /**
     * 计算icon背景宽度
     *
     * @param text icon内文字
     */
    private float caculateBgWidth(String text) {
        if (text == null) {
            text = "";
        }
        if (text.length() > 1) {
            //多字，宽度=文字宽度+padding
            Rect textRect = new Rect();
            Paint paint = new Paint();
            paint.setTextSize(mTextSize);
            paint.getTextBounds(text, 0, text.length(), textRect);
            float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, mContext.getResources().getDisplayMetrics());
            return textRect.width() + padding * 2;
        } else {
            //单字，宽高一致为正方形
            return mBgHeight;
        }
    }

    /**
     * 设置右边距
     *
     * @param rightMarginDpValue
     */
    public IconTextSpan setRightMarginDpValue(int rightMarginDpValue) {
        this.mRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightMarginDpValue, mContext.getResources().getDisplayMetrics());
        return this;
    }

    /**
     * 设置宽度，宽度=背景宽度+右边距
     */
    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (mBgWidth + mRightMargin);
    }

    /**
     * draw
     *
     * @param text   完整文本
     * @param start  setSpan里设置的start
     * @param end    setSpan里设置的start
     * @param x
     * @param top    当前span所在行的上方y
     * @param y      y其实就是metric里baseline的位置
     * @param bottom 当前span所在行的下方y(包含了行间距)，会和下一行的top重合
     * @param paint  使用此span的画笔
     */
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        if (TextUtils.isEmpty(mText)) {
            return;
        }

        //画背景
        Paint bgPaint = mBgPaint;
        bgPaint.setColor(mContext.getResources().getColor(mBgColorResId));
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        Paint.FontMetrics metrics = paint.getFontMetrics();

        float textHeight = metrics.descent - metrics.ascent;
        //算出背景开始画的y坐标
        float bgStartY = y + (textHeight - mBgHeight) / 2 + metrics.ascent;

        //画背景
        RectF bgRect = new RectF(x, bgStartY, x + mBgWidth, bgStartY + mBgHeight);
        canvas.drawRoundRect(bgRect, mRadius, mRadius, bgPaint);

        //把字画在背景中间
        TextPaint textPaint = mTextPaint;
        textPaint.setColor(mContext.getResources().getColor(mTextColorResId));
        textPaint.setTextSize(mTextSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);  //这个只针对x有效
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textRectHeight = fontMetrics.bottom - fontMetrics.top;
        canvas.drawText(mText, x + mBgWidth / 2, bgStartY + (mBgHeight - textRectHeight) / 2 - fontMetrics.top, textPaint);
    }
}