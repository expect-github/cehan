package com.nj.baijiayun.module_public.helper;

import android.annotation.SuppressLint;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.module_public.R;

/**
 * @author chengang
 * @date 2019-06-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_user.helper
 * @describe
 */
public abstract class CountDownHandler extends android.os.Handler {
    public static final int COUNT_DOWN = 1;
    private int max = 60;
    private int currentCount;
    private TextView textView;
    public boolean end = false;

    public void clear() {
        this.removeCallbacksAndMessages(null);
    }

    public void endCountDown() {
        if (!end) {
            end();
        }
        clear();
        if (textView != null) {
            textView.setEnabled(true);
            setTextInit(textView);
        }
    }

    @SuppressLint("HandlerLeak")
    public static CountDownHandler create(TextView textView) {
        return new CountDownHandler(textView) {
            @Override
            protected void setTextByCountDown(TextView textView, int currentCount) {
                textView.setText(String.format(textView.getContext().getString(R.string.public_get_code_fmt), currentCount));
            }

            @Override
            protected void setTextInit(TextView textView) {
                textView.setText(textView.getContext().getString(R.string.common_sendcode));

            }
        };
    }

    public CountDownHandler(int seconds, TextView countView) {
        this.textView = countView;
        max = seconds;
        currentCount = max;
    }


    public CountDownHandler(TextView countView) {
        this.textView = countView;
        currentCount = max;
    }

    public void start() {

        currentCount = max + 1;
        end = false;
        sendEmptyMessage(COUNT_DOWN);
        this.textView.setEnabled(false);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == COUNT_DOWN) {
            if (textView == null) {
                return;
            }
            if (currentCount-- > 0) {

                if (!end) {
                    if (textView.getVisibility() == View.VISIBLE) {
                        setTextByCountDown(textView, currentCount);
                    }
                    sendEmptyMessageDelayed(COUNT_DOWN, 1000);
                }
            } else {
                textView.setEnabled(true);
                setTextInit(textView);
            }
        }
    }

    protected abstract void setTextByCountDown(TextView textView, int currentCount);

    protected abstract void setTextInit(TextView textView);

    private void end() {
        end = true;
    }
}
