package com.nj.baijiayun.module_course.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2019-11-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.widget
 * @describe
 */
public class AutoVerticalScrollRecycleView extends RecyclerView {
    LinearSmoothScroller smoothScroller;
    private ScrollCallBack scrollCallBack;
    private boolean isStart;
    private boolean needConsumerCallBack = false;

    public AutoVerticalScrollRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        smoothScroller = new LinearSmoothScroller(context) {
            // 返回：滑过1px时经历的时间(ms)。
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 150.0F / (float) displayMetrics.densityDpi;
            }

            //使得不在屏幕的时候也会滑动
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }

            @Override
            protected void onSeekTargetStep(int dx, int dy, State state, Action action) {
                super.onSeekTargetStep(dx, dy, state, action);
            }
        };

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (needConsumerCallBack) {
                        if (scrollCallBack != null) {
                            scrollCallBack.scrollPage();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    public void startScroll() {
        isStart = true;
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    public boolean isAutoScroll() {
        return isStart;
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isStart) {
                return;
            }
            if (getContext() == null || ((Activity) getContext()).isFinishing()) {
                stopAutoScroll();
                return;
            }
            smoothScroller.setTargetPosition(1);
            Objects.requireNonNull(getLayoutManager()).startSmoothScroll(smoothScroller);
            needConsumerCallBack = true;
            handler.sendEmptyMessageDelayed(1, 2000);
        }
    };

    public void stopAutoScroll() {
        isStart = false;
        handler.removeMessages(1);
    }


    public void setScrollCallBack(ScrollCallBack scrollCallBack) {
        this.scrollCallBack = scrollCallBack;
    }

    public interface ScrollCallBack {
        void scrollPage();
    }
}
