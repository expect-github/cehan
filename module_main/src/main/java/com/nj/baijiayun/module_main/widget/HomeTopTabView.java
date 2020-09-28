package com.nj.baijiayun.module_main.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_main.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-09-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.widget
 * @describe
 */
@Deprecated
public class HomeTopTabView extends LinearLayout {
    private ImageView mImgIcSelectCourse;
    private TextView mSelectCourseTv;
    private ClickCallBack clickCallBack;


    public void setImgRes(int imgRes) {
        mImgIcSelectCourse.setImageResource(imgRes);
    }

    public HomeTopTabView(Context context) {
        this(context, null);

    }

    public void setTextResId(int strResId) {
        mSelectCourseTv.setText(strResId);
    }

    public HomeTopTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTopTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.main_layout_top_tab_view, this);
        mImgIcSelectCourse = inflate.findViewById(R.id.img_ic_select_course);
        mSelectCourseTv = inflate.findViewById(R.id.tv_select_course);
        normalUi();
        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                pressedUi();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                normalUi();
                if (clickCallBack != null) {
                    clickCallBack.call();
                }
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                normalUi();

            }
            return true;
        });
    }


    private void normalUi() {
        setBackgroundResource(R.drawable.main_shap_home_top_tab_normal);
        mImgIcSelectCourse.setColorFilter(null);
        mSelectCourseTv.setTextColor(ContextCompat.getColor(getContext(), R.color.public_FF8C8C8C));

    }

    private void pressedUi() {
        mImgIcSelectCourse.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
        setBackgroundResource(R.drawable.main_shap_home_top_tab_pressed);
        mSelectCourseTv.setTextColor(Color.WHITE);

    }

    public void setClickCallBack(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ClickCallBack {
        void call();
    }


}

