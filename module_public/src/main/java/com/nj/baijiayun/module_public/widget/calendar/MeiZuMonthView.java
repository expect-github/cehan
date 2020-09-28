package com.nj.baijiayun.module_public.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_public.R;

/**
 * 高仿魅族日历布局
 * Created by huanghaibin on 2017/11/15.
 */

public class MeiZuMonthView extends MonthView {

    /**
     * 选中的图形半径
     */
    private float mSelectRadius;

    private float textCenterY;
    private float mPointCenterY;


    /**
     * 背景圆点
     */
    private Paint mPointPaint = new Paint();

    /**
     * 今天的背景色
     */
    private Paint mCurrentDayPaint = new Paint();

    /**
     * 选中的标记颜色
     */
    private Paint mCurrentSchemePain = new Paint();

    /**
     * 圆点半径
     */
    private float mPointRadius;

    private int mPadding;

    private float mCircleRadius;
    /**
     * 自定义魅族标记的圆形背景
     */
    private Paint mSchemeBasicPaint = new Paint();

    private float mSchemeBaseLine;
    private float mMuziTextBaseLine;

    public MeiZuMonthView(Context context) {
        super(context);

//        mSolarTermTextPaint.setColor(0xff489dff);
//        mSolarTermTextPaint.setAntiAlias(true);
//        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        mSchemeBasicPaint.setColor(Color.WHITE);


        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.STROKE);
        mCurrentDayPaint.setStrokeWidth(2);
        mCurrentDayPaint.setColor(0x2D72F5);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);


        Paint.FontMetrics metrics = mCurMonthTextPaint.getFontMetrics();
        mMuziTextBaseLine = DensityUtil.dp2px(10) + (metrics.bottom - metrics.top-metrics.descent);
        mSelectRadius = DensityUtil.dp2px(14);
        textCenterY = DensityUtil.dp2px(10) + (metrics.bottom - metrics.top) / 2;
        mPointCenterY = mMuziTextBaseLine + DensityUtil.dp2px((float) 12.5);
        mPointRadius = DensityUtil.dp2px((float) 2.5);

        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
//        mSelectedPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));

        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint);
//        mSchemeBasicPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));


    }

    @Override
    protected void onPreviewHook() {
//        mSolarTermTextPaint.setTextSize(mCurMonthLunarTextPaint.getTextSize());
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        canvas.drawCircle(x + mItemWidth / 2, y + textCenterY, mSelectRadius, mSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

        boolean isSelected = isSelected(calendar);
        if (isSelected) {
            mPointPaint.setColor(getResources().getColor(R.color.common_main_color));
        } else {
            mPointPaint.setColor(getResources().getColor(R.color.common_main_color));
        }

        canvas.drawCircle(x + mItemWidth / 2, y + mPointCenterY, mPointRadius, mPointPaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float cx = x + mItemWidth / 2;
        boolean isInRange = isInRange(calendar);

        mSelectTextPaint.setTextSize(mCurMonthTextPaint.getTextSize());
        //当然可以换成其它对应的画笔就不麻烦，
        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mMuziTextBaseLine + y, mSelectTextPaint);
        } else {

            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + y,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange ? mCurMonthTextPaint : mOtherMonthTextPaint);

//            canvas.drawText(String.valueOf(calendar.getDay()), cx, mMuziTextBaseLine + y, mCurMonthTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
