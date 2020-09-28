package com.nj.baijiayun.module_common.helper;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author chengang
 * @date 2019-06-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.helper
 * @describe
 */
public class AnimationHelper {

    public static void rotate180Reverse(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180f, 360f);
        animator.setDuration(500);
        animator.start();

    }

    public static void rotate180(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 180f);
        animator.setDuration(500);
        animator.start();
    }

}
