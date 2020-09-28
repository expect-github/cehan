package com.nj.baijiayun.module_common.widget.jptabbar.animate;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AnticipateInterpolator;


/**
 * Author jpeng
 * Date: 16-11-15
 * E-mail:peng8350@gmail.com
 * 实现旋转的动画类
 */
public class RotateAnimater implements Animatable {

    @Override
    public void onPressDown(View v, boolean selected) {

    }

    @Override
    public void onTouchOut(View v, boolean selected) {

    }

    @Override
    public void onSelectChanged(View v, boolean selected) {
        int end = selected ? 360 : 0;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(v, "rotation",  end);
        rotateAnimator.setDuration(400);
        rotateAnimator.setInterpolator(new AnticipateInterpolator());
        rotateAnimator.start();
    }

    @Override
    public void onPageAnimate(View v, float offset) {
        v.setRotation(offset * 360);
    }

    @Override
    public boolean isNeedPageAnimate() {
        return true;
    }

}
