package com.nj.baijiayun.module_common.widget.jptabbar.animate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;



/**
 * Author jpeng
 * Date: 17-9-3
 * E-mail:peng8350@gmail.com
 */
public class Scale2Animater implements Animatable {

    @Override
    public void onPressDown(View v, boolean selected) {
        if(!selected) {
            v.setScaleX(0.75f);
            v.setScaleY(0.75f);
        }
    }

    @Override
    public void onTouchOut(View v, boolean selected) {
        if(!selected) {
            v.setScaleX(1f);
            v.setScaleY(1f);
        }
    }

    @Override
    public void onSelectChanged(View v, boolean selected) {
        if(!selected) {
            return;
        }
        AnimatorSet set= new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(v,"scaleX",0.75f,1.3f,1f,1.2f,1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(v,"scaleY",0.75f,1.3f,1f,1.2f,1f);
        set.playTogether(animator1,animator2);
        set.setDuration(800);
        set.start();

    }

    @Override
    public void onPageAnimate(View v, float offset) {

    }

    @Override
    public boolean isNeedPageAnimate() {
        return false;
    }
}
