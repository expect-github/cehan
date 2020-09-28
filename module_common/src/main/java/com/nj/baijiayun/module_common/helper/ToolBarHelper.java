package com.nj.baijiayun.module_common.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.module_common.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-06-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.helper
 * @describe
 */
public class ToolBarHelper {

    private static void setToolbarBackColor(ActionBar actionBar, Context context, int color) {
        Drawable upArrow = ContextCompat.getDrawable(context, R.drawable.common_back_icon);
        if (upArrow != null) {
            upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(upArrow);
            }
        }
    }

    public static TextView setToolBarTextCenter(final Toolbar toolbar) {
        toolbar.setTitle("");
        TextView inflate = (TextView) LayoutInflater.from(toolbar.getContext()).inflate(R.layout.common_layout_title, null);
        addCenterView(toolbar, inflate);
        return inflate;
    }


    public static TextView setToolBarTextCenter(final Toolbar toolbar, String text) {
        toolbar.setTitle("");
        TextView inflate = (TextView) LayoutInflater.from(toolbar.getContext()).inflate(R.layout.common_layout_title, null);
        addCenterView(toolbar, inflate);
        inflate.setText(text);
        return inflate;
    }


    public static void setToolBarTransparent(Context context, ActionBar actionBar, Toolbar toolbar) {
        toolbar.setBackground(null);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        setToolbarBackColor(actionBar, context, Color.WHITE);
    }

    public static void setToolBarWhite(Context context, ActionBar actionBar, Toolbar toolbar) {
        toolbar.setBackgroundColor(Color.WHITE);
        setToolbarBackColor(actionBar, context, Color.BLACK);
    }

    public static void setTitlePaddingLeftRight(View titleView) {
        titleView.setPadding(DensityUtil.dip2px(25), 0, DensityUtil.dip2px(25), 0);
    }


    public static void addRightImageView(Toolbar toolbar, int res, View.OnClickListener onClickListener) {
        addRightImageView(toolbar, res, 20, onClickListener);
    }

    public static void addLeftImageView(Toolbar toolbar, int res, View.OnClickListener onClickListener) {
        ImageView imageView = new ImageView(toolbar.getContext());
        imageView.setImageResource(res);
        toolbar.addView(imageView);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = DensityUtil.dip2px(15);
        layoutParams.height = DensityUtil.dip2px(15);
        layoutParams.leftMargin = DensityUtil.dip2px(15);
        layoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(onClickListener);
    }

    public static void addRightView(Toolbar toolbar, View view) {
        toolbar.addView(view);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) view.getLayoutParams();
        layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        view.setLayoutParams(layoutParams);
    }


    public static void addRightText(Toolbar toolbar, String text, View.OnClickListener onClickListener) {
        TextView inflate = (TextView) LayoutInflater.from(toolbar.getContext()).inflate(R.layout.common_layout_right_text, null);
        toolbar.addView(inflate);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) inflate.getLayoutParams();
        layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        layoutParams.rightMargin = DensityUtil.dip2px(15);
        inflate.setLayoutParams(layoutParams);
        inflate.setText(text);
        inflate.setOnClickListener(onClickListener);
    }


    public static void addCenterView(Toolbar toolbar, View view) {
        toolbar.addView(view);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) view.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        view.setLayoutParams(layoutParams);
    }

    public static View addCenterView(Toolbar toolbar, int layout) {
        View inflate = LayoutInflater.from(toolbar.getContext()).inflate(layout, null);
        addCenterView(toolbar, inflate);
        return inflate;
    }

    public static View addRightView(Toolbar toolbar, int layout) {
        View inflate = LayoutInflater.from(toolbar.getContext()).inflate(layout, null);
        addRightView(toolbar, inflate);
        return inflate;
    }

    public static View getBackView(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if (toolbar.getChildAt(i) instanceof ImageButton) {
                return toolbar.getChildAt(i);
            }
        }
        return toolbar.getChildAt(0);
    }

    public static void addRightImageViewsLayoutRightToLeft(Toolbar toolbar, int ivDp, int[] resArray, View.OnClickListener[] onClickListenersArray) {
        for (int i = 0; i < resArray.length; i++) {
            ImageView imageView = new ImageView(toolbar.getContext());
            imageView.setImageResource(resArray[i]);
            toolbar.addView(imageView);
            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = DensityUtil.dip2px(ivDp);
            layoutParams.height = DensityUtil.dip2px(ivDp);
            layoutParams.rightMargin = DensityUtil.dip2px(15);
            layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(onClickListenersArray[i]);
        }

    }

    public static void addRightImageView(Toolbar toolbar, int res, int dp, View.OnClickListener onClickListener) {
        ImageView imageView = new ImageView(toolbar.getContext());
        imageView.setImageResource(res);
        toolbar.addView(imageView);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = DensityUtil.dip2px(dp);
        layoutParams.height = DensityUtil.dip2px(dp);
        layoutParams.rightMargin = DensityUtil.dip2px(15);
        layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(onClickListener);
    }

    public static View addBack(Toolbar toolbar, View.OnClickListener onClickListener) {
        View inflate = LayoutInflater.from(toolbar.getContext()).inflate(R.layout.common_layout_back, null);

        toolbar.addView(inflate);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) inflate.getLayoutParams();
        layoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        inflate.setLayoutParams(layoutParams);
        inflate.setOnClickListener(onClickListener);
        return inflate;
    }

}
