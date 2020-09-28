package com.nj.baijiayun.module_public.helper;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.nj.baijiayun.module_public.R;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class CodeSendHelper {
    @SuppressLint("HandlerLeak")
    public static CountDownHandler initCountDownHandler(TextView mGetCodeTv, CallBack callBack) {

        CountDownHandler countDownHandler = new CountDownHandler( mGetCodeTv) {
            @Override
            protected void setTextByCountDown(TextView textView, int currentCount) {
                textView.setText(String.format(mGetCodeTv.getContext().getString(R.string.public_get_code_fmt), currentCount));
            }

            @Override
            protected void setTextInit(TextView textView) {
                textView.setText(mGetCodeTv.getContext().getString(R.string.common_sendcode));

            }
        };
        mGetCodeTv.setOnClickListener(v -> {
            countDownHandler.start();
            if (callBack != null) {
                callBack.pressSendCode();
            }
        });


        return countDownHandler;


    }

    public interface CallBack {
        void pressSendCode();
    }

}
