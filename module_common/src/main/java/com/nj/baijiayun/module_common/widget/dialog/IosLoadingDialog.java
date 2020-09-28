package com.nj.baijiayun.module_common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.nj.baijiayun.module_common.R;


/**
 * @project zywx_android
 * @class name：com.baijiayun.baselib.widget.dialog
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/12/4 下午2:26
 * @change
 * @time
 * @describe
 */
public class IosLoadingDialog extends Dialog {
    private LoadingView loadingView;
    private String mLoadingMsg;
    private TextView mLoadingTxt;

    public IosLoadingDialog(@NonNull Context context) {
        this(context, R.style.BasicCommonDialog);
    }

    public IosLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.BasicCommonDialog);
        setContentView(R.layout.common_loading_dialog);
        loadingView = findViewById(R.id.loading_view);
        mLoadingTxt = findViewById(R.id.loading_msg);
    }

    public IosLoadingDialog setLoadingTxt(@StringRes int strIds) {
        return setLoadingTxt(getContext().getString(strIds));
    }


    public IosLoadingDialog setLoadingTxt(String loadingTxt) {
        this.mLoadingMsg = loadingTxt;
        if (this.mLoadingMsg == null || this.mLoadingMsg.length() <= 0) {
            mLoadingTxt.setVisibility(View.GONE);
        } else {
            mLoadingTxt.setVisibility(View.VISIBLE);

        }
        mLoadingTxt.setText(mLoadingMsg);
        return this;
    }

    @Override
    public void dismiss() {
        loadingView.stop();
        super.dismiss();

    }

    @Override
    public void show() {
        loadingView.start();
        super.show();
    }
}