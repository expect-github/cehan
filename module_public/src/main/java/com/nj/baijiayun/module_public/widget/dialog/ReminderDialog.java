package com.nj.baijiayun.module_public.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nj.baijiayun.basic.widget.MultipleStatusView;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.PublicContractResponse;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.zzhoujay.richtext.RichText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @author chengang
 * @date 2019-12-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.dialog
 * @describe
 */
public class ReminderDialog extends Dialog {
    private MultipleStatusView mMultipleStatusView;
    private CallBack callBack;

    public ReminderDialog(@NonNull Activity activity) {
        super(activity, R.style.BasicCommonDialog);
        setContentView(R.layout.public_dialog_app_first_enter_reminder);
        mMultipleStatusView = findViewById(R.id.multiple_status_view);
        Button mBtnCancel = findViewById(R.id.btn_cancel);
        Button mBtnConfirm = findViewById(R.id.btn_confirm);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mBtnCancel.setOnClickListener(v -> activity.finish());
        mBtnConfirm.setOnClickListener(v -> {
            dismiss();
            ConfigManager.getInstance().showReminder();
            if (callBack != null) {
                callBack.agree();
            }

        });
        mMultipleStatusView.setContentViewResId(R.layout.public_layout_textview);
        mMultipleStatusView.setOnRetryClickListener(v -> {
            getReminder();
        });

    }


    private TextView getContentView() {
        for (int i = 0; i < mMultipleStatusView.getChildCount(); i++) {
            View childAt = mMultipleStatusView.getChildAt(i);
            if (childAt.getId() == R.id.tv_content) {
                return (TextView) childAt;
            }
        }
        return null;
    }

    @Override
    public void show() {
        super.show();
        getReminder();
    }

    private void getReminder() {
        mMultipleStatusView.showLoading();
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getAppReminder()
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<PublicContractResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(PublicContractResponse publicContractResponse) {
                        if (isDialogShow()) {
                            mMultipleStatusView.showContent();

                            TextView contentView = getContentView();
                            RichText.from(publicContractResponse.getData())
                                    .linkFix(holder -> holder.setColor(ContextCompat.getColor(getContext(), R.color.common_main_color)))
                                    .urlClick(url -> {
                                        JumpHelper.jumpWebViewNoNeedAppTitle(url);
                                        return true;
                                    }).into(contentView);

                            //RichText 的bug  设置点击导致滚动不了,这里修复
                            if (contentView != null) {
                                contentView.setMovementMethod(new UrlLongClickableLinkMovementMethod());
                            }
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        mMultipleStatusView.showError();
                    }
                });
    }

    private boolean isDialogShow() {
        return this.isShowing();
    }


    public ReminderDialog setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void agree();
    }

}
