package com.nj.baijiayun.module_common.widget.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.dialog
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/12/4 下午3:57
 * @change
 * @time
 * @describe
 */
public class LoadingView extends View implements Animatable {

    private IosLoadingDrawable progressDrawable;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progressDrawable = new IosLoadingDrawable(getContext());
        progressDrawable.setCallback(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        progressDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setLoadingDrawable(Drawable drawable) {
        //TODO 添加多种可变的loading
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        progressDrawable.draw(canvas);
    }

    @Override
    public void start() {
        progressDrawable.start();
    }

    @Override
    public void stop() {
        progressDrawable.stop();
    }

    @Override
    public boolean isRunning() {
        return progressDrawable.isRunning();
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        final Rect bounds = drawable.getBounds();
        invalidate(bounds.left , bounds.top ,
                bounds.right , bounds.bottom );
    }
}
