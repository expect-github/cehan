package com.nj.baijiayun.module_common.widget.jptabbar.animate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * Author jpeng
 * Date: 16-11-14
 * E-mail:peng8350@gmail.com
 * 实现图标缩放动画者
 */
public class ScaleAnimater implements Animatable {

    @Override
    public void onPressDown(View v, boolean selected) {

        v.setScaleX(1.1f);
        v.setScaleY(1.1f);
    }

    @Override
    public void onTouchOut(View v, boolean selected) {
        v.setScaleX(selected?1.2f:1f);
        v.setScaleY(selected?1.2f:1f);
    }

    @Override
    public void onSelectChanged(View v,boolean selected) {
        AnimatorSet scaleAnimator = new AnimatorSet();
        float end = selected?1.2f:1f;
        ObjectAnimator scaleX ;
        ObjectAnimator scaleY;
        scaleX = ObjectAnimator.ofFloat(v,"scaleX",end);
        scaleY  = ObjectAnimator.ofFloat(v,"scaleY",end);
        scaleAnimator.playTogether(scaleX,scaleY);
        scaleAnimator.setDuration(300);
        scaleAnimator.start();
    }

    @Override
    public void onPageAnimate(View v,float offset){
        v.setScaleX(offset*0.2f+1f);
        v.setScaleY(offset*0.2f+1f);
    }

    @Override
    public boolean isNeedPageAnimate() {
        return true;
    }

}