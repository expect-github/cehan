package com.nj.baijiayun.module_public.widget;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;

/**
 * @author chengang
 * @date 2019-07-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget
 * @describe
 */
public class TeachItemView extends ConstraintLayout {
    public TeachItemView(Context context) {
        super(context);
    }

    public TeachItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TeachItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }


}
