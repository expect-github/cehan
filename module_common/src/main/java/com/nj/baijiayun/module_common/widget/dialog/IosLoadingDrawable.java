package com.nj.baijiayun.module_common.widget.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import androidx.annotation.NonNull;
import android.view.animation.LinearInterpolator;

import com.nj.baijiayun.module_common.R;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.dialog.IosLoadingDrawable
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/12/4 下午3:18
 * @change
 * @time
 * @describe
 */
public class IosLoadingDrawable extends PaintDrawable implements Animatable, ValueAnimator.AnimatorUpdateListener {

    protected int mProgressDegree = 0;
    protected ValueAnimator mValueAnimator;
    private final Drawable drawable;

    public IosLoadingDrawable(Context context) {
        mValueAnimator = ValueAnimator.ofInt(30, 360);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        drawable = context.getResources().getDrawable(R.drawable.common_ios_loading);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        int preDegree = mProgressDegree;
        mProgressDegree = 30 * (value / 30);
        if (preDegree != mProgressDegree) {
            invalidateSelf();
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        final int width = bounds.width();
        final int height = bounds.height();
        canvas.save();
        canvas.rotate(mProgressDegree, (width) / 2, (height) / 2);
        drawable.draw(canvas);
        canvas.restore();
    }


    @Override
    public void start() {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.addUpdateListener(this);
            mValueAnimator.start();
        }
    }

    @Override
    public void stop() {
        if (mValueAnimator.isRunning()) {
            mValueAnimator.removeAllListeners();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return mValueAnimator.isRunning();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        drawable.setBounds(left, top, right, bottom);

    }
}
