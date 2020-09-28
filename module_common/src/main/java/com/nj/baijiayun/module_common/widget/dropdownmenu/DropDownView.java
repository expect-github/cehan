package com.nj.baijiayun.module_common.widget.dropdownmenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.nj.baijiayun.module_common.R;

/**
 * Created by baiiu.
 * 筛选器
 */
public class DropDownView extends RelativeLayout {

    private FrameLayout frameLayoutContainer;
    private View currentView;
    private Animation dismissAnimation;
    private Animation occurAnimation;
    private Animation alphaDismissAnimation;
    private Animation alphaOccurAnimation;


    public DropDownView(Context context) {
        this(context, null);
    }

    public DropDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DropDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        setBackgroundColor(Color.WHITE);
//        setContentView(LayoutInflater.from(getContext()).inflate(R.layout.common_layout_filter, null));

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    public void setContentView(View contentView) {
        removeAllViews();
        frameLayoutContainer = new FrameLayout(getContext());
        frameLayoutContainer.setBackgroundColor(Color.parseColor("#7f000000"));
        addView(frameLayoutContainer,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        frameLayoutContainer.setVisibility(GONE);
        frameLayoutContainer.addView(contentView);
        this.currentView = contentView;
        initListener();
        initAnimation();
    }

    private boolean isOpen = false;

    public void close() {
        frameLayoutContainer.startAnimation(alphaDismissAnimation);
        if (currentView != null) {
            currentView.startAnimation(dismissAnimation);
        }
        isOpen = false;
    }


    public void switchStatus() {
        if (isOpen) {
            close();
        } else {
            open();
        }
    }

    //=======================之上对外暴漏方法=======================================
    private void initListener() {
        frameLayoutContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    close();
                }
            }
        });

    }


    public void open() {
        frameLayoutContainer.setVisibility(VISIBLE);
        frameLayoutContainer.startAnimation(alphaOccurAnimation);
        //可移出去,进行每次展出
        currentView.startAnimation(occurAnimation);
        isOpen = true;

    }

    private void initAnimation() {
        occurAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.top_in);

        SimpleAnimationListener listener = new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                frameLayoutContainer.setVisibility(GONE);
            }
        };
        dismissAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.top_out);
        dismissAnimation.setAnimationListener(listener);
        alphaDismissAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_to_zero);
        alphaDismissAnimation.setDuration(300);
        alphaDismissAnimation.setAnimationListener(listener);
        alphaOccurAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_to_one);
        alphaOccurAnimation.setDuration(300);
    }


}
