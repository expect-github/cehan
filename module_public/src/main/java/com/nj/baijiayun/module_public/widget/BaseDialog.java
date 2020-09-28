package com.nj.baijiayun.module_public.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.nj.baijiayun.module_public.R;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/**
 * Dialog基类
 *
 * @author yangfei
 */
public abstract class BaseDialog extends Dialog {

    /**
     * 获取布局资源Id
     *
     * @return 布局资源Id
     */
    public abstract int getLayoutResId();

    /**
     * 窗口
     */
    private Window mWindow;
    /**
     * 布局参数
     */
    private WindowManager.LayoutParams mParams;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.style_dialog_fuzzy);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);

        // 窗口
        mWindow = super.getWindow();
        if (null != mWindow) {
            // 布局参数
            mParams = mWindow.getAttributes();
        }

        // 获取布局资源Id
        super.setContentView(this.getLayoutResId());

        // 设置可以取消
        setCanCancel(true);
        // 弹出框位置
        setGravity(Gravity.CENTER);
        // 设置宽度比例
        setWidthPercent(1.0f);
        // 设置动画
        setAnimation(R.style.DialogCentreAnim);
    }

    /**
     * 设置可以取消
     */
    public void setCanCancel(boolean cancel) {
        super.setCancelable(cancel);
        super.setCanceledOnTouchOutside(cancel);
    }

    /**
     * 弹出框位置
     */
    public void setGravity(int gravity) {
        if (null != mParams) {
            mParams.gravity = gravity;
            super.onWindowAttributesChanged(mParams);
        }
    }

    /**
     * 设置宽度比例
     */
    public void setWidthPercent(@FloatRange(from = 0.0, to = 1.0) float percent) {
        if (null != mWindow && null != mParams) {
            mParams.width = (int) (getScreenWidth() * percent);
            mWindow.setAttributes(mParams);
        }
    }

    /*
     * 设置高度比例
     */
    public void setHeightPercent(@FloatRange(from = 0.0, to = 1.0) float percent) {
        if (null != mWindow && null != mParams) {
            mParams.height = (int) (getScreenHeight() * percent);
            mWindow.setAttributes(mParams);
        }
    }

    /**
     * 设置动画
     */
    public void setAnimation(@StyleRes int resId) {
        if (null != mWindow) {
            mWindow.setWindowAnimations(resId);
        }
    }

    /**
     * 屏幕宽度
     */
    public  int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     */
    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


}